package com.cipher.media.di;

import com.cipher.media.data.local.MediaDao;
import com.cipher.media.data.local.MediaDatabase;
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
public final class AppModule_ProvideMediaDaoFactory implements Factory<MediaDao> {
  private final Provider<MediaDatabase> databaseProvider;

  public AppModule_ProvideMediaDaoFactory(Provider<MediaDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public MediaDao get() {
    return provideMediaDao(databaseProvider.get());
  }

  public static AppModule_ProvideMediaDaoFactory create(Provider<MediaDatabase> databaseProvider) {
    return new AppModule_ProvideMediaDaoFactory(databaseProvider);
  }

  public static MediaDao provideMediaDao(MediaDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideMediaDao(database));
  }
}
