package com.cipher.media.data.repository;

import com.cipher.media.data.local.MediaDao;
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
public final class FavoritesRepository_Factory implements Factory<FavoritesRepository> {
  private final Provider<MediaDao> mediaDaoProvider;

  public FavoritesRepository_Factory(Provider<MediaDao> mediaDaoProvider) {
    this.mediaDaoProvider = mediaDaoProvider;
  }

  @Override
  public FavoritesRepository get() {
    return newInstance(mediaDaoProvider.get());
  }

  public static FavoritesRepository_Factory create(Provider<MediaDao> mediaDaoProvider) {
    return new FavoritesRepository_Factory(mediaDaoProvider);
  }

  public static FavoritesRepository newInstance(MediaDao mediaDao) {
    return new FavoritesRepository(mediaDao);
  }
}
