package com.cipher.media.di;

import com.cipher.media.data.local.MediaDatabase;
import com.cipher.media.data.local.VideoPreferencesDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideVideoPreferencesDaoFactory implements Factory<VideoPreferencesDao> {
  private final Provider<MediaDatabase> databaseProvider;

  public AppModule_ProvideVideoPreferencesDaoFactory(Provider<MediaDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public VideoPreferencesDao get() {
    return provideVideoPreferencesDao(databaseProvider.get());
  }

  public static AppModule_ProvideVideoPreferencesDaoFactory create(
      Provider<MediaDatabase> databaseProvider) {
    return new AppModule_ProvideVideoPreferencesDaoFactory(databaseProvider);
  }

  public static VideoPreferencesDao provideVideoPreferencesDao(MediaDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVideoPreferencesDao(database));
  }
}
