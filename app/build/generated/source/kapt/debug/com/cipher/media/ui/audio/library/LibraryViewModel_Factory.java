package com.cipher.media.ui.audio.library;

import com.cipher.media.data.repository.MediaRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class LibraryViewModel_Factory implements Factory<LibraryViewModel> {
  private final Provider<MediaRepository> mediaRepositoryProvider;

  private final Provider<TagEditorManager> tagEditorProvider;

  public LibraryViewModel_Factory(Provider<MediaRepository> mediaRepositoryProvider,
      Provider<TagEditorManager> tagEditorProvider) {
    this.mediaRepositoryProvider = mediaRepositoryProvider;
    this.tagEditorProvider = tagEditorProvider;
  }

  @Override
  public LibraryViewModel get() {
    return newInstance(mediaRepositoryProvider.get(), tagEditorProvider.get());
  }

  public static LibraryViewModel_Factory create(Provider<MediaRepository> mediaRepositoryProvider,
      Provider<TagEditorManager> tagEditorProvider) {
    return new LibraryViewModel_Factory(mediaRepositoryProvider, tagEditorProvider);
  }

  public static LibraryViewModel newInstance(MediaRepository mediaRepository,
      TagEditorManager tagEditor) {
    return new LibraryViewModel(mediaRepository, tagEditor);
  }
}
