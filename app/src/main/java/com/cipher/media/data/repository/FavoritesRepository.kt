package com.cipher.media.data.repository

import com.cipher.media.data.local.MediaDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing favorite media items.
 * Handles persistence of favorite status to Room database.
 */
@Singleton
class FavoritesRepository @Inject constructor(
  private val mediaDao: MediaDao
) {

  /**
   * Toggles the favorite status of a media item.
   * Inserts the item if it doesn't exist.
   * @return The new favorite status
   */
  suspend fun toggleFavorite(audioItem: com.cipher.media.data.model.AudioItem): Boolean = withContext(Dispatchers.IO) {
    val currentFavorite = mediaDao.isFavorite(audioItem.id) ?: false
    if (!currentFavorite) {
        val entity = com.cipher.media.data.local.entity.MediaEntity(
            id = audioItem.id,
            uri = audioItem.uri.toString(),
            displayName = audioItem.title,
            duration = audioItem.duration,
            size = audioItem.size,
            dateAdded = audioItem.dateAdded,
            path = audioItem.path,
            mimeType = audioItem.mimeType,
            isVaulted = false,
            isFavorite = true,
            encryptedPath = null
        )
        mediaDao.insertMedia(entity)
        true
    } else {
        mediaDao.setFavorite(audioItem.id, false)
        false
    }
  }

  /**
   * Sets the favorite status of a media item.
   */
  suspend fun setFavorite(mediaId: Long, isFavorite: Boolean) = withContext(Dispatchers.IO) {
    mediaDao.setFavorite(mediaId, isFavorite)
  }

  /**
   * Checks if a media item is favorited.
   */
  suspend fun isFavorite(mediaId: Long): Boolean = withContext(Dispatchers.IO) {
    mediaDao.isFavorite(mediaId) ?: false
  }

  /**
   * Gets all favorite media items.
   */
  suspend fun getFavoriteMediaIds(): Set<Long> = withContext(Dispatchers.IO) {
    mediaDao.getFavoriteMedia().map { it.id }.toSet()
  }
}

