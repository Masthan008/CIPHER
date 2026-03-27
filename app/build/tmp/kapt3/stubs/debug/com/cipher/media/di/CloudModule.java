package com.cipher.media.di;

import com.cipher.media.cloud.CloudMetadataRepository;
import com.cipher.media.cloud.CloudinaryManager;
import com.cipher.media.cloud.VaultSyncEngine;
import com.cipher.media.billing.BillingRepository;
import com.cipher.media.data.local.VaultDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\b\u0010\u0005\u001a\u00020\u0006H\u0007J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007\u00a8\u0006\u000f"}, d2 = {"Lcom/cipher/media/di/CloudModule;", "", "()V", "provideCloudMetadataRepository", "Lcom/cipher/media/cloud/CloudMetadataRepository;", "provideCloudinaryManager", "Lcom/cipher/media/cloud/CloudinaryManager;", "provideVaultSyncEngine", "Lcom/cipher/media/cloud/VaultSyncEngine;", "cloudinaryManager", "metadataRepository", "vaultDao", "Lcom/cipher/media/data/local/VaultDao;", "billingRepository", "Lcom/cipher/media/billing/BillingRepository;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class CloudModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.di.CloudModule INSTANCE = null;
    
    private CloudModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.cloud.CloudinaryManager provideCloudinaryManager() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.cloud.CloudMetadataRepository provideCloudMetadataRepository() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.cloud.VaultSyncEngine provideVaultSyncEngine(@org.jetbrains.annotations.NotNull()
    com.cipher.media.cloud.CloudinaryManager cloudinaryManager, @org.jetbrains.annotations.NotNull()
    com.cipher.media.cloud.CloudMetadataRepository metadataRepository, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.VaultDao vaultDao, @org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.BillingRepository billingRepository) {
        return null;
    }
}