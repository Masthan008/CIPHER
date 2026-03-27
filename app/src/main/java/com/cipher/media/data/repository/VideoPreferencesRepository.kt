package com.cipher.media.data.repository

import com.cipher.media.data.local.VideoPreferencesDao
import com.cipher.media.data.local.entity.VideoPreferencesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoPreferencesRepository @Inject constructor(
    private val dao: VideoPreferencesDao
) {

    fun getPreferencesAsFlow(videoUri: String): Flow<VideoPreferencesEntity?> {
        return dao.getPreferencesAsFlow(videoUri)
    }

    suspend fun getPreferences(videoUri: String): VideoPreferencesEntity? {
        return dao.getPreferences(videoUri)
    }

    suspend fun savePreferences(preferences: VideoPreferencesEntity) {
        dao.insertOrUpdate(preferences.copy(lastUpdatedTimestamp = System.currentTimeMillis()))
    }

    /**
     * Helper to quickly update playback position on pause.
     */
    suspend fun updatePlaybackPosition(videoUri: String, positionMs: Long) {
        dao.updatePlaybackPosition(videoUri, positionMs)
    }

    suspend fun clearPreferences(videoUri: String) {
        dao.deletePreferences(videoUri)
    }
}
