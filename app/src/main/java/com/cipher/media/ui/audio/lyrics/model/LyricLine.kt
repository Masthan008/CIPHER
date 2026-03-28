package com.cipher.media.ui.audio.lyrics.model

/**
 * Single line from an LRC file.
 * @param timestampMs position in milliseconds
 * @param text the lyric text
 */
data class LyricLine(
    val timestampMs: Long,
    val text: String
) : Comparable<LyricLine> {
    override fun compareTo(other: LyricLine): Int = timestampMs.compareTo(other.timestampMs)

    companion object {
        /** Parse "[mm:ss.xx] text" format */
        fun parse(raw: String): LyricLine? {
            val regex = Regex("""\[(\d{2}):(\d{2})\.(\d{2,3})](.*)""")
            val match = regex.find(raw.trim()) ?: return null
            val (min, sec, ms, text) = match.destructured
            val millis = min.toLong() * 60_000 +
                    sec.toLong() * 1_000 +
                    ms.padEnd(3, '0').toLong()
            return LyricLine(millis, text.trim())
        }
    }
}

/**
 * Parsed lyrics container.
 */
data class ParsedLyrics(
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val lines: List<LyricLine> = emptyList(),
    val source: LyricsSource = LyricsSource.NONE
) {
    val isSync get() = lines.any { it.timestampMs > 0 }
    val plainText get() = lines.joinToString("\n") { it.text }
}

enum class LyricsSource {
    NONE,
    EMBEDDED,
    LOCAL_LRC,
    ONLINE,
    MANUAL
}
