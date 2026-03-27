package com.cipher.media.ui.search;

import android.content.ContentResolver;
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
public final class SearchViewModel_Factory implements Factory<SearchViewModel> {
  private final Provider<ContentResolver> contentResolverProvider;

  public SearchViewModel_Factory(Provider<ContentResolver> contentResolverProvider) {
    this.contentResolverProvider = contentResolverProvider;
  }

  @Override
  public SearchViewModel get() {
    return newInstance(contentResolverProvider.get());
  }

  public static SearchViewModel_Factory create(Provider<ContentResolver> contentResolverProvider) {
    return new SearchViewModel_Factory(contentResolverProvider);
  }

  public static SearchViewModel newInstance(ContentResolver contentResolver) {
    return new SearchViewModel(contentResolver);
  }
}
