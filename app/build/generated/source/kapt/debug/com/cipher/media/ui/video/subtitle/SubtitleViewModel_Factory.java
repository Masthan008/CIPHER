package com.cipher.media.ui.video.subtitle;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class SubtitleViewModel_Factory implements Factory<SubtitleViewModel> {
  private final Provider<Context> contextProvider;

  public SubtitleViewModel_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SubtitleViewModel get() {
    return newInstance(contextProvider.get());
  }

  public static SubtitleViewModel_Factory create(Provider<Context> contextProvider) {
    return new SubtitleViewModel_Factory(contextProvider);
  }

  public static SubtitleViewModel newInstance(Context context) {
    return new SubtitleViewModel(context);
  }
}
