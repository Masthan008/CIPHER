package com.cipher.media.ui.video.audio;

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
public final class VideoEqualizerViewModel_Factory implements Factory<VideoEqualizerViewModel> {
  private final Provider<BillingRepository> billingRepositoryProvider;

  public VideoEqualizerViewModel_Factory(Provider<BillingRepository> billingRepositoryProvider) {
    this.billingRepositoryProvider = billingRepositoryProvider;
  }

  @Override
  public VideoEqualizerViewModel get() {
    return newInstance(billingRepositoryProvider.get());
  }

  public static VideoEqualizerViewModel_Factory create(
      Provider<BillingRepository> billingRepositoryProvider) {
    return new VideoEqualizerViewModel_Factory(billingRepositoryProvider);
  }

  public static VideoEqualizerViewModel newInstance(BillingRepository billingRepository) {
    return new VideoEqualizerViewModel(billingRepository);
  }
}
