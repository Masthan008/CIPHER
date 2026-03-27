package com.cipher.media.data.model

/**
 * A folder within the vault for organizing files.
 */
data class VaultFolder(
    val id: String,
    val name: String,
    val createdAt: Long = System.currentTimeMillis()
)
