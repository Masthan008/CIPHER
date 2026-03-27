package com.cipher.media.ui.settings.cloud;

import androidx.lifecycle.ViewModel;
import com.cipher.media.billing.BillingRepository;
import com.cipher.media.billing.model.SubscriptionTier;
import com.cipher.media.cloud.CloudMetadataRepository;
import com.cipher.media.cloud.SyncStatus;
import com.cipher.media.cloud.VaultSyncEngine;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0010\u001a\u00020\u0011H\u0002J\b\u0010\u0012\u001a\u00020\u0011H\u0002J\u0006\u0010\u0013\u001a\u00020\u0011R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/cipher/media/ui/settings/cloud/CloudSyncViewModel;", "Landroidx/lifecycle/ViewModel;", "vaultSyncEngine", "Lcom/cipher/media/cloud/VaultSyncEngine;", "metadataRepository", "Lcom/cipher/media/cloud/CloudMetadataRepository;", "billingRepository", "Lcom/cipher/media/billing/BillingRepository;", "(Lcom/cipher/media/cloud/VaultSyncEngine;Lcom/cipher/media/cloud/CloudMetadataRepository;Lcom/cipher/media/billing/BillingRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/ui/settings/cloud/CloudSyncUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadData", "", "observeSyncStatus", "startSync", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CloudSyncViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.cloud.VaultSyncEngine vaultSyncEngine = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.cloud.CloudMetadataRepository metadataRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.billing.BillingRepository billingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.ui.settings.cloud.CloudSyncUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.settings.cloud.CloudSyncUiState> uiState = null;
    
    @javax.inject.Inject()
    public CloudSyncViewModel(@org.jetbrains.annotations.NotNull()
    com.cipher.media.cloud.VaultSyncEngine vaultSyncEngine, @org.jetbrains.annotations.NotNull()
    com.cipher.media.cloud.CloudMetadataRepository metadataRepository, @org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.BillingRepository billingRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.settings.cloud.CloudSyncUiState> getUiState() {
        return null;
    }
    
    private final void loadData() {
    }
    
    private final void observeSyncStatus() {
    }
    
    public final void startSync() {
    }
}