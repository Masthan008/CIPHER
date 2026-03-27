package com.cipher.media.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cipher.media.data.local.entity.MediaEntity
import com.cipher.media.data.local.entity.VideoPreferencesEntity
import com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity
import com.cipher.media.ui.audio.queue.model.QueueItemEntity
import com.cipher.media.ui.audio.queue.model.QueueDao

@Database(
    entities = [
        MediaEntity::class,
        VideoPreferencesEntity::class,
        PlaybackQueueEntity::class,
        QueueItemEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class MediaDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
    abstract fun videoPreferencesDao(): VideoPreferencesDao
    abstract fun queueDao(): QueueDao
}
