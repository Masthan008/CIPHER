package com.cipher.media.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cipher.media.data.local.entity.MediaEntity

@Database(
    entities = [MediaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MediaDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
}
