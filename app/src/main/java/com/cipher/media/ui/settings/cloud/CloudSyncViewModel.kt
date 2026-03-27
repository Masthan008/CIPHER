package com.cipher.media.ui.settings.cloud

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cipher.media.billing.BillingRepository
import com.cipher.media.billing.model.SubscriptionTier
import com.cipher.media.cloud.CloudMetadataRepository
import com.cipher.media.cloud.SyncStatus
import com.cipher.media.cloud.VaultSyncEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CloudSyncUiState(
    val activeTier: SubscriptionTier = SubscriptionTier.FREE,
    val totalQuotaBytes: Long = VaultSyncEngine.QUOTA_FREE,
    val usedStorageBytes: Long = 0L,
    val syncStatus: SyncStatus = SyncStatus.Idle,
    val isLoading: Boolean = true
)

@HiltViewModel
class CloudSyncViewModel @Inject constructor(
    private val vaultSyncEngine: VaultSyncEngine,
    private val metadataRepository: CloudMetadataRepository,
    private val billingRepository: BillingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CloudSyncUiState())
    val uiState: StateFlow<CloudSyncUiState> = _uiState

    init {
        loadData()
        observeSyncStatus()
    }

    private fun loadData() {
        viewModelScope.launch {
            billingRepository.activeTier.collect { tier ->
                val used = metadataRepository.getSyncedStorageSize()
                val quota = when (tier) {
                    SubscriptionTier.FREE -> VaultSyncEngine.QUOTA_FREE
                    SubscriptionTier.PRO -> VaultSyncEngine.QUOTA_PRO
                    SubscriptionTier.POWER -> VaultSyncEngine.QUOTA_POWER
                }
                
                _uiState.value = _uiState.value.copy(
                    activeTier = tier,
                    usedStorageBytes = used,
                    totalQuotaBytes = quota,
                    isLoading = false
                )
            }
        }
    }

    private fun observeSyncStatus() {
        viewModelScope.launch {
            vaultSyncEngine.syncStatus.collect { status ->
                _uiState.value = _uiState.value.copy(syncStatus = status)
                if (status is SyncStatus.Success) {
                    // Refresh storage used after sync finishes
                    loadData()
                }
            }
        }
    }

    fun startSync() {
        viewModelScope.launch {
            vaultSyncEngine.startSync()
        }
    }
}
