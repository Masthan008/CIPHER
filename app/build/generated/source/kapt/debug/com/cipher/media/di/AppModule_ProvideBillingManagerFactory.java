package com.cipher.media.di;

import android.content.Context;
import com.cipher.media.billing.BillingManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_ProvideBillingManagerFactory implements Factory<BillingManager> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideBillingManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public BillingManager get() {
    return provideBillingManager(contextProvider.get());
  }

  public static AppModule_ProvideBillingManagerFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideBillingManagerFactory(contextProvider);
  }

  public static BillingManager provideBillingManager(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBillingManager(context));
  }
}
