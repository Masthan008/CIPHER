package com.cipher.media.ui.settings;

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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<SettingsRepository> repoProvider;

  private final Provider<LanguageManager> languageManagerProvider;

  public SettingsViewModel_Factory(Provider<Context> contextProvider,
      Provider<SettingsRepository> repoProvider,
      Provider<LanguageManager> languageManagerProvider) {
    this.contextProvider = contextProvider;
    this.repoProvider = repoProvider;
    this.languageManagerProvider = languageManagerProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(contextProvider.get(), repoProvider.get(), languageManagerProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<Context> contextProvider,
      Provider<SettingsRepository> repoProvider,
      Provider<LanguageManager> languageManagerProvider) {
    return new SettingsViewModel_Factory(contextProvider, repoProvider, languageManagerProvider);
  }

  public static SettingsViewModel newInstance(Context context, SettingsRepository repo,
      LanguageManager languageManager) {
    return new SettingsViewModel(context, repo, languageManager);
  }
}
