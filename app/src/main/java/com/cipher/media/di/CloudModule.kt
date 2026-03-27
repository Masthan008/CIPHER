package com.cipher.media.di

import com.cipher.media.cloud.CloudMetadataRepository
import com.cipher.media.cloud.CloudinaryManager
import com.cipher.media.cloud.VaultSyncEngine
import com.cipher.media.billing.BillingRepository
import com.cipher.media.data.local.VaultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CloudModule {

    @Provides
    @Singleton
    fun provideCloudinaryManager(): CloudinaryManager {
        return CloudinaryManager()
    }

    @Provides
    @Singleton
    fun provideCloudMetadataRepository(): CloudMetadataRepository {
        return CloudMetadataRepository()
    }

    @Provides
    @Singleton
    fun provideVaultSyncEngine(
        cloudinaryManager: CloudinaryManager,
        metadataRepository: CloudMetadataRepository,
        vaultDao: VaultDao,
        billingRepository: BillingRepository
    ): VaultSyncEngine {
        return VaultSyncEngine(cloudinaryManager, metadataRepository, vaultDao, billingRepository)
    }
}
