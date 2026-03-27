package com.cipher.media.ui.video.enhancement;

import android.content.Context;
import com.cipher.media.billing.BillingRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class VideoEnhancementViewModel_Factory implements Factory<VideoEnhancementViewModel> {
  private final Provider<BillingRepository> billingRepositoryProvider;

  private final Provider<Context> contextProvider;

  public VideoEnhancementViewModel_Factory(Provider<BillingRepository> billingRepositoryProvider,
      Provider<Context> contextProvider) {
    this.billingRepositoryProvider = billingRepositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public VideoEnhancementViewModel get() {
    return newInstance(billingRepositoryProvider.get(), contextProvider.get());
  }

  public static VideoEnhancementViewModel_Factory create(
      Provider<BillingRepository> billingRepositoryProvider, Provider<Context> contextProvider) {
    return new VideoEnhancementViewModel_Factory(billingRepositoryProvider, contextProvider);
  }

  public static VideoEnhancementViewModel newInstance(BillingRepository billingRepository,
      Context context) {
    return new VideoEnhancementViewModel(billingRepository, context);
  }
}
