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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0012\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0018\u001a\u00020\nH\u00c6\u0003J;\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u001fH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012\u00a8\u0006 "}, d2 = {"Lcom/cipher/media/ui/settings/cloud/CloudSyncUiState;", "", "activeTier", "Lcom/cipher/media/billing/model/SubscriptionTier;", "totalQuotaBytes", "", "usedStorageBytes", "syncStatus", "Lcom/cipher/media/cloud/SyncStatus;", "isLoading", "", "(Lcom/cipher/media/billing/model/SubscriptionTier;JJLcom/cipher/media/cloud/SyncStatus;Z)V", "getActiveTier", "()Lcom/cipher/media/billing/model/SubscriptionTier;", "()Z", "getSyncStatus", "()Lcom/cipher/media/cloud/SyncStatus;", "getTotalQuotaBytes", "()J", "getUsedStorageBytes", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
public final class CloudSyncUiState {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.billing.model.SubscriptionTier activeTier = null;
    private final long totalQuotaBytes = 0L;
    private final long usedStorageBytes = 0L;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.cloud.SyncStatus syncStatus = null;
    private final boolean isLoading = false;
    
    public CloudSyncUiState(@org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.model.SubscriptionTier activeTier, long totalQuotaBytes, long usedStorageBytes, @org.jetbrains.annotations.NotNull()
    com.cipher.media.cloud.SyncStatus syncStatus, boolean isLoading) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.billing.model.SubscriptionTier getActiveTier() {
        return null;
    }
    
    public final long getTotalQuotaBytes() {
        return 0L;
    }
    
    public final long getUsedStorageBytes() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.cloud.SyncStatus getSyncStatus() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public CloudSyncUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.billing.model.SubscriptionTier component1() {
        return null;
    }
    
    public final long component2() {
        return 0L;
    }
    
    public final long component3() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.cloud.SyncStatus component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.settings.cloud.CloudSyncUiState copy(@org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.model.SubscriptionTier activeTier, long totalQuotaBytes, long usedStorageBytes, @org.jetbrains.annotations.NotNull()
    com.cipher.media.cloud.SyncStatus syncStatus, boolean isLoading) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}