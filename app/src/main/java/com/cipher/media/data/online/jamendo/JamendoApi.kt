package com.cipher.media.data.online.jamendo

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JamendoApi @Inject constructor(
    private val client: OkHttpClient,
    private val gson: Gson
) {
    companion object {
        const val BASE_URL = "https://api.jamendo.com/v3.0"
        const val CLIENT_ID = "db4460ec"
        const val DEFAULT_LIMIT = 50

        // Language to keywords mapping for better search results
        private val languageToKeywords = mapOf(
            "hindi" to listOf("hindi", "bollywood", "indian", "desi"),
            "tamil" to listOf("tamil", "kollywood", "indian"),
            "telugu" to listOf("telugu", "tollywood", "indian"),
            "punjabi" to listOf("punjabi", "bhangra", "indian"),
            "marathi" to listOf("marathi", "indian"),
            "bengali" to listOf("bengali", "bangla", "indian"),
            "malayalam" to listOf("malayalam", "indian"),
            "kannada" to listOf("kannada", "indian"),
            "gujarati" to listOf("gujarati", "indian"),
            "english" to listOf("english", "pop", "rock"),
            "all" to listOf("music")
        )
    }

    suspend fun searchTracks(
        query: String? = null,
        genre: String? = null,
        language: String? = null,
        limit: Int = DEFAULT_LIMIT
    ): Result<List<JamendoTrack>> = withContext(Dispatchers.IO) {
        try {
            val urlBuilder = "$BASE_URL/tracks".toHttpUrlOrNull()?.newBuilder()
                ?.addQueryParameter("client_id", CLIENT_ID)
                ?.addQueryParameter("format", "json")
                ?.addQueryParameter("limit", limit.toString())
                ?.addQueryParameter("include", "stats+lyrics")
                ?.addQueryParameter("audioformat", "mp32")

            // Build search query combining user query, language keywords, and genre
            val searchTerms = mutableListOf<String>()
            query?.let { if (it.isNotBlank()) searchTerms.add(it) }

            // Add language keywords
            language?.let { lang ->
                if (lang != "all") {
                    val keywords = languageToKeywords[lang.lowercase()] ?: listOf(lang)
                    searchTerms.addAll(keywords)
                    // Add fuzzytags for better matching
                    urlBuilder?.addQueryParameter("fuzzytags", lang)
                }
            }

            // Add genre
            genre?.let { searchTerms.add(it) }

            // Set combined search terms
            if (searchTerms.isNotEmpty()) {
                urlBuilder?.addQueryParameter("search", searchTerms.joinToString(" "))
            }

            val url = urlBuilder?.build()
                ?: return@withContext Result.failure(IOException("Failed to build URL"))

            Log.d("JamendoApi", "Request URL: $url")

            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                return@withContext Result.failure(IOException("HTTP ${response.code}"))
            }

            val body = response.body?.string()
                ?: return@withContext Result.failure(IOException("Empty response body"))

            val result = gson.fromJson(body, JamendoResponse::class.java)
            Result.success(result.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTrending(
        language: String? = null,
        period: String = "week"
    ): Result<List<JamendoTrack>> = withContext(Dispatchers.IO) {
        try {
            val urlBuilder = "$BASE_URL/tracks".toHttpUrlOrNull()?.newBuilder()
                ?.addQueryParameter("client_id", CLIENT_ID)
                ?.addQueryParameter("format", "json")
                ?.addQueryParameter("limit", "30")
                ?.addQueryParameter("order", "popularity_$period")
                ?.addQueryParameter("include", "stats")
                ?.addQueryParameter("audioformat", "mp32")

            // Add language keywords to search if specified
            language?.let { lang ->
                if (lang != "all") {
                    val keywords = languageToKeywords[lang.lowercase()]?.joinToString(" ") ?: lang
                    urlBuilder?.addQueryParameter("search", keywords)
                    urlBuilder?.addQueryParameter("fuzzytags", lang)
                }
            }

            val url = urlBuilder?.build()
                ?: return@withContext Result.failure(IOException("Failed to build URL"))

            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            val body = response.body?.string()
                ?: return@withContext Result.failure(IOException("Empty response body"))

            val result = gson.fromJson(body, JamendoResponse::class.java)
            Result.success(result.results)
        } catch (e: Exception) {
            Log.e("JamendoApi", "getTrending Error: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun getTracksByLanguage(
        language: String,
        limit: Int = 50
    ): Result<List<JamendoTrack>> = withContext(Dispatchers.IO) {
        val keywords = languageToKeywords[language.lowercase()] ?: listOf(language)

        // Try with first keyword
        val result = searchTracks(
            query = keywords.first(),
            language = language,
            limit = limit
        )

        result
    }

    suspend fun getIndianMusic(
        language: String? = null,
        subGenre: String? = null
    ): Result<List<JamendoTrack>> = withContext(Dispatchers.IO) {
        val tags = buildList {
            add("indian")
            language?.let { add(it) }
            subGenre?.let { add(it) }
        }.joinToString("+")

        searchTracks(genre = tags, limit = 50)
    }

    fun getStreamUrl(trackId: String, highQuality: Boolean = false): String {
        val quality = if (highQuality) "mp31" else "mp32"
        return "$BASE_URL/download/track/$trackId/?client_id=$CLIENT_ID&audioformat=$quality"
    }
}
