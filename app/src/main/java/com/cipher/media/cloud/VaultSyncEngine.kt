package com.cipher.media.cloud

import com.cipher.media.billing.BillingRepository
import com.cipher.media.billing.model.SubscriptionTier
import com.cipher.media.data.local.VaultDao
import com.cipher.media.data.local.VaultFolderEntity
import com.cipher.media.data.local.VaultItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

sealed class SyncStatus {
    object Idle : SyncStatus()
    data class InProgress(val progressText: String, val percent: Float) : SyncStatus()
    data class Success(val message: String) : SyncStatus()
    data class Error(val reason: String) : SyncStatus()
    data class QuotaExceeded(val limitStr: String) : SyncStatus()
}

@Singleton
class VaultSyncEngine @Inject constructor(
    private val cloudinaryManager: CloudinaryManager,
    private val metadataRepository: CloudMetadataRepository,
    private val vaultDao: VaultDao,
    private val billingRepository: BillingRepository
) {
    private val _syncStatus = MutableStateFlow<SyncStatus>(SyncStatus.Idle)
    val syncStatus: StateFlow<SyncStatus> = _syncStatus

    companion object {
        const val QUOTA_FREE = 1L * 1024 * 1024 * 1024       // 1 GB
        const val QUOTA_PRO = 10L * 1024 * 1024 * 1024       // 10 GB
        const val QUOTA_POWER = Long.MAX_VALUE               // Unlimited
    }

    suspend fun startSync() = withContext(Dispatchers.IO) {
        try {
            _syncStatus.value = SyncStatus.InProgress("Checking quotas...", 0f)

            // 1. Determine Tier and Quota
            val tier = billingRepository.activeTier.first()
            val maxQuotaBytes = when (tier) {
                SubscriptionTier.FREE -> QUOTA_FREE
                SubscriptionTier.PRO -> QUOTA_PRO
                SubscriptionTier.POWER -> QUOTA_POWER
            }

            // 2. Calculate projected total size (Local Vault Size)
            // Assuming we sync everything in the local vault to the cloud.
            val localItemsList = vaultDao.getAllItems().first()
            val totalLocalBytes = localItemsList.sumOf { it.size }

            if (totalLocalBytes > maxQuotaBytes) {
                val limitStr = if (tier == SubscriptionTier.FREE) "1 GB (Free Tier)" else "10 GB (Pro Tier)"
                _syncStatus.value = SyncStatus.QuotaExceeded(limitStr)
                return@withContext
            }

            // 3. Sync Folders first
            val folders = vaultDao.getAllFolders().first()
            folders.forEachIndexed { index, folder ->
                _syncStatus.value = SyncStatus.InProgress("Syncing Folders...", index / folders.size.toFloat())
                metadataRepository.syncVaultFolder(folder)
            }

            // 4. Sync Items to Cloudinary
            localItemsList.forEachIndexed { index, item ->
                _syncStatus.value = SyncStatus.InProgress("Uploading ${item.originalName}...", index / localItemsList.size.toFloat())
                
                // Upload encrypted file blob to Cloudinary
                val localFile = File(item.encryptedPath)
                if (localFile.exists()) {
                    // Note: We'd normally check if it already exists in Firestore first to skip.
                    // For now, we attempt upload (Cloudinary handles overwrites if same ID, or we generate new ones).
                    val publicId = cloudinaryManager.uploadEncryptedBlob(localFile, "current_user")
                    if (publicId != null) {
                        // 5. Save metadata to Firestore
                        metadataRepository.syncVaultItem(item, publicId)
                    }
                }
            }

            _syncStatus.value = SyncStatus.Success("Vault synced successfully.")
        } catch (e: Exception) {
            e.printStackTrace()
            _syncStatus.value = SyncStatus.Error(e.localizedMessage ?: "Unknown error occurred.")
        }
    }
}
