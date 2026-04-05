package com.cipher.media.ui.audio;

import android.content.Context;
import com.cipher.media.data.repository.FavoritesRepository;
import com.cipher.media.data.repository.MediaRepository;
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
public final class AudioPlayerViewModel_Factory implements Factory<AudioPlayerViewModel> {
  private final Provider<MediaRepository> mediaRepositoryProvider;

  private final Provider<FavoritesRepository> favoritesRepositoryProvider;

  private final Provider<Context> contextProvider;

  public AudioPlayerViewModel_Factory(Provider<MediaRepository> mediaRepositoryProvider,
      Provider<FavoritesRepository> favoritesRepositoryProvider,
      Provider<Context> contextProvider) {
    this.mediaRepositoryProvider = mediaRepositoryProvider;
    this.favoritesRepositoryProvider = favoritesRepositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public AudioPlayerViewModel get() {
    return newInstance(mediaRepositoryProvider.get(), favoritesRepositoryProvider.get(), contextProvider.get());
  }

  public static AudioPlayerViewModel_Factory create(
      Provider<MediaRepository> mediaRepositoryProvider,
      Provider<FavoritesRepository> favoritesRepositoryProvider,
      Provider<Context> contextProvider) {
    return new AudioPlayerViewModel_Factory(mediaRepositoryProvider, favoritesRepositoryProvider, contextProvider);
  }

  public static AudioPlayerViewModel newInstance(MediaRepository mediaRepository,
      FavoritesRepository favoritesRepository, Context context) {
    return new AudioPlayerViewModel(mediaRepository, favoritesRepository, context);
  }
}
