package com.cipher.media.data.model

/**
 * Basic playlist container.
 */
data class Playlist(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val audioIds: List<Long> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
)
