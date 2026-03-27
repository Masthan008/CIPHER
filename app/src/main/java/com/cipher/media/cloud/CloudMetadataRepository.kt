package com.cipher.media.cloud

import com.cipher.media.data.local.VaultFolderEntity
import com.cipher.media.data.local.VaultItemEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudMetadataRepository @Inject constructor() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val userId: String?
        get() = auth.currentUser?.uid

    /**
     * Syncs a local VaultItem metadata to Firestore, attaching the Cloudinary publicId.
     */
    suspend fun syncVaultItem(item: VaultItemEntity, cloudinaryPublicId: String) {
        val uid = userId ?: return
        val docRef = firestore.collection("users").document(uid)
            .collection("vault_items").document(item.id)
            
        val data = mapOf(
            "id" to item.id,
            "originalName" to item.originalName,
            "fileType" to item.fileType,
            "size" to item.size,
            "addedDate" to item.addedDate,
            "folderId" to item.folderId,
            "cloudinaryPublicId" to cloudinaryPublicId,
            "lastSynced" to System.currentTimeMillis()
        )
        
        docRef.set(data).await()
    }

    /**
     * Removes an item's metadata from Firestore.
     */
    suspend fun removeVaultItem(itemId: String) {
        val uid = userId ?: return
        firestore.collection("users").document(uid)
            .collection("vault_items").document(itemId)
            .delete().await()
    }

    /**
     * Syncs a Vault Folder structure to Firestore.
     */
    suspend fun syncVaultFolder(folder: VaultFolderEntity) {
        val uid = userId ?: return
        val docRef = firestore.collection("users").document(uid)
            .collection("vault_folders").document(folder.id)
            
        val data = mapOf(
            "id" to folder.id,
            "name" to folder.name,
            "createdAt" to folder.createdAt,
            "lastSynced" to System.currentTimeMillis()
        )
        
        docRef.set(data).await()
    }

    /**
     * Calculates total bytes synced in Firestore by summing the `size` field of all synced items.
     * This is used to enforce tier quotas before attempting a large upload.
     */
    suspend fun getSyncedStorageSize(): Long {
        val uid = userId ?: return 0L
        return try {
            val snapshot = firestore.collection("users").document(uid)
                .collection("vault_items").get().await()
            
            snapshot.documents.sumOf { it.getLong("size") ?: 0L }
        } catch (e: Exception) {
            e.printStackTrace()
            0L
        }
    }
}
