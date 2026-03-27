package com.cipher.media.ui.audio.queue.model

import androidx.room.*

/**
 * Room entity for a playback queue.
 * FREE: Up to 5 queues, PRO: Up to 20 queues.
 */
@Entity(tableName = "playback_queues")
data class PlaybackQueueEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val iconName: String = "queue_music", // Material icon name
    val currentIndex: Int = 0,
    val currentPositionMs: Long = 0L,
    val playbackMode: String = "STANDARD", // STANDARD, SHUFFLE, REPEAT
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = false
)

/**
 * Room entity for items within a queue.
 */
@Entity(
    tableName = "queue_items",
    foreignKeys = [ForeignKey(
        entity = PlaybackQueueEntity::class,
        parentColumns = ["id"],
        childColumns = ["queueId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("queueId")]
)
data class QueueItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val queueId: String,
    val position: Int,  // Order in queue
    val uri: String,
    val title: String,
    val artist: String,
    val album: String = "",
    val albumArtUri: String? = null,
    val durationMs: Long = 0L
)

/**
 * Playback mode enum.
 */
enum class PlaybackMode {
    STANDARD, SHUFFLE, REPEAT
}

/**
 * Combined queue with items for UI display.
 */
data class PlaybackQueue(
    val id: String,
    val name: String,
    val iconName: String,
    val items: List<QueueItemEntity>,
    val currentIndex: Int,
    val currentPositionMs: Long,
    val playbackMode: PlaybackMode,
    val createdAt: Long,
    val updatedAt: Long,
    val isActive: Boolean
) {
    val trackCount: Int get() = items.size
    val currentTrack: QueueItemEntity? get() = items.getOrNull(currentIndex)
}
