package com.cipher.media.ui.audio.queue

import com.cipher.media.data.model.AudioItem
import com.cipher.media.ui.audio.queue.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

/**
 * Repository for multi-queue CRUD operations.
 * Bridges Room DAO with domain models.
 */
class QueueRepository(private val dao: QueueDao) {

    companion object {
        const val FREE_QUEUE_LIMIT = 5
        const val PRO_QUEUE_LIMIT = 20
        const val DEFAULT_QUEUE_NAME = "Now Playing"
    }

    /** Observe all queues (metadata only, no items). */
    val allQueues: Flow<List<PlaybackQueueEntity>> = dao.getAllQueues()

    /** Get queue count for tier-gating. */
    suspend fun getQueueCount(): Int = dao.getQueueCount()

    /** Create a new queue. Returns null if tier limit reached. */
    suspend fun createQueue(
        name: String,
        iconName: String = "queue_music",
        isPro: Boolean = false
    ): PlaybackQueueEntity? {
        val limit = if (isPro) PRO_QUEUE_LIMIT else FREE_QUEUE_LIMIT
        if (getQueueCount() >= limit) return null

        val queue = PlaybackQueueEntity(
            id = UUID.randomUUID().toString(),
            name = name,
            iconName = iconName
        )
        dao.insertQueue(queue)
        return queue
    }

    /** Delete a queue and its items (CASCADE). */
    suspend fun deleteQueue(queueId: String) {
        val queue = dao.getQueueById(queueId) ?: return
        dao.deleteQueue(queue)
    }

    /** Rename a queue. */
    suspend fun renameQueue(queueId: String, newName: String) {
        val queue = dao.getQueueById(queueId) ?: return
        dao.updateQueue(queue.copy(name = newName, updatedAt = System.currentTimeMillis()))
    }

    /** Duplicate a queue (copies items). */
    suspend fun duplicateQueue(queueId: String, isPro: Boolean): PlaybackQueueEntity? {
        val source = dao.getQueueById(queueId) ?: return null
        val items = dao.getItemsForQueue(queueId)

        val newQueue = createQueue("${source.name} (copy)", source.iconName, isPro) ?: return null

        val newItems = items.map { it.copy(id = 0, queueId = newQueue.id) }
        dao.insertItems(newItems)
        return newQueue
    }

    /** Switch active queue. */
    suspend fun switchQueue(queueId: String) {
        dao.deactivateAll()
        dao.setActive(queueId)
    }

    /** Get the currently active queue with items. */
    suspend fun getActiveQueue(): PlaybackQueue? {
        val entity = dao.getActiveQueue() ?: return null
        return loadFullQueue(entity)
    }

    /** Get a specific queue with all its items. */
    suspend fun getQueue(queueId: String): PlaybackQueue? {
        val entity = dao.getQueueById(queueId) ?: return null
        return loadFullQueue(entity)
    }

    /** Save playback position for active queue. */
    suspend fun savePosition(queueId: String, index: Int, positionMs: Long) {
        dao.updatePlaybackPosition(queueId, index, positionMs)
    }

    /** Update playback mode. */
    suspend fun setPlaybackMode(queueId: String, mode: PlaybackMode) {
        dao.updatePlaybackMode(queueId, mode.name)
    }

    /** Replace all items in a queue. */
    suspend fun setQueueItems(queueId: String, audioItems: List<AudioItem>) {
        val entities = audioItems.mapIndexed { index, audio ->
            QueueItemEntity(
                queueId = queueId,
                position = index,
                uri = audio.uri.toString(),
                title = audio.title,
                artist = audio.artist,
                album = audio.album,
                albumArtUri = audio.albumArtUri?.toString(),
                durationMs = audio.duration
            )
        }
        dao.replaceQueueItems(queueId, entities)
    }

    /** Add a single track to a queue. */
    suspend fun addToQueue(queueId: String, audio: AudioItem) {
        val existing = dao.getItemsForQueue(queueId)
        val newItem = QueueItemEntity(
            queueId = queueId,
            position = existing.size,
            uri = audio.uri.toString(),
            title = audio.title,
            artist = audio.artist,
            album = audio.album,
            albumArtUri = audio.albumArtUri?.toString(),
            durationMs = audio.duration
        )
        dao.insertItems(listOf(newItem))
    }

    /** Remove a track from a queue. */
    suspend fun removeFromQueue(itemId: Long) {
        dao.deleteItem(itemId)
    }

    /** Ensure a default queue exists. */
    suspend fun ensureDefaultQueue() {
        if (getQueueCount() == 0) {
            val queue = PlaybackQueueEntity(
                id = UUID.randomUUID().toString(),
                name = DEFAULT_QUEUE_NAME,
                isActive = true
            )
            dao.insertQueue(queue)
        }
    }

    private suspend fun loadFullQueue(entity: PlaybackQueueEntity): PlaybackQueue {
        val items = dao.getItemsForQueue(entity.id)
        return PlaybackQueue(
            id = entity.id,
            name = entity.name,
            iconName = entity.iconName,
            items = items,
            currentIndex = entity.currentIndex,
            currentPositionMs = entity.currentPositionMs,
            playbackMode = try { PlaybackMode.valueOf(entity.playbackMode) } catch (_: Exception) { PlaybackMode.STANDARD },
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt,
            isActive = entity.isActive
        )
    }
}
