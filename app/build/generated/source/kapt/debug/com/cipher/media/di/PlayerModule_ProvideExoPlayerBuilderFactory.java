package com.cipher.media.di;

import android.content.Context;
import androidx.media3.exoplayer.ExoPlayer;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class PlayerModule_ProvideExoPlayerBuilderFactory implements Factory<ExoPlayer.Builder> {
  private final Provider<Context> contextProvider;

  public PlayerModule_ProvideExoPlayerBuilderFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ExoPlayer.Builder get() {
    return provideExoPlayerBuilder(contextProvider.get());
  }

  public static PlayerModule_ProvideExoPlayerBuilderFactory create(
      Provider<Context> contextProvider) {
    return new PlayerModule_ProvideExoPlayerBuilderFactory(contextProvider);
  }

  public static ExoPlayer.Builder provideExoPlayerBuilder(Context context) {
    return Preconditions.checkNotNullFromProvides(PlayerModule.INSTANCE.provideExoPlayerBuilder(context));
  }
}
