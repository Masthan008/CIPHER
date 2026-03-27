package com.cipher.media.ui.video.subtitle.online

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

/**
 * Online subtitle search result model.
 */
data class OnlineSubtitleResult(
    val id: String,
    val fileId: Int,
    val fileName: String,
    val language: String,
    val release: String,
    val downloadCount: Int,
    val source: String // "OpenSubtitles" or "Wyzie"
)

/**
 * OpenSubtitles REST API v1 client.
 * PRO FEATURE: Online subtitle search and download.
 *
 * Free account: 20 downloads/day
 * Rate limit: 40 req/10 seconds
 */
class OpenSubtitlesApi(private val apiKey: String) {

    companion object {
        private const val BASE_URL = "https://api.opensubtitles.com/api/v1"
        private const val USER_AGENT = "CIPHER Media Player v1.0"
    }

    /**
     * Search subtitles by query string and language.
     */
    suspend fun search(
        query: String,
        language: String = "en"
    ): List<OnlineSubtitleResult> = withContext(Dispatchers.IO) {
        val results = mutableListOf<OnlineSubtitleResult>()
        try {
            val url = URL("$BASE_URL/subtitles?query=${encode(query)}&languages=$language")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                setRequestProperty("Api-Key", apiKey)
                setRequestProperty("User-Agent", USER_AGENT)
                setRequestProperty("Content-Type", "application/json")
                connectTimeout = 10_000
                readTimeout = 10_000
            }

            if (conn.responseCode == 200) {
                val body = conn.inputStream.bufferedReader().readText()
                val json = JSONObject(body)
                val data = json.getJSONArray("data")

                for (i in 0 until data.length().coerceAtMost(20)) {
                    val item = data.getJSONObject(i)
                    val attrs = item.getJSONObject("attributes")
                    val files = attrs.getJSONArray("files")

                    if (files.length() > 0) {
                        val file = files.getJSONObject(0)
                        results.add(
                            OnlineSubtitleResult(
                                id = item.getString("id"),
                                fileId = file.getInt("file_id"),
                                fileName = file.getString("file_name"),
                                language = attrs.getString("language"),
                                release = attrs.optString("release", "Unknown"),
                                downloadCount = attrs.optInt("download_count", 0),
                                source = "OpenSubtitles"
                            )
                        )
                    }
                }
            }
            conn.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        results
    }

    /**
     * Get download link for a subtitle file.
     * Consumes one download from the daily quota.
     */
    suspend fun getDownloadLink(fileId: Int): String? = withContext(Dispatchers.IO) {
        try {
            val url = URL("$BASE_URL/download")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Api-Key", apiKey)
                setRequestProperty("User-Agent", USER_AGENT)
                setRequestProperty("Content-Type", "application/json")
                doOutput = true
                connectTimeout = 10_000
                readTimeout = 10_000
            }

            val body = """{"file_id": $fileId}"""
            conn.outputStream.write(body.toByteArray())

            if (conn.responseCode == 200) {
                val response = conn.inputStream.bufferedReader().readText()
                val json = JSONObject(response)
                conn.disconnect()
                json.getString("link")
            } else {
                conn.disconnect()
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Download subtitle file from URL to cache directory.
     */
    suspend fun downloadToFile(context: Context, downloadUrl: String, fileName: String): File? =
        withContext(Dispatchers.IO) {
            try {
                val url = URL(downloadUrl)
                val conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = 15_000
                conn.readTimeout = 15_000

                val cacheDir = File(context.cacheDir, "subtitles")
                cacheDir.mkdirs()
                val outFile = File(cacheDir, fileName)

                conn.inputStream.use { input ->
                    outFile.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                conn.disconnect()
                outFile
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

    private fun encode(s: String): String = java.net.URLEncoder.encode(s, "UTF-8")
}
