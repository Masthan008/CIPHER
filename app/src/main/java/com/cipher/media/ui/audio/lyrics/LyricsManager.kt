package com.cipher.media.ui.audio.lyrics

import android.content.Context
import com.cipher.media.data.model.AudioItem
import com.cipher.media.ui.audio.lyrics.model.LyricLine
import com.cipher.media.ui.audio.lyrics.model.LyricsSource
import com.cipher.media.ui.audio.lyrics.model.ParsedLyrics
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages lyrics loading from multiple sources with priority:
 * 1. Embedded metadata (via MediaStore - already in AudioItem)
 * 2. Local .lrc file (same directory, same base name)
 * 3. Online LRCLIB API (Pro only)
 * 4. Manual paste
 */
@Singleton
class LyricsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    /**
     * Load lyrics for the given audio item. Tries sources in priority order.
     */
    suspend fun loadLyrics(audio: AudioItem): ParsedLyrics = withContext(Dispatchers.IO) {
        // 1. Try local LRC file
        val lrcFromFile = tryLoadLocalLrc(audio.path)
        if (lrcFromFile != null && lrcFromFile.lines.isNotEmpty()) {
            return@withContext lrcFromFile
        }

        // 2. Return empty (embedded/manual can be handled by caller)
        ParsedLyrics(
            title = audio.title,
            artist = audio.artist,
            album = audio.album,
            source = LyricsSource.NONE
        )
    }

    /**
     * Try to find a .lrc file with the same basename as the audio file.
     */
    private fun tryLoadLocalLrc(audioPath: String): ParsedLyrics? {
        try {
            val audioFile = File(audioPath)
            val baseName = audioFile.nameWithoutExtension
            val lrcFile = File(audioFile.parent, "$baseName.lrc")
            if (!lrcFile.exists()) return null

            val raw = lrcFile.readText(Charsets.UTF_8)
            return parseLrc(raw, LyricsSource.LOCAL_LRC)
        } catch (_: Exception) {
            return null
        }
    }

    /**
     * Parse raw LRC string into ParsedLyrics.
     */
    fun parseLrc(raw: String, source: LyricsSource = LyricsSource.MANUAL): ParsedLyrics {
        var title = ""
        var artist = ""
        var album = ""
        val lines = mutableListOf<LyricLine>()

        raw.lines().forEach { line ->
            val trimmed = line.trim()
            when {
                trimmed.startsWith("[ti:") -> title = trimmed.removeSurrounding("[ti:", "]")
                trimmed.startsWith("[ar:") -> artist = trimmed.removeSurrounding("[ar:", "]")
                trimmed.startsWith("[al:") -> album = trimmed.removeSurrounding("[al:", "]")
                trimmed.startsWith("[") -> {
                    // May have multiple timestamps per line: [00:12.00][00:24.00]text
                    val timestamps = Regex("""\[\d{2}:\d{2}\.\d{2,3}]""").findAll(trimmed)
                    val text = trimmed.replace(Regex("""\[\d{2}:\d{2}\.\d{2,3}]"""), "").trim()
                    timestamps.forEach { match ->
                        LyricLine.parse("${match.value}$text")?.let { lines.add(it) }
                    }
                }
                trimmed.isNotEmpty() -> {
                    // Plain text line (unsynchronized)
                    lines.add(LyricLine(0L, trimmed))
                }
            }
        }

        lines.sort()
        return ParsedLyrics(title, artist, album, lines, source)
    }

    /**
     * Parse plain text lyrics (no timestamps).
     */
    fun parsePlainText(text: String): ParsedLyrics {
        val lines = text.lines()
            .filter { it.isNotBlank() }
            .map { LyricLine(0L, it.trim()) }
        return ParsedLyrics(lines = lines, source = LyricsSource.MANUAL)
    }

    /**
     * Download lyrics from LRCLIB.net API (Pro feature).
     * Returns null on failure.
     */
    suspend fun downloadFromLrcLib(
        title: String,
        artist: String,
        album: String = "",
        duration: Long = 0L
    ): ParsedLyrics? = withContext(Dispatchers.IO) {
        try {
            val query = buildString {
                append("https://lrclib.net/api/get?")
                append("track_name=${URLEncoder.encode(title, "UTF-8")}")
                append("&artist_name=${URLEncoder.encode(artist, "UTF-8")}")
                if (album.isNotBlank()) append("&album_name=${URLEncoder.encode(album, "UTF-8")}")
                if (duration > 0) append("&duration=${duration / 1000}")
            }

            val url = URL(query)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.setRequestProperty("User-Agent", "CIPHER Media Player/1.0")
            conn.connectTimeout = 5000
            conn.readTimeout = 5000

            if (conn.responseCode != 200) return@withContext null

            val body = conn.inputStream.bufferedReader().use { it.readText() }
            conn.disconnect()

            // Simple JSON parse for syncedLyrics or plainLyrics
            val syncedLyrics = extractJsonField(body, "syncedLyrics")
            val plainLyrics = extractJsonField(body, "plainLyrics")

            when {
                !syncedLyrics.isNullOrBlank() -> parseLrc(syncedLyrics, LyricsSource.ONLINE)
                !plainLyrics.isNullOrBlank() -> parsePlainText(plainLyrics).copy(source = LyricsSource.ONLINE)
                else -> null
            }
        } catch (_: Exception) {
            null
        }
    }

    /**
     * Search LRCLIB for lyrics matches (Pro feature).
     */
    suspend fun searchLrcLib(query: String): List<LrcLibResult> = withContext(Dispatchers.IO) {
        try {
            val url = URL("https://lrclib.net/api/search?q=${URLEncoder.encode(query, "UTF-8")}")
            val conn = url.openConnection() as HttpURLConnection
            conn.setRequestProperty("User-Agent", "CIPHER Media Player/1.0")
            conn.connectTimeout = 5000

            if (conn.responseCode != 200) return@withContext emptyList()

            val body = conn.inputStream.bufferedReader().use { it.readText() }
            conn.disconnect()

            // Simplified array parse
            parseSearchResults(body)
        } catch (_: Exception) {
            emptyList()
        }
    }

    /**
     * Save lyrics as .lrc file next to the audio file.
     */
    suspend fun saveLrcFile(audioPath: String, lyrics: ParsedLyrics): Boolean = withContext(Dispatchers.IO) {
        try {
            val audioFile = File(audioPath)
            val lrcFile = File(audioFile.parent, "${audioFile.nameWithoutExtension}.lrc")
            val content = buildString {
                if (lyrics.title.isNotEmpty()) appendLine("[ti:${lyrics.title}]")
                if (lyrics.artist.isNotEmpty()) appendLine("[ar:${lyrics.artist}]")
                if (lyrics.album.isNotEmpty()) appendLine("[al:${lyrics.album}]")
                appendLine()
                lyrics.lines.forEach { line ->
                    if (line.timestampMs > 0) {
                        val min = line.timestampMs / 60_000
                        val sec = (line.timestampMs % 60_000) / 1_000
                        val ms = (line.timestampMs % 1_000) / 10
                        appendLine("[%02d:%02d.%02d]%s".format(min, sec, ms, line.text))
                    } else {
                        appendLine(line.text)
                    }
                }
            }
            lrcFile.writeText(content, Charsets.UTF_8)
            true
        } catch (_: Exception) {
            false
        }
    }

    /** Minimal JSON field extractor (avoids adding Gson/Moshi dependency) */
    private fun extractJsonField(json: String, field: String): String? {
        val pattern = """"$field"\s*:\s*"((?:[^"\\]|\\.)*)"""".toRegex()
        val match = pattern.find(json) ?: return null
        return match.groupValues[1]
            .replace("\\n", "\n")
            .replace("\\\"", "\"")
            .replace("\\\\", "\\")
    }

    private fun parseSearchResults(json: String): List<LrcLibResult> {
        val results = mutableListOf<LrcLibResult>()
        // Simple bracket-split approach for JSON arrays
        val items = json.split("},").map { it.trim().removePrefix("[").removeSuffix("]") + "}" }
        items.forEach { item ->
            val trackName = extractJsonField(item, "trackName") ?: return@forEach
            val artistName = extractJsonField(item, "artistName") ?: ""
            val albumName = extractJsonField(item, "albumName") ?: ""
            val synced = extractJsonField(item, "syncedLyrics")
            results.add(LrcLibResult(trackName, artistName, albumName, synced != null))
        }
        return results.take(10)
    }
}

data class LrcLibResult(
    val trackName: String,
    val artistName: String,
    val albumName: String,
    val hasSyncedLyrics: Boolean
)
