package com.cipher.media.di;

import com.cipher.media.billing.BillingRepository;
import com.cipher.media.cloud.CloudMetadataRepository;
import com.cipher.media.cloud.CloudinaryManager;
import com.cipher.media.cloud.VaultSyncEngine;
import com.cipher.media.data.local.VaultDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class CloudModule_ProvideVaultSyncEngineFactory implements Factory<VaultSyncEngine> {
  private final Provider<CloudinaryManager> cloudinaryManagerProvider;

  private final Provider<CloudMetadataRepository> metadataRepositoryProvider;

  private final Provider<VaultDao> vaultDaoProvider;

  private final Provider<BillingRepository> billingRepositoryProvider;

  public CloudModule_ProvideVaultSyncEngineFactory(
      Provider<CloudinaryManager> cloudinaryManagerProvider,
      Provider<CloudMetadataRepository> metadataRepositoryProvider,
      Provider<VaultDao> vaultDaoProvider, Provider<BillingRepository> billingRepositoryProvider) {
    this.cloudinaryManagerProvider = cloudinaryManagerProvider;
    this.metadataRepositoryProvider = metadataRepositoryProvider;
    this.vaultDaoProvider = vaultDaoProvider;
    this.billingRepositoryProvider = billingRepositoryProvider;
  }

  @Override
  public VaultSyncEngine get() {
    return provideVaultSyncEngine(cloudinaryManagerProvider.get(), metadataRepositoryProvider.get(), vaultDaoProvider.get(), billingRepositoryProvider.get());
  }

  public static CloudModule_ProvideVaultSyncEngineFactory create(
      Provider<CloudinaryManager> cloudinaryManagerProvider,
      Provider<CloudMetadataRepository> metadataRepositoryProvider,
      Provider<VaultDao> vaultDaoProvider, Provider<BillingRepository> billingRepositoryProvider) {
    return new CloudModule_ProvideVaultSyncEngineFactory(cloudinaryManagerProvider, metadataRepositoryProvider, vaultDaoProvider, billingRepositoryProvider);
  }

  public static VaultSyncEngine provideVaultSyncEngine(CloudinaryManager cloudinaryManager,
      CloudMetadataRepository metadataRepository, VaultDao vaultDao,
      BillingRepository billingRepository) {
    return Preconditions.checkNotNullFromProvides(CloudModule.INSTANCE.provideVaultSyncEngine(cloudinaryManager, metadataRepository, vaultDao, billingRepository));
  }
}
