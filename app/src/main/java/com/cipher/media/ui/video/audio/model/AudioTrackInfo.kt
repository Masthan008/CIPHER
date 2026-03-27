package com.cipher.media.ui.video.audio.model

/**
 * Represents an ExoPlayer audio track selection.
 */
data class AudioTrackInfo(
    val groupIndex: Int,
    val trackIndex: Int,
    val language: String,
    val label: String,
    val codec: String,
    val sampleRate: Int,
    val channelCount: Int,
    val isSelected: Boolean
) {
    val displayTitle: String
        get() {
            val lang = if (language.isNotBlank() && language != "und") language.uppercase() else "Unknown"
            val layout = when (channelCount) {
                1 -> "Mono"
                2 -> "Stereo"
                6 -> "5.1 Surround"
                8 -> "7.1 Surround"
                else -> "$channelCount Channels"
            }
            return "$lang • $layout • ${sampleRate/1000}kHz"
        }
}
