package com.cipher.media.ui.video;

import androidx.media3.exoplayer.ExoPlayer;
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
public final class VideoPlayerViewModel_Factory implements Factory<VideoPlayerViewModel> {
  private final Provider<ExoPlayer.Builder> playerBuilderProvider;

  public VideoPlayerViewModel_Factory(Provider<ExoPlayer.Builder> playerBuilderProvider) {
    this.playerBuilderProvider = playerBuilderProvider;
  }

  @Override
  public VideoPlayerViewModel get() {
    return newInstance(playerBuilderProvider.get());
  }

  public static VideoPlayerViewModel_Factory create(
      Provider<ExoPlayer.Builder> playerBuilderProvider) {
    return new VideoPlayerViewModel_Factory(playerBuilderProvider);
  }

  public static VideoPlayerViewModel newInstance(ExoPlayer.Builder playerBuilder) {
    return new VideoPlayerViewModel(playerBuilder);
  }
}
