package com.cipher.media.di;

import android.content.Context;
import com.cipher.media.data.encryption.CryptoUtil;
import com.cipher.media.data.encryption.KeystoreManager;
import com.cipher.media.data.encryption.VaultEncryptionManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideVaultEncryptionManagerFactory implements Factory<VaultEncryptionManager> {
  private final Provider<Context> contextProvider;

  private final Provider<KeystoreManager> keystoreManagerProvider;

  private final Provider<CryptoUtil> cryptoUtilProvider;

  public AppModule_ProvideVaultEncryptionManagerFactory(Provider<Context> contextProvider,
      Provider<KeystoreManager> keystoreManagerProvider, Provider<CryptoUtil> cryptoUtilProvider) {
    this.contextProvider = contextProvider;
    this.keystoreManagerProvider = keystoreManagerProvider;
    this.cryptoUtilProvider = cryptoUtilProvider;
  }

  @Override
  public VaultEncryptionManager get() {
    return provideVaultEncryptionManager(contextProvider.get(), keystoreManagerProvider.get(), cryptoUtilProvider.get());
  }

  public static AppModule_ProvideVaultEncryptionManagerFactory create(
      Provider<Context> contextProvider, Provider<KeystoreManager> keystoreManagerProvider,
      Provider<CryptoUtil> cryptoUtilProvider) {
    return new AppModule_ProvideVaultEncryptionManagerFactory(contextProvider, keystoreManagerProvider, cryptoUtilProvider);
  }

  public static VaultEncryptionManager provideVaultEncryptionManager(Context context,
      KeystoreManager keystoreManager, CryptoUtil cryptoUtil) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVaultEncryptionManager(context, keystoreManager, cryptoUtil));
  }
}
