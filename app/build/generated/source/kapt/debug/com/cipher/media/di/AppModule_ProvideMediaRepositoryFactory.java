package com.cipher.media.di;

import android.content.ContentResolver;
import com.cipher.media.data.repository.MediaRepository;
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
public final class AppModule_ProvideMediaRepositoryFactory implements Factory<MediaRepository> {
  private final Provider<ContentResolver> contentResolverProvider;

  public AppModule_ProvideMediaRepositoryFactory(
      Provider<ContentResolver> contentResolverProvider) {
    this.contentResolverProvider = contentResolverProvider;
  }

  @Override
  public MediaRepository get() {
    return provideMediaRepository(contentResolverProvider.get());
  }

  public static AppModule_ProvideMediaRepositoryFactory create(
      Provider<ContentResolver> contentResolverProvider) {
    return new AppModule_ProvideMediaRepositoryFactory(contentResolverProvider);
  }

  public static MediaRepository provideMediaRepository(ContentResolver contentResolver) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideMediaRepository(contentResolver));
  }
}
