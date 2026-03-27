package com.cipher.media.data.model

/**
 * Represents an encrypted file stored in the vault.
 */
data class VaultItem(
    val id: String,            // UUID
    val originalName: String,  // Original filename
    val encryptedPath: String, // Relative path within vault dir
    val fileType: FileType,
    val size: Long,            // Original file size in bytes
    val addedDate: Long,       // Epoch millis
    val folderId: String? = null
) {
    enum class FileType {
        IMAGE, VIDEO, AUDIO, OTHER
    }

    val formattedSize: String
        get() = when {
            size >= 1_073_741_824 -> "%.1f GB".format(size / 1_073_741_824.0)
            size >= 1_048_576 -> "%.1f MB".format(size / 1_048_576.0)
            size >= 1024 -> "%.0f KB".format(size / 1024.0)
            else -> "$size B"
        }
}
