package com.cipher.media.data.model

import android.net.Uri

/**
 * Domain-level media item used across the app.
 * This is separate from the Room entity to keep layers clean.
 */
data class MediaItem(
    val id: Long,
    val uri: Uri,
    val displayName: String,
    val duration: Long,       // in milliseconds
    val size: Long,           // in bytes
    val dateAdded: Long,      // epoch seconds
    val path: String,
    val mimeType: String = "",
    val width: Int = 0,
    val height: Int = 0
) {
    /** Formatted file size (e.g., "1.2 GB") */
    val formattedSize: String
        get() = when {
            size >= 1_073_741_824 -> "%.1f GB".format(size / 1_073_741_824.0)
            size >= 1_048_576 -> "%.1f MB".format(size / 1_048_576.0)
            size >= 1024 -> "%.0f KB".format(size / 1024.0)
            else -> "$size B"
        }
}
