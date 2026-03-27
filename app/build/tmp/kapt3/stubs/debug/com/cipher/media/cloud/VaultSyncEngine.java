package com.cipher.media.cloud;

import com.cipher.media.billing.BillingRepository;
import com.cipher.media.billing.model.SubscriptionTier;
import com.cipher.media.data.local.VaultDao;
import com.cipher.media.data.local.VaultFolderEntity;
import com.cipher.media.data.local.VaultItemEntity;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.StateFlow;
import java.io.File;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u0012\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0014R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/cipher/media/cloud/VaultSyncEngine;", "", "cloudinaryManager", "Lcom/cipher/media/cloud/CloudinaryManager;", "metadataRepository", "Lcom/cipher/media/cloud/CloudMetadataRepository;", "vaultDao", "Lcom/cipher/media/data/local/VaultDao;", "billingRepository", "Lcom/cipher/media/billing/BillingRepository;", "(Lcom/cipher/media/cloud/CloudinaryManager;Lcom/cipher/media/cloud/CloudMetadataRepository;Lcom/cipher/media/data/local/VaultDao;Lcom/cipher/media/billing/BillingRepository;)V", "_syncStatus", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/cloud/SyncStatus;", "syncStatus", "Lkotlinx/coroutines/flow/StateFlow;", "getSyncStatus", "()Lkotlinx/coroutines/flow/StateFlow;", "startSync", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class VaultSyncEngine {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.cloud.CloudinaryManager cloudinaryManager = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.cloud.CloudMetadataRepository metadataRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.data.local.VaultDao vaultDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.billing.BillingRepository billingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.cloud.SyncStatus> _syncStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.cloud.SyncStatus> syncStatus = null;
    public static final long QUOTA_FREE = 1073741824L;
    public static final long QUOTA_PRO = 10737418240L;
    public static final long QUOTA_POWER = 9223372036854775807L;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.cloud.VaultSyncEngine.Companion Companion = null;
    
    @javax.inject.Inject()
    public VaultSyncEngine(@org.jetbrains.annotations.NotNull()
    com.cipher.media.cloud.CloudinaryManager cloudinaryManager, @org.jetbrains.annotations.NotNull()
    com.cipher.media.cloud.CloudMetadataRepository metadataRepository, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.VaultDao vaultDao, @org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.BillingRepository billingRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.cloud.SyncStatus> getSyncStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object startSync(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/cipher/media/cloud/VaultSyncEngine$Companion;", "", "()V", "QUOTA_FREE", "", "QUOTA_POWER", "QUOTA_PRO", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}