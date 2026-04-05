package com.cipher.media.data.online

import com.cipher.media.data.online.jamendo.JamendoApi
import com.cipher.media.data.online.jamendo.JamendoTrack
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnlineMusicRepository @Inject constructor(
    private val jamendoApi: JamendoApi
) {
    private var trendingCache: List<JamendoTrack>? = null
    private var trendingCacheTime: Long = 0
    private var lastLanguage: String? = null
    private val CACHE_DURATION_MS = 3600000L

    suspend fun getTrendingSongs(
        language: String? = null,
        forceRefresh: Boolean = false
    ): Result<List<JamendoTrack>> {
        // Clear cache if language changed
        if (language != lastLanguage) {
            clearCache()
            lastLanguage = language
        }

        if (!forceRefresh && trendingCache != null &&
            System.currentTimeMillis() - trendingCacheTime < CACHE_DURATION_MS) {
            return Result.success(trendingCache!!)
        }

        return jamendoApi.getTrending(language = language).also { result ->
            result.onSuccess { tracks ->
                trendingCache = tracks
                trendingCacheTime = System.currentTimeMillis()
            }
        }
    }

    suspend fun searchSongs(
        query: String,
        language: String? = null,
        genre: String? = null
    ): Result<List<JamendoTrack>> {
        return jamendoApi.searchTracks(
            query = query,
            language = language,
            genre = genre
        )
    }

    suspend fun getIndianSongs(language: String? = null): Result<List<JamendoTrack>> {
        return jamendoApi.getIndianMusic(language = language)
    }

    fun getStreamUrl(track: JamendoTrack, highQuality: Boolean = false): String {
        return jamendoApi.getStreamUrl(track.id, highQuality)
    }

    fun clearCache() {
        trendingCache = null
        trendingCacheTime = 0
    }
}
