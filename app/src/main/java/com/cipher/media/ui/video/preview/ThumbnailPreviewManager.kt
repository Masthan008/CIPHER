package com.cipher.media.ui.video.preview

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import androidx.collection.LruCache
import com.cipher.media.billing.ProFeatureGate
import com.cipher.media.billing.Tier
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Feature 13: Thumbnail Preview While Seeking (₹99/month)
 * Extracts frames at seek position with LRU cache
 */
@Singleton
class ThumbnailPreviewManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val proFeatureGate: ProFeatureGate
) {
    private var currentTier: Tier = Tier.FREE

    // 50MB LRU cache
    private val cache = object : LruCache<String, Bitmap>(50 * 1024 * 1024) {
        override fun sizeOf(key: String, value: Bitmap): Int {
            return value.byteCount
        }
    }

    fun setUserTier(tier: Tier) {
        currentTier = tier
    }

    suspend fun getThumbnail(videoPath: String, timeMs: Long): Bitmap? {
        if (!proFeatureGate.checkAccess(currentTier)) return null

        // Validate video path
        if (videoPath.isBlank() || !isValidVideoPath(videoPath)) return null

        val cacheKey = "$videoPath:$timeMs"
        cache.get(cacheKey)?.let { return it }

        return withContext(Dispatchers.IO) {
            var retriever: MediaMetadataRetriever? = null
            try {
                retriever = MediaMetadataRetriever()
                
                // Set data source with proper error handling
                try {
                    retriever.setDataSource(videoPath)
                } catch (e: IllegalArgumentException) {
                    // Invalid path or unsupported format
                    return@withContext null
                } catch (e: SecurityException) {
                    // Permission denied
                    return@withContext null
                } catch (e: RuntimeException) {
                    // Other runtime errors (file not found, etc.)
                    return@withContext null
                }

                // Get frame at time (microseconds)
                val frame = retriever.getFrameAtTime(
                    timeMs * 1000,
                    MediaMetadataRetriever.OPTION_CLOSEST_SYNC
                )

                frame?.let {
                    // Scale to thumbnail size 160x90
                    val scaled = Bitmap.createScaledBitmap(it, 160, 90, true)
                    it.recycle() // Recycle original to free memory
                    cache.put(cacheKey, scaled)
                    scaled
                }
            } catch (e: Exception) {
                null
            } finally {
                // CRITICAL FIX: Always release retriever to prevent memory leak
                try {
                    retriever?.release()
                } catch (e: Exception) {
                    // Ignore release errors
                }
            }
        }
    }

    private fun isValidVideoPath(path: String): Boolean {
        return path.startsWith("/") || 
               path.startsWith("content://") ||
               path.startsWith("file://") ||
               path.startsWith("http://") ||
               path.startsWith("https://")
    }

    fun clearCache() {
        cache.evictAll()
    }
}
