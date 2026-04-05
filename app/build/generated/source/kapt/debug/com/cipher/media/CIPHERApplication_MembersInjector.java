package com.cipher.media;

import com.cipher.media.ads.AdManager;
import com.cipher.media.ui.settings.LanguageManager;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class CIPHERApplication_MembersInjector implements MembersInjector<CIPHERApplication> {
  private final Provider<AdManager> adManagerProvider;

  private final Provider<LanguageManager> languageManagerProvider;

  public CIPHERApplication_MembersInjector(Provider<AdManager> adManagerProvider,
      Provider<LanguageManager> languageManagerProvider) {
    this.adManagerProvider = adManagerProvider;
    this.languageManagerProvider = languageManagerProvider;
  }

  public static MembersInjector<CIPHERApplication> create(Provider<AdManager> adManagerProvider,
      Provider<LanguageManager> languageManagerProvider) {
    return new CIPHERApplication_MembersInjector(adManagerProvider, languageManagerProvider);
  }

  @Override
  public void injectMembers(CIPHERApplication instance) {
    injectAdManager(instance, adManagerProvider.get());
    injectLanguageManager(instance, languageManagerProvider.get());
  }

  @InjectedFieldSignature("com.cipher.media.CIPHERApplication.adManager")
  public static void injectAdManager(CIPHERApplication instance, AdManager adManager) {
    instance.adManager = adManager;
  }

  @InjectedFieldSignature("com.cipher.media.CIPHERApplication.languageManager")
  public static void injectLanguageManager(CIPHERApplication instance,
      LanguageManager languageManager) {
    instance.languageManager = languageManager;
  }
}
