package com.cipher.media.data.encryption;

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
public final class VaultEncryptionManager_Factory implements Factory<VaultEncryptionManager> {
  private final Provider<Context> contextProvider;

  private final Provider<KeystoreManager> keystoreManagerProvider;

  private final Provider<CryptoUtil> cryptoUtilProvider;

  public VaultEncryptionManager_Factory(Provider<Context> contextProvider,
      Provider<KeystoreManager> keystoreManagerProvider, Provider<CryptoUtil> cryptoUtilProvider) {
    this.contextProvider = contextProvider;
    this.keystoreManagerProvider = keystoreManagerProvider;
    this.cryptoUtilProvider = cryptoUtilProvider;
  }

  @Override
  public VaultEncryptionManager get() {
    return newInstance(contextProvider.get(), keystoreManagerProvider.get(), cryptoUtilProvider.get());
  }

  public static VaultEncryptionManager_Factory create(Provider<Context> contextProvider,
      Provider<KeystoreManager> keystoreManagerProvider, Provider<CryptoUtil> cryptoUtilProvider) {
    return new VaultEncryptionManager_Factory(contextProvider, keystoreManagerProvider, cryptoUtilProvider);
  }

  public static VaultEncryptionManager newInstance(Context context, KeystoreManager keystoreManager,
      CryptoUtil cryptoUtil) {
    return new VaultEncryptionManager(context, keystoreManager, cryptoUtil);
  }
}
