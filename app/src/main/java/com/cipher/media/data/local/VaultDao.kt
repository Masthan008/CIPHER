package com.cipher.media.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Room entities for vault items and folders.
 */
@Entity(tableName = "vault_items")
data class VaultItemEntity(
    @PrimaryKey val id: String,
    val originalName: String,
    val encryptedPath: String,
    val fileType: String,      // IMAGE, VIDEO, AUDIO, OTHER
    val size: Long,
    val addedDate: Long,
    val folderId: String? = null
)

@Entity(tableName = "vault_folders")
data class VaultFolderEntity(
    @PrimaryKey val id: String,
    val name: String,
    val createdAt: Long
)

/**
 * DAO for vault items and folders.
 */
@Dao
interface VaultDao {

    // -- Items --
    @Query("SELECT * FROM vault_items ORDER BY addedDate DESC")
    fun getAllItems(): Flow<List<VaultItemEntity>>

    @Query("SELECT * FROM vault_items WHERE folderId = :folderId ORDER BY addedDate DESC")
    fun getItemsByFolder(folderId: String): Flow<List<VaultItemEntity>>

    @Query("SELECT * FROM vault_items WHERE id = :id")
    suspend fun getItemById(id: String): VaultItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: VaultItemEntity)

    @Delete
    suspend fun deleteItem(item: VaultItemEntity)

    @Query("DELETE FROM vault_items WHERE id = :id")
    suspend fun deleteItemById(id: String)

    @Query("UPDATE vault_items SET folderId = :folderId WHERE id = :itemId")
    suspend fun moveItemToFolder(itemId: String, folderId: String?)

    @Query("SELECT COUNT(*) FROM vault_items")
    fun getItemCount(): Flow<Int>

    // -- Folders --
    @Query("SELECT * FROM vault_folders ORDER BY name ASC")
    fun getAllFolders(): Flow<List<VaultFolderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder: VaultFolderEntity)

    @Delete
    suspend fun deleteFolder(folder: VaultFolderEntity)

    @Query("DELETE FROM vault_folders WHERE id = :id")
    suspend fun deleteFolderById(id: String)

    @Query("DELETE FROM vault_items")
    suspend fun deleteAllItems()

    @Query("DELETE FROM vault_folders")
    suspend fun deleteAllFolders()
}
