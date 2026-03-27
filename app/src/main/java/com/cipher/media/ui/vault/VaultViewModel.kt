package com.cipher.media.ui.vault

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipher.media.data.encryption.VaultEncryptionManager
import com.cipher.media.data.local.VaultDao
import com.cipher.media.data.local.VaultFolderEntity
import com.cipher.media.data.local.VaultItemEntity
import com.cipher.media.data.model.VaultFolder
import com.cipher.media.data.model.VaultItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class VaultViewModel @Inject constructor(
    private val vaultDao: VaultDao,
    private val encryptionManager: VaultEncryptionManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val vaultItems: StateFlow<List<VaultItem>> = vaultDao.getAllItems()
        .map { entities -> entities.map { it.toDomain() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val folders: StateFlow<List<VaultFolder>> = vaultDao.getAllFolders()
        .map { entities -> entities.map { VaultFolder(it.id, it.name, it.createdAt) } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val itemCount: StateFlow<Int> = vaultDao.getItemCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    private val _selectedFolderId = MutableStateFlow<String?>(null)
    val selectedFolderId: StateFlow<String?> = _selectedFolderId.asStateFlow()

    val filteredItems: StateFlow<List<VaultItem>> = combine(
        vaultItems, _selectedFolderId
    ) { items, folderId ->
        if (folderId == null) items else items.filter { it.folderId == folderId }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _isImporting = MutableStateFlow(false)
    val isImporting: StateFlow<Boolean> = _isImporting.asStateFlow()

    fun selectFolder(folderId: String?) {
        _selectedFolderId.value = folderId
    }

    fun importFile(uri: Uri, deleteOriginal: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            _isImporting.value = true
            try {
                val fileName = getFileName(uri) ?: "unknown"
                val fileSize = getFileSize(uri)
                val fileType = detectFileType(fileName)

                val encryptedPath = encryptionManager.importFile(uri, fileName, deleteOriginal)

                val item = VaultItemEntity(
                    id = UUID.randomUUID().toString(),
                    originalName = fileName,
                    encryptedPath = encryptedPath,
                    fileType = fileType.name,
                    size = fileSize,
                    addedDate = System.currentTimeMillis(),
                    folderId = _selectedFolderId.value
                )
                vaultDao.insertItem(item)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isImporting.value = false
            }
        }
    }

    fun deleteItem(item: VaultItem) {
        viewModelScope.launch(Dispatchers.IO) {
            encryptionManager.deleteVaultFile(item.encryptedPath)
            vaultDao.deleteItemById(item.id)
        }
    }

    fun createFolder(name: String) {
        viewModelScope.launch {
            vaultDao.insertFolder(
                VaultFolderEntity(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    createdAt = System.currentTimeMillis()
                )
            )
        }
    }

    fun deleteFolder(folderId: String) {
        viewModelScope.launch {
            vaultDao.deleteFolderById(folderId)
        }
    }

    /**
     * Decrypts a vault item to bytes (for images).
     */
    fun decryptToBytes(item: VaultItem): ByteArray {
        return encryptionManager.decryptToBytes(item.encryptedPath)
    }

    /**
     * Decrypts a vault item to a temp file (for video playback).
     */
    fun decryptToTempFile(item: VaultItem): java.io.File {
        val ext = when (item.fileType) {
            VaultItem.FileType.VIDEO -> ".mp4"
            VaultItem.FileType.IMAGE -> ".jpg"
            else -> ".tmp"
        }
        return encryptionManager.decryptToTempFile(item.encryptedPath, ext)
    }

    fun cleanupTemp() {
        encryptionManager.cleanupTempFiles()
    }

    private fun getFileName(uri: Uri): String? {
        return context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            if (nameIndex >= 0) cursor.getString(nameIndex) else null
        }
    }

    private fun getFileSize(uri: Uri): Long {
        return context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            cursor.moveToFirst()
            if (sizeIndex >= 0) cursor.getLong(sizeIndex) else 0L
        } ?: 0L
    }

    private fun detectFileType(fileName: String): VaultItem.FileType {
        val ext = fileName.substringAfterLast('.', "").lowercase()
        return when (ext) {
            "jpg", "jpeg", "png", "gif", "webp", "bmp" -> VaultItem.FileType.IMAGE
            "mp4", "mkv", "avi", "mov", "webm", "3gp" -> VaultItem.FileType.VIDEO
            "mp3", "wav", "flac", "aac", "ogg", "m4a" -> VaultItem.FileType.AUDIO
            else -> VaultItem.FileType.OTHER
        }
    }

    private fun VaultItemEntity.toDomain() = VaultItem(
        id = id,
        originalName = originalName,
        encryptedPath = encryptedPath,
        fileType = VaultItem.FileType.valueOf(fileType),
        size = size,
        addedDate = addedDate,
        folderId = folderId
    )
}
