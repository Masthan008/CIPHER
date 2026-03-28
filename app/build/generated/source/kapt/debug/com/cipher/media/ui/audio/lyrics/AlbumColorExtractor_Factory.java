package com.cipher.media.ui.audio.lyrics;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AlbumColorExtractor_Factory implements Factory<AlbumColorExtractor> {
  private final Provider<Context> contextProvider;

  public AlbumColorExtractor_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AlbumColorExtractor get() {
    return newInstance(contextProvider.get());
  }

  public static AlbumColorExtractor_Factory create(Provider<Context> contextProvider) {
    return new AlbumColorExtractor_Factory(contextProvider);
  }

  public static AlbumColorExtractor newInstance(Context context) {
    return new AlbumColorExtractor(context);
  }
}
