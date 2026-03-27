package com.cipher.media;

import com.cipher.media.ads.AdManager;
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

  public CIPHERApplication_MembersInjector(Provider<AdManager> adManagerProvider) {
    this.adManagerProvider = adManagerProvider;
  }

  public static MembersInjector<CIPHERApplication> create(Provider<AdManager> adManagerProvider) {
    return new CIPHERApplication_MembersInjector(adManagerProvider);
  }

  @Override
  public void injectMembers(CIPHERApplication instance) {
    injectAdManager(instance, adManagerProvider.get());
  }

  @InjectedFieldSignature("com.cipher.media.CIPHERApplication.adManager")
  public static void injectAdManager(CIPHERApplication instance, AdManager adManager) {
    instance.adManager = adManager;
  }
}
