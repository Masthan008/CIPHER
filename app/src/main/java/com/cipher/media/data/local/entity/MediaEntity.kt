package com.cipher.media.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for persisting media metadata.
 * Prepared for Phase 3 (Vault) — will store encrypted file references.
 */
@Entity(tableName = "media_items")
data class MediaEntity(
    @PrimaryKey
    val id: Long,
    val uri: String,
    val displayName: String,
    val duration: Long,
    val size: Long,
    val dateAdded: Long,
    val path: String,
    val mimeType: String = "",
    val isVaulted: Boolean = false,      // Phase 3: vault flag
    val encryptedPath: String? = null    // Phase 3: encrypted storage path
)
