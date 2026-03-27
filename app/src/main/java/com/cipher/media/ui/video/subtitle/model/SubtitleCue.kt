package com.cipher.media.ui.video.subtitle.model

/**
 * A single subtitle cue with timing and text content.
 * Used across all subtitle formats (SRT, ASS, VTT).
 */
data class SubtitleCue(
    val index: Int,
    val startTimeMs: Long,   // Start time in milliseconds
    val endTimeMs: Long,     // End time in milliseconds
    val text: String         // Subtitle text (HTML tags stripped)
) {
    /**
     * Check if this cue should be displayed at the given playback position.
     * @param positionMs Current playback position in milliseconds
     * @param syncOffsetMs User-configured sync offset in milliseconds
     */
    fun isActiveAt(positionMs: Long, syncOffsetMs: Long = 0L): Boolean {
        val adjustedStart = startTimeMs + syncOffsetMs
        val adjustedEnd = endTimeMs + syncOffsetMs
        return positionMs in adjustedStart..adjustedEnd
    }
}

/**
 * Metadata about a loaded subtitle track.
 */
data class SubtitleTrack(
    val fileName: String,
    val format: SubtitleFormat,
    val cueCount: Int,
    val language: String? = null,
    val filePath: String? = null
)

/**
 * Supported subtitle formats.
 */
enum class SubtitleFormat(val extensions: List<String>) {
    SRT(listOf("srt")),
    ASS(listOf("ass", "ssa")),
    VTT(listOf("vtt")),
    UNKNOWN(listOf());

    companion object {
        fun fromFileName(name: String): SubtitleFormat {
            val ext = name.substringAfterLast('.', "").lowercase()
            return entries.find { ext in it.extensions } ?: UNKNOWN
        }
    }
}
