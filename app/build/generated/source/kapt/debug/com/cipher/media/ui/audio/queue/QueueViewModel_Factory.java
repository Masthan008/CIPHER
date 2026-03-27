package com.cipher.media.ui.audio.queue;

import com.cipher.media.billing.BillingRepository;
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
public final class QueueViewModel_Factory implements Factory<QueueViewModel> {
  private final Provider<QueueRepository> repoProvider;

  private final Provider<BillingRepository> billingRepositoryProvider;

  public QueueViewModel_Factory(Provider<QueueRepository> repoProvider,
      Provider<BillingRepository> billingRepositoryProvider) {
    this.repoProvider = repoProvider;
    this.billingRepositoryProvider = billingRepositoryProvider;
  }

  @Override
  public QueueViewModel get() {
    return newInstance(repoProvider.get(), billingRepositoryProvider.get());
  }

  public static QueueViewModel_Factory create(Provider<QueueRepository> repoProvider,
      Provider<BillingRepository> billingRepositoryProvider) {
    return new QueueViewModel_Factory(repoProvider, billingRepositoryProvider);
  }

  public static QueueViewModel newInstance(QueueRepository repo,
      BillingRepository billingRepository) {
    return new QueueViewModel(repo, billingRepository);
  }
}
