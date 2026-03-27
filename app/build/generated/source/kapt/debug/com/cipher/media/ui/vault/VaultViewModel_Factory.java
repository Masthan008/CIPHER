package com.cipher.media.ui.vault;

import android.content.Context;
import com.cipher.media.data.encryption.VaultEncryptionManager;
import com.cipher.media.data.local.VaultDao;
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
public final class VaultViewModel_Factory implements Factory<VaultViewModel> {
  private final Provider<VaultDao> vaultDaoProvider;

  private final Provider<VaultEncryptionManager> encryptionManagerProvider;

  private final Provider<Context> contextProvider;

  public VaultViewModel_Factory(Provider<VaultDao> vaultDaoProvider,
      Provider<VaultEncryptionManager> encryptionManagerProvider,
      Provider<Context> contextProvider) {
    this.vaultDaoProvider = vaultDaoProvider;
    this.encryptionManagerProvider = encryptionManagerProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public VaultViewModel get() {
    return newInstance(vaultDaoProvider.get(), encryptionManagerProvider.get(), contextProvider.get());
  }

  public static VaultViewModel_Factory create(Provider<VaultDao> vaultDaoProvider,
      Provider<VaultEncryptionManager> encryptionManagerProvider,
      Provider<Context> contextProvider) {
    return new VaultViewModel_Factory(vaultDaoProvider, encryptionManagerProvider, contextProvider);
  }

  public static VaultViewModel newInstance(VaultDao vaultDao,
      VaultEncryptionManager encryptionManager, Context context) {
    return new VaultViewModel(vaultDao, encryptionManager, context);
  }
}
