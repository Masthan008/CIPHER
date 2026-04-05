package com.cipher.media.ui.settings;

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
public final class LanguageManager_Factory implements Factory<LanguageManager> {
  private final Provider<Context> contextProvider;

  private final Provider<SettingsRepository> repoProvider;

  public LanguageManager_Factory(Provider<Context> contextProvider,
      Provider<SettingsRepository> repoProvider) {
    this.contextProvider = contextProvider;
    this.repoProvider = repoProvider;
  }

  @Override
  public LanguageManager get() {
    return newInstance(contextProvider.get(), repoProvider.get());
  }

  public static LanguageManager_Factory create(Provider<Context> contextProvider,
      Provider<SettingsRepository> repoProvider) {
    return new LanguageManager_Factory(contextProvider, repoProvider);
  }

  public static LanguageManager newInstance(Context context, SettingsRepository repo) {
    return new LanguageManager(context, repo);
  }
}
