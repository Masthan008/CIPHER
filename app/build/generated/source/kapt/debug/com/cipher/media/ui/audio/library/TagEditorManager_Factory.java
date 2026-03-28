package com.cipher.media.ui.audio.library;

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
public final class TagEditorManager_Factory implements Factory<TagEditorManager> {
  private final Provider<ContentResolver> contentResolverProvider;

  public TagEditorManager_Factory(Provider<ContentResolver> contentResolverProvider) {
    this.contentResolverProvider = contentResolverProvider;
  }

  @Override
  public TagEditorManager get() {
    return newInstance(contentResolverProvider.get());
  }

  public static TagEditorManager_Factory create(Provider<ContentResolver> contentResolverProvider) {
    return new TagEditorManager_Factory(contentResolverProvider);
  }

  public static TagEditorManager newInstance(ContentResolver contentResolver) {
    return new TagEditorManager(contentResolver);
  }
}
