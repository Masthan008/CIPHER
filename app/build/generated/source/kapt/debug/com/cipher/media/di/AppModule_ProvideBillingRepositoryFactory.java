package com.cipher.media.di;

import com.cipher.media.billing.BillingManager;
import com.cipher.media.billing.BillingRepository;
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
public final class AppModule_ProvideBillingRepositoryFactory implements Factory<BillingRepository> {
  private final Provider<BillingManager> billingManagerProvider;

  public AppModule_ProvideBillingRepositoryFactory(
      Provider<BillingManager> billingManagerProvider) {
    this.billingManagerProvider = billingManagerProvider;
  }

  @Override
  public BillingRepository get() {
    return provideBillingRepository(billingManagerProvider.get());
  }

  public static AppModule_ProvideBillingRepositoryFactory create(
      Provider<BillingManager> billingManagerProvider) {
    return new AppModule_ProvideBillingRepositoryFactory(billingManagerProvider);
  }

  public static BillingRepository provideBillingRepository(BillingManager billingManager) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBillingRepository(billingManager));
  }
}
