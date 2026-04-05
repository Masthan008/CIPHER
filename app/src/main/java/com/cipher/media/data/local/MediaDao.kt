package com.cipher.media.data.local

import androidx.room.*
import com.cipher.media.data.local.entity.MediaEntity

/**
 * Data Access Object for media items.
 * Currently a stub for Phase 3 (Vault feature).
 */
@Dao
interface MediaDao {

  @Query("SELECT * FROM media_items ORDER BY dateAdded DESC")
  suspend fun getAllMedia(): List<MediaEntity>

  @Query("SELECT * FROM media_items WHERE isVaulted = 1 ORDER BY dateAdded DESC")
  suspend fun getVaultedMedia(): List<MediaEntity>

  @Query("SELECT * FROM media_items WHERE isFavorite = 1 ORDER BY dateAdded DESC")
  suspend fun getFavoriteMedia(): List<MediaEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertMedia(media: MediaEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(media: List<MediaEntity>)

  @Delete
  suspend fun deleteMedia(media: MediaEntity)

  @Query("DELETE FROM media_items WHERE id = :id")
  suspend fun deleteById(id: Long)

  @Query("UPDATE media_items SET isFavorite = :isFavorite WHERE id = :id")
  suspend fun setFavorite(id: Long, isFavorite: Boolean)

  @Query("SELECT isFavorite FROM media_items WHERE id = :id")
  suspend fun isFavorite(id: Long): Boolean?
}
