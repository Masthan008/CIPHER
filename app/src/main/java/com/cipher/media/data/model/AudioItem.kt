package com.cipher.media.data.model

import android.net.Uri

/**
 * Domain-level audio item representing a single audio track.
 */
data class AudioItem(
    val id: Long,
    val uri: Uri,
    val title: String,
    val artist: String,
    val album: String,
    val albumArtUri: Uri?,
    val duration: Long,        // milliseconds
    val size: Long,            // bytes
    val dateAdded: Long,       // epoch seconds
    val path: String,
    val mimeType: String = "",
    val trackNumber: Int = 0
) {
    val formattedSize: String
        get() = when {
            size >= 1_073_741_824 -> "%.1f GB".format(size / 1_073_741_824.0)
            size >= 1_048_576 -> "%.1f MB".format(size / 1_048_576.0)
            size >= 1024 -> "%.0f KB".format(size / 1024.0)
            else -> "$size B"
        }
}
