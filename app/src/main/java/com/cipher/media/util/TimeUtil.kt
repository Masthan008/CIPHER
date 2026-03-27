package com.cipher.media.util

/**
 * Utility for formatting time durations.
 */
object TimeUtil {

    /**
     * Formats milliseconds to HH:MM:SS or MM:SS string.
     * @param millis Duration in milliseconds
     * @return Formatted time string
     */
    fun formatDuration(millis: Long): String {
        if (millis <= 0) return "00:00"

        val totalSeconds = millis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        return if (hours > 0) {
            "%d:%02d:%02d".format(hours, minutes, seconds)
        } else {
            "%02d:%02d".format(minutes, seconds)
        }
    }

    /**
     * Formats a seek delta for display (e.g., "+10s" or "-5s").
     */
    fun formatSeekDelta(deltaMs: Long): String {
        val seconds = deltaMs / 1000
        return if (seconds >= 0) "+${seconds}s" else "${seconds}s"
    }
}
