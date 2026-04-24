package com.cipher.media.ui.video.tools

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.HardwareBuffer
import android.media.ImageReader
import android.media.MediaScannerConnection
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.PixelCopy
import android.view.SurfaceView
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.media3.ui.PlayerView
import com.cipher.media.billing.ProFeatureGate
import com.cipher.media.billing.Tier
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

/**
 * Feature 16: 4K Screenshot Capture (₹99/month)
 * No watermark, full resolution, save to gallery
 */
@Singleton
class ScreenshotManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val proFeatureGate: ProFeatureGate
) {
    private var currentTier: Tier = Tier.FREE

    fun setUserTier(tier: Tier) {
        currentTier = tier
    }

    suspend fun capture(
        playerView: PlayerView,
        videoWidth: Int = 0,
        videoHeight: Int = 0
    ): File? = withContext(Dispatchers.IO) {
        if (!proFeatureGate.checkAccess(currentTier)) {
            return@withContext null
        }

        try {
            val surfaceView = playerView.videoSurfaceView as? SurfaceView ?: return@withContext null
            val width = if (videoWidth > 0) videoWidth else surfaceView.width
            val height = if (videoHeight > 0) videoHeight else surfaceView.height

            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            suspendCancellableCoroutine { continuation ->
                PixelCopy.request(
                    surfaceView,
                    bitmap,
                    { copyResult ->
                        if (copyResult == PixelCopy.SUCCESS) {
                            val file = saveToGallery(bitmap)
                            continuation.resume(file)
                        } else {
                            continuation.resume(null)
                        }
                    },
                    Handler(Looper.getMainLooper())
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun saveToGallery(bitmap: Bitmap): File? {
        val fileName = "CIPHER_${System.currentTimeMillis()}.png"

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveToGalleryApi29(bitmap, fileName)
        } else {
            saveToGalleryLegacy(bitmap, fileName)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveToGalleryApi29(bitmap: Bitmap, fileName: String): File? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/CIPHER")
        }

        val uri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ) ?: return null

        context.contentResolver.openOutputStream(uri)?.use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }

        return File(uri.path ?: return null)
    }

    private fun saveToGalleryLegacy(bitmap: Bitmap, fileName: String): File? {
        val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val cipherDir = File(picturesDir, "CIPHER")
        cipherDir.mkdirs()

        val file = File(cipherDir, fileName)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }

        MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), null, null)
        return file
    }
}
