package com.cipher.media.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for "Per-Video Memory" feature (Power Tier).
 * Stores playback settings (speed, subtitle sync, crop mode) per video URI.
 */
@Entity(tableName = "video_preferences")
data class VideoPreferencesEntity(
    @PrimaryKey
    val videoUri: String,
    
    // Playback Speed
    val playbackSpeed: Float = 1.0f,
    val pitchCorrection: Boolean = true,
    
    // Subtitles
    val subtitleSyncOffsetMs: Long = 0L,
    val selectedSubtitleTrackId: String? = null,
    
    // Audio
    val selectedAudioTrackId: String? = null,
    val audioDelayMs: Long = 0L,
    
    // Video Transform
    val cropMode: String = "FIT",
    val zoomLevel: Float = 1.0f,
    val panX: Float = 0f,
    val panY: Float = 0f,
    
    // A-B Repeat
    val pointA: Long = -1L,
    val pointB: Long = -1L,
    
    // Resume Playback
    val lastPlaybackPositionMs: Long = 0L,
    
    // Update Tracking
    val lastUpdatedTimestamp: Long = System.currentTimeMillis()
)
