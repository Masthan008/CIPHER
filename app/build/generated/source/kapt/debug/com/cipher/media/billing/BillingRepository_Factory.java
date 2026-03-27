package com.cipher.media.billing;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class BillingRepository_Factory implements Factory<BillingRepository> {
  private final Provider<BillingManager> billingManagerProvider;

  public BillingRepository_Factory(Provider<BillingManager> billingManagerProvider) {
    this.billingManagerProvider = billingManagerProvider;
  }

  @Override
  public BillingRepository get() {
    return newInstance(billingManagerProvider.get());
  }

  public static BillingRepository_Factory create(Provider<BillingManager> billingManagerProvider) {
    return new BillingRepository_Factory(billingManagerProvider);
  }

  public static BillingRepository newInstance(BillingManager billingManager) {
    return new BillingRepository(billingManager);
  }
}
