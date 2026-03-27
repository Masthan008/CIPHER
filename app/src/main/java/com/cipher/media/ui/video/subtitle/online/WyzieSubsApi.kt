package com.cipher.media.ui.video.subtitle.online

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

/**
 * Wyzie Subs API — free, open-source, no registration required.
 * Proxied requests to OpenSubtitles; no direct rate limits.
 * Used as a backup when OpenSubtitles quota is exhausted.
 */
class WyzieSubsApi {

    companion object {
        private const val BASE_URL = "https://sub.wyzie.ru"
    }

    /**
     * Search subtitles by movie/show title and language.
     */
    suspend fun search(
        query: String,
        language: String = "en"
    ): List<OnlineSubtitleResult> = withContext(Dispatchers.IO) {
        val results = mutableListOf<OnlineSubtitleResult>()
        try {
            val url = URL("$BASE_URL/search?query=${encode(query)}&language=$language")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                setRequestProperty("User-Agent", "CIPHER Media Player v1.0")
                connectTimeout = 10_000
                readTimeout = 10_000
            }

            if (conn.responseCode == 200) {
                val body = conn.inputStream.bufferedReader().readText()
                val data = JSONArray(body)

                for (i in 0 until data.length().coerceAtMost(20)) {
                    val item = data.getJSONObject(i)
                    results.add(
                        OnlineSubtitleResult(
                            id = item.optString("id", "$i"),
                            fileId = item.optInt("file_id", 0),
                            fileName = item.optString("file_name", "subtitle.srt"),
                            language = item.optString("language", language),
                            release = item.optString("release", "Unknown"),
                            downloadCount = item.optInt("download_count", 0),
                            source = "Wyzie"
                        )
                    )
                }
            }
            conn.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        results
    }

    private fun encode(s: String): String = java.net.URLEncoder.encode(s, "UTF-8")
}
