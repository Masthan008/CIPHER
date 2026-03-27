package com.cipher.media.data.model

/**
 * Records a break-in attempt: front camera photo, GPS, timestamp.
 */
data class IntruderLog(
    val id: String,
    val photoPath: String?,     // Encrypted photo path in app-private storage
    val latitude: Double?,
    val longitude: Double?,
    val timestamp: Long,        // Epoch millis
    val attemptCount: Int,
    val pinEntered: String = "" // What they tried (hashed)
)
