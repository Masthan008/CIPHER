package com.cipher.media.ui.settings.cloud;

import com.cipher.media.billing.BillingRepository;
import com.cipher.media.cloud.CloudMetadataRepository;
import com.cipher.media.cloud.VaultSyncEngine;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class CloudSyncViewModel_Factory implements Factory<CloudSyncViewModel> {
  private final Provider<VaultSyncEngine> vaultSyncEngineProvider;

  private final Provider<CloudMetadataRepository> metadataRepositoryProvider;

  private final Provider<BillingRepository> billingRepositoryProvider;

  public CloudSyncViewModel_Factory(Provider<VaultSyncEngine> vaultSyncEngineProvider,
      Provider<CloudMetadataRepository> metadataRepositoryProvider,
      Provider<BillingRepository> billingRepositoryProvider) {
    this.vaultSyncEngineProvider = vaultSyncEngineProvider;
    this.metadataRepositoryProvider = metadataRepositoryProvider;
    this.billingRepositoryProvider = billingRepositoryProvider;
  }

  @Override
  public CloudSyncViewModel get() {
    return newInstance(vaultSyncEngineProvider.get(), metadataRepositoryProvider.get(), billingRepositoryProvider.get());
  }

  public static CloudSyncViewModel_Factory create(Provider<VaultSyncEngine> vaultSyncEngineProvider,
      Provider<CloudMetadataRepository> metadataRepositoryProvider,
      Provider<BillingRepository> billingRepositoryProvider) {
    return new CloudSyncViewModel_Factory(vaultSyncEngineProvider, metadataRepositoryProvider, billingRepositoryProvider);
  }

  public static CloudSyncViewModel newInstance(VaultSyncEngine vaultSyncEngine,
      CloudMetadataRepository metadataRepository, BillingRepository billingRepository) {
    return new CloudSyncViewModel(vaultSyncEngine, metadataRepository, billingRepository);
  }
}
