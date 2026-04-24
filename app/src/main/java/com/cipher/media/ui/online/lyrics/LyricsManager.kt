package com.cipher.media.ui.online.lyrics

import android.util.LruCache
import com.cipher.media.data.online.jamendo.JamendoApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manager for fetching and parsing lyrics from Jamendo API.
 */
@Singleton
class LyricsManager @Inject constructor(
    private val jamendoApi: JamendoApi
) {
    private val lyricsCache = LruCache<String, String>(50)

    suspend fun fetchLyrics(trackId: String): String? {
        // Check cache first
        lyricsCache.get(trackId)?.let { return it }

        // Fetch from API
        return jamendoApi.getLyrics(trackId)?.also { lyrics ->
            lyricsCache.put(trackId, lyrics)
        }
    }

    /**
     * Parse lyrics text into list of timed lines.
     * Supports LRC format [mm:ss.xx] or plain text.
     */
    fun parseLyrics(lyricsText: String?): List<LyricLine> {
        if (lyricsText.isNullOrBlank()) return emptyList()

        // Check if it's LRC format [mm:ss.xx]
        val lrcPattern = "\\[(\\d{2}):(\\d{2})\\.(\\d{2,3})\\](.*)".toRegex()

        return if (lrcPattern.containsMatchIn(lyricsText)) {
            // Parse LRC format
            lyricsText.lines().mapNotNull { line ->
                val match = lrcPattern.find(line)
                match?.let {
                    val (min, sec, ms, text) = it.destructured
                    val timeMs = (min.toInt() * 60 + sec.toInt()) * 1000L +
                                ms.padEnd(3, '0').toLong()
                    LyricLine(timeMs, text.trim())
                }
            }.sortedBy { it.timeMs }
        } else {
            // Plain text - split by lines, estimate timestamps
            val lines = lyricsText.lines().filter { it.isNotBlank() }
            val estimatedDurationPerLine = 5000L // 5 seconds per line estimate
            lines.mapIndexed { index, line ->
                LyricLine(index * estimatedDurationPerLine, line.trim())
            }
        }
    }
}

/**
 * Represents a single line of lyrics with timestamp.
 */
data class LyricLine(
    val timeMs: Long,
    val text: String
)
