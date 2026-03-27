package com.cipher.media.ui.audio.queue.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO for multi-queue CRUD operations.
 */
@Dao
interface QueueDao {

    // ── Queue CRUD ──

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQueue(queue: PlaybackQueueEntity)

    @Update
    suspend fun updateQueue(queue: PlaybackQueueEntity)

    @Delete
    suspend fun deleteQueue(queue: PlaybackQueueEntity)

    @Query("SELECT * FROM playback_queues ORDER BY updatedAt DESC")
    fun getAllQueues(): Flow<List<PlaybackQueueEntity>>

    @Query("SELECT * FROM playback_queues WHERE id = :id")
    suspend fun getQueueById(id: String): PlaybackQueueEntity?

    @Query("SELECT * FROM playback_queues WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveQueue(): PlaybackQueueEntity?

    @Query("UPDATE playback_queues SET isActive = 0")
    suspend fun deactivateAll()

    @Query("UPDATE playback_queues SET isActive = 1 WHERE id = :id")
    suspend fun setActive(id: String)

    @Query("SELECT COUNT(*) FROM playback_queues")
    suspend fun getQueueCount(): Int

    @Query("UPDATE playback_queues SET currentIndex = :index, currentPositionMs = :positionMs, updatedAt = :updatedAt WHERE id = :queueId")
    suspend fun updatePlaybackPosition(queueId: String, index: Int, positionMs: Long, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE playback_queues SET playbackMode = :mode WHERE id = :queueId")
    suspend fun updatePlaybackMode(queueId: String, mode: String)

    // ── Queue Items ──

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<QueueItemEntity>)

    @Query("DELETE FROM queue_items WHERE queueId = :queueId")
    suspend fun clearItems(queueId: String)

    @Query("SELECT * FROM queue_items WHERE queueId = :queueId ORDER BY position ASC")
    suspend fun getItemsForQueue(queueId: String): List<QueueItemEntity>

    @Query("SELECT * FROM queue_items WHERE queueId = :queueId ORDER BY position ASC")
    fun observeItemsForQueue(queueId: String): Flow<List<QueueItemEntity>>

    @Query("DELETE FROM queue_items WHERE id = :itemId")
    suspend fun deleteItem(itemId: Long)

    // ── Transaction: Replace all items in a queue ──
    @Transaction
    suspend fun replaceQueueItems(queueId: String, items: List<QueueItemEntity>) {
        clearItems(queueId)
        insertItems(items)
    }
}
