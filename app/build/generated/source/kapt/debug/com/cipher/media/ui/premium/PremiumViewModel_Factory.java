package com.cipher.media.ui.premium;

import com.cipher.media.billing.BillingManager;
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
public final class PremiumViewModel_Factory implements Factory<PremiumViewModel> {
  private final Provider<BillingManager> billingManagerProvider;

  private final Provider<BillingRepository> billingRepositoryProvider;

  public PremiumViewModel_Factory(Provider<BillingManager> billingManagerProvider,
      Provider<BillingRepository> billingRepositoryProvider) {
    this.billingManagerProvider = billingManagerProvider;
    this.billingRepositoryProvider = billingRepositoryProvider;
  }

  @Override
  public PremiumViewModel get() {
    return newInstance(billingManagerProvider.get(), billingRepositoryProvider.get());
  }

  public static PremiumViewModel_Factory create(Provider<BillingManager> billingManagerProvider,
      Provider<BillingRepository> billingRepositoryProvider) {
    return new PremiumViewModel_Factory(billingManagerProvider, billingRepositoryProvider);
  }

  public static PremiumViewModel newInstance(BillingManager billingManager,
      BillingRepository billingRepository) {
    return new PremiumViewModel(billingManager, billingRepository);
  }
}
