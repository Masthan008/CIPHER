package com.cipher.media.data.repository;

import android.content.ContentResolver;
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
public final class MediaRepository_Factory implements Factory<MediaRepository> {
  private final Provider<ContentResolver> contentResolverProvider;

  public MediaRepository_Factory(Provider<ContentResolver> contentResolverProvider) {
    this.contentResolverProvider = contentResolverProvider;
  }

  @Override
  public MediaRepository get() {
    return newInstance(contentResolverProvider.get());
  }

  public static MediaRepository_Factory create(Provider<ContentResolver> contentResolverProvider) {
    return new MediaRepository_Factory(contentResolverProvider);
  }

  public static MediaRepository newInstance(ContentResolver contentResolver) {
    return new MediaRepository(contentResolver);
  }
}
