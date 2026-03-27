package com.cipher.media.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cipher.media.data.local.entity.VideoPreferencesEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for per-video memory preferences.
 */
@Dao
interface VideoPreferencesDao {

    @Query("SELECT * FROM video_preferences WHERE videoUri = :uri")
    fun getPreferencesAsFlow(uri: String): Flow<VideoPreferencesEntity?>

    @Query("SELECT * FROM video_preferences WHERE videoUri = :uri")
    suspend fun getPreferences(uri: String): VideoPreferencesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(preferences: VideoPreferencesEntity)

    @Query("DELETE FROM video_preferences WHERE videoUri = :uri")
    suspend fun deletePreferences(uri: String)

    @Query("UPDATE video_preferences SET lastPlaybackPositionMs = :position, lastUpdatedTimestamp = :timestamp WHERE videoUri = :uri")
    suspend fun updatePlaybackPosition(uri: String, position: Long, timestamp: Long = System.currentTimeMillis())
}
