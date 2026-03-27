package com.cipher.media.ui.video;

import android.content.Context;
import androidx.media3.exoplayer.ExoPlayer;
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
public final class VideoPlayerViewModel_Factory implements Factory<VideoPlayerViewModel> {
  private final Provider<ExoPlayer.Builder> playerBuilderProvider;

  private final Provider<BillingRepository> billingRepositoryProvider;

  private final Provider<Context> contextProvider;

  public VideoPlayerViewModel_Factory(Provider<ExoPlayer.Builder> playerBuilderProvider,
      Provider<BillingRepository> billingRepositoryProvider, Provider<Context> contextProvider) {
    this.playerBuilderProvider = playerBuilderProvider;
    this.billingRepositoryProvider = billingRepositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public VideoPlayerViewModel get() {
    return newInstance(playerBuilderProvider.get(), billingRepositoryProvider.get(), contextProvider.get());
  }

  public static VideoPlayerViewModel_Factory create(
      Provider<ExoPlayer.Builder> playerBuilderProvider,
      Provider<BillingRepository> billingRepositoryProvider, Provider<Context> contextProvider) {
    return new VideoPlayerViewModel_Factory(playerBuilderProvider, billingRepositoryProvider, contextProvider);
  }

  public static VideoPlayerViewModel newInstance(ExoPlayer.Builder playerBuilder,
      BillingRepository billingRepository, Context context) {
    return new VideoPlayerViewModel(playerBuilder, billingRepository, context);
  }
}
