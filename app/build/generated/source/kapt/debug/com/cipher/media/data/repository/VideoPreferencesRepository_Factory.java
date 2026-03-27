package com.cipher.media.data.repository;

import com.cipher.media.data.local.VideoPreferencesDao;
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
public final class VideoPreferencesRepository_Factory implements Factory<VideoPreferencesRepository> {
  private final Provider<VideoPreferencesDao> daoProvider;

  public VideoPreferencesRepository_Factory(Provider<VideoPreferencesDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public VideoPreferencesRepository get() {
    return newInstance(daoProvider.get());
  }

  public static VideoPreferencesRepository_Factory create(
      Provider<VideoPreferencesDao> daoProvider) {
    return new VideoPreferencesRepository_Factory(daoProvider);
  }

  public static VideoPreferencesRepository newInstance(VideoPreferencesDao dao) {
    return new VideoPreferencesRepository(dao);
  }
}
