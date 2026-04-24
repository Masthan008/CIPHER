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
        const val TAG = "JamendoApi"

        // Enhanced language mapping with fallback keywords
        val languageKeywords = mapOf(
            "hindi" to listOf("hindi", "bollywood", "india", "desi", "hindustani", "shreya", "arijit", "sonu"),
            "tamil" to listOf("tamil", "kollywood", "chennai", "ilayaraja", "rahman", "arrahman"),
            "telugu" to listOf("telugu", "tollywood", "hyderabad", "dsp", "devi sri"),
            "punjabi" to listOf("punjabi", "bhangra", "punjab", "diljit", "guru", "jass"),
            "marathi" to listOf("marathi", "marathi song", "lavani", "natya"),
            "bengali" to listOf("bengali", "bangla", "kolkata", "rabindra", "nachiketa"),
            "malayalam" to listOf("malayalam", "mollywood", "kerala", "gopi", "sushin"),
            "kannada" to listOf("kannada", "sandalwood", "bangalore", "punith", "yash"),
            "gujarati" to listOf("gujarati", "gujarat", "garba", "dandiya", "falguni"),
            "english" to listOf("pop", "rock", "english", "billie", "taylor", "ed sheeran"),
            "all" to listOf("music", "popular", "trending")
        )
    }

    // Cache for search results
    private val searchCache = android.util.LruCache<String, List<JamendoTrack>>(100)

    // MAIN SEARCH - Fixed with multiple strategies and caching
    suspend fun searchTracks(
        query: String = "",
        language: String = "all",
        limit: Int = 50
    ): Result<List<JamendoTrack>> = withContext(Dispatchers.IO) {
        try {
            val cacheKey = "$query-$language-$limit"
            searchCache.get(cacheKey)?.let { return@withContext Result.success(it) }

            // Strategy 1: Direct search with query + language keywords
            val keywords = languageKeywords[language.lowercase()] ?: listOf(language)
            val combinedQuery = buildString {
                if (query.isNotBlank()) append("$query ")
                append(keywords.take(2).joinToString(" "))
            }

            val result1 = fetchTracks(
                search = combinedQuery.trim(),
                limit = limit / 2
            )

            // Strategy 2: Search by artist name (if first fails)
            val result2 = if (result1.getOrNull().isNullOrEmpty()) {
                fetchTracks(
                    artist = query.takeIf { it.isNotBlank() },
                    tags = keywords.first(),
                    limit = limit / 2
                )
            } else Result.success(emptyList())

            // Strategy 3: Generic popular tracks as fallback
            val result3 = if (result1.getOrNull().isNullOrEmpty() &&
                result2.getOrNull().isNullOrEmpty()) {
                fetchTracks(
                    order = "popularity_week",
                    tags = keywords.first(),
                    limit = limit
                )
            } else Result.success(emptyList())

            // Combine all results
            val allTracks = mutableListOf<JamendoTrack>()
            result1.onSuccess { allTracks.addAll(it) }
            result2.onSuccess { allTracks.addAll(it) }
            result3.onSuccess { allTracks.addAll(it) }

            // Remove duplicates and filter by relevance
            val uniqueTracks = allTracks
                .distinctBy { it.id }
                .filter { track ->
                    isRelevantTrack(track, query, language)
                }
                .take(limit)

            // Cache results
            if (uniqueTracks.isNotEmpty()) {
                searchCache.put(cacheKey, uniqueTracks)
            }

            if (uniqueTracks.isEmpty()) {
                return@withContext Result.failure(
                    IOException("No songs found. Try: 1) Different keywords 2) 'All' language 3) Check internet")
                )
            }

            Result.success(uniqueTracks)

        } catch (e: Exception) {
            Log.e(TAG, "Search error: ${e.message}", e)
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
                ?.addQueryParameter("limit", "50")
                ?.addQueryParameter("order", "popularity_$period")
                ?.addQueryParameter("include", "stats")
                ?.addQueryParameter("audioformat", "mp32")

            // Add fuzzytags for language filtering (not search terms)
            language?.let { lang ->
                if (lang != "all") {
                    urlBuilder?.addQueryParameter("fuzzytags", lang)
                }
            }

            val url = urlBuilder?.build()
                ?: return@withContext Result.failure(IOException("Failed to build URL"))

            Log.d("JamendoApi", "Trending URL: $url")

            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            val body = response.body?.string()
                ?: return@withContext Result.failure(IOException("Empty response body"))

            val result = gson.fromJson(body, JamendoResponse::class.java)
            Log.d("JamendoApi", "Trending found ${result.results.size} tracks")
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
        val keywords = languageKeywords[language.lowercase()] ?: listOf(language)
        val allTracks = mutableListOf<JamendoTrack>()
        
        // Try each keyword and combine results
        for (keyword in keywords) {
            if (allTracks.size >= limit) break
            
            val result = searchTracks(
                query = keyword,
                language = language,
                limit = limit / keywords.size + 10
            )
            
            result.getOrNull()?.let { tracks ->
                // Avoid duplicates by checking track ID
                val existingIds = allTracks.map { it.id }.toSet()
                val newTracks = tracks.filter { it.id !in existingIds }
                allTracks.addAll(newTracks)
            }
        }
        
        if (allTracks.isNotEmpty()) {
            Result.success(allTracks.take(limit))
        } else {
            Result.failure(IOException("No tracks found for language: $language"))
        }
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

        searchTracks(query = tags, limit = 50)
    }

    // Core API call helper
    private suspend fun fetchTracks(
        search: String? = null,
        artist: String? = null,
        tags: String? = null,
        order: String? = null,
        limit: Int = 20
    ): Result<List<JamendoTrack>> = withContext(Dispatchers.IO) {
        val urlBuilder = ("$BASE_URL/tracks".toHttpUrlOrNull() ?: return@withContext Result.failure(IOException("Invalid URL"))).newBuilder()
            .addQueryParameter("client_id", CLIENT_ID)
            .addQueryParameter("format", "json")
            .addQueryParameter("limit", limit.toString())
            .addQueryParameter("include", "stats")
            .addQueryParameter("audioformat", "mp32")
            .addQueryParameter("fuzzytags", "true")

        search?.let { urlBuilder.addQueryParameter("search", it) }
        artist?.let { urlBuilder.addQueryParameter("artist_name", it) }
        tags?.let { urlBuilder.addQueryParameter("tags", it) }
        order?.let { urlBuilder.addQueryParameter("order", it) }

        val url = urlBuilder.build()
        Log.d(TAG, "Fetching: $url")

        return@withContext try {
            val request = Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .build()

            val response = client.newCall(request).execute()

            if (!response.isSuccessful) {
                return@withContext Result.failure(IOException("HTTP ${response.code}"))
            }

            val body = response.body?.string()
                ?: return@withContext Result.failure(IOException("Empty response"))

            val jamendoResponse = gson.fromJson(body, JamendoResponse::class.java)

            if (jamendoResponse.headers.code != 0) {
                return@withContext Result.failure(
                    IOException("API Error: ${jamendoResponse.headers.error_message}")
                )
            }

            Result.success(jamendoResponse.results)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Relevance filter
    private fun isRelevantTrack(track: JamendoTrack, query: String, language: String): Boolean {
        if (query.isBlank()) return true

        val searchTerms = query.lowercase().split(" ")
        val trackText = """
            ${track.name} 
            ${track.artist_name} 
            ${track.album_name}
        """.lowercase()

        return searchTerms.any { term ->
            trackText.contains(term) ||
            fuzzyMatch(term, track.name) ||
            fuzzyMatch(term, track.artist_name)
        }
    }

    private fun fuzzyMatch(query: String, target: String?): Boolean {
        if (target.isNullOrBlank()) return false
        if (query.length < 3) return target.contains(query, ignoreCase = true)
        val distance = levenshteinDistance(query.lowercase(), target.take(query.length + 2).lowercase())
        return distance <= 2
    }

    private fun levenshteinDistance(s1: String, s2: String): Int {
        val dp = Array(s1.length + 1) { IntArray(s2.length + 1) }
        for (i in 0..s1.length) dp[i][0] = i
        for (j in 0..s2.length) dp[0][j] = j

        for (i in 1..s1.length) {
            for (j in 1..s2.length) {
                dp[i][j] = if (s1[i-1] == s2[j-1]) {
                    dp[i-1][j-1]
                } else {
                    minOf(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
                }
            }
        }
        return dp[s1.length][s2.length]
    }

    // Get lyrics for a track
    suspend fun getLyrics(trackId: String): String? = withContext(Dispatchers.IO) {
        return@withContext try {
            val urlBuilder = "$BASE_URL/tracks".toHttpUrlOrNull()?.newBuilder()
                ?: return@withContext null
            
            urlBuilder
                .addQueryParameter("client_id", CLIENT_ID)
                .addQueryParameter("format", "json")
                .addQueryParameter("id", trackId)
                .addQueryParameter("include", "lyrics")
            
            val url = urlBuilder.build()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: return@withContext null

            val result = gson.fromJson(body, JamendoResponse::class.java)
            result.results.firstOrNull()?.lyrics

        } catch (e: Exception) {
            Log.e(TAG, "Lyrics fetch error: ${e.message}")
            null
        }
    }

    fun getStreamUrl(trackId: String, highQuality: Boolean = false): String {
        val quality = if (highQuality) "mp31" else "mp32"
        return "$BASE_URL/download/track/$trackId/?client_id=$CLIENT_ID&audioformat=$quality"
    }
}
