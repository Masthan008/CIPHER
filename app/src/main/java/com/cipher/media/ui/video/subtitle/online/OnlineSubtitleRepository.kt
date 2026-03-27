package com.cipher.media.ui.video.subtitle.online

import android.content.Context
import java.io.File
import java.time.LocalDate

/**
 * PRO FEATURE: Online subtitle repository with smart fallback.
 *
 * Priority order:
 * 1. OpenSubtitles (largest database, API key auth)
 * 2. Wyzie Subs (backup, unlimited, no registration)
 *
 * Free users: 5 downloads/day
 * Pro users: 50 downloads/day (with API key)
 */
class OnlineSubtitleRepository(
    private val context: Context,
    apiKey: String
) {
    private val openSubtitles = OpenSubtitlesApi(apiKey)
    private val wyzieSubs = WyzieSubsApi()

    companion object {
        private const val PREFS_NAME = "cipher_subtitle_quotas"
        private const val KEY_LAST_DATE = "last_download_date"
        private const val KEY_DOWNLOAD_COUNT = "download_count"
        private const val FREE_DAILY_LIMIT = 5
        private const val PRO_DAILY_LIMIT = 50
    }

    /**
     * Search subtitles using smart fallback: OpenSubtitles → Wyzie.
     */
    suspend fun searchSubtitles(
        query: String,
        language: String = "en"
    ): List<OnlineSubtitleResult> {
        // Try OpenSubtitles first (better quality and larger database)
        try {
            val results = openSubtitles.search(query, language)
            if (results.isNotEmpty()) return results
        } catch (e: Exception) {
            // Rate limited or error — fall through to backup
        }

        // Fallback to Wyzie Subs (unlimited, no registration)
        try {
            val results = wyzieSubs.search(query, language)
            if (results.isNotEmpty()) return results
        } catch (e: Exception) {
            // Both failed
        }

        return emptyList()
    }

    /**
     * Download a subtitle file to cache.
     * Checks daily quota based on user tier.
     *
     * @param result The subtitle search result to download
     * @param isPro Whether user has Pro tier
     * @return Downloaded File or null if quota exceeded / error
     */
    suspend fun downloadSubtitle(
        result: OnlineSubtitleResult,
        isPro: Boolean
    ): DownloadResult {
        // Check daily limit
        val limit = if (isPro) PRO_DAILY_LIMIT else FREE_DAILY_LIMIT
        val todayCount = getTodayDownloadCount()

        if (todayCount >= limit) {
            return DownloadResult.QuotaExceeded(
                limit = limit,
                used = todayCount,
                isPro = isPro
            )
        }

        // Download based on source
        val file: File? = when (result.source) {
            "OpenSubtitles" -> {
                val downloadUrl = openSubtitles.getDownloadLink(result.fileId)
                if (downloadUrl != null) {
                    openSubtitles.downloadToFile(context, downloadUrl, result.fileName)
                } else null
            }
            "Wyzie" -> {
                // For Wyzie, the fileId can be used to construct download URL
                // Use OpenSubtitles API for download if available
                val downloadUrl = openSubtitles.getDownloadLink(result.fileId)
                if (downloadUrl != null) {
                    openSubtitles.downloadToFile(context, downloadUrl, result.fileName)
                } else null
            }
            else -> null
        }

        return if (file != null && file.exists()) {
            incrementDownloadCount()
            DownloadResult.Success(file)
        } else {
            DownloadResult.Error("Failed to download subtitle")
        }
    }

    /**
     * Get remaining downloads for today.
     */
    fun getRemainingDownloads(isPro: Boolean): Int {
        val limit = if (isPro) PRO_DAILY_LIMIT else FREE_DAILY_LIMIT
        return (limit - getTodayDownloadCount()).coerceAtLeast(0)
    }

    private fun getTodayDownloadCount(): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lastDate = prefs.getString(KEY_LAST_DATE, null)
        val today = LocalDate.now().toString()

        return if (lastDate == today) {
            prefs.getInt(KEY_DOWNLOAD_COUNT, 0)
        } else {
            // New day — reset counter
            prefs.edit()
                .putString(KEY_LAST_DATE, today)
                .putInt(KEY_DOWNLOAD_COUNT, 0)
                .apply()
            0
        }
    }

    private fun incrementDownloadCount() {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val today = LocalDate.now().toString()
        val count = prefs.getInt(KEY_DOWNLOAD_COUNT, 0)
        prefs.edit()
            .putString(KEY_LAST_DATE, today)
            .putInt(KEY_DOWNLOAD_COUNT, count + 1)
            .apply()
    }
}

/**
 * Sealed result type for subtitle download operations.
 */
sealed class DownloadResult {
    data class Success(val file: File) : DownloadResult()
    data class QuotaExceeded(val limit: Int, val used: Int, val isPro: Boolean) : DownloadResult()
    data class Error(val message: String) : DownloadResult()
}
