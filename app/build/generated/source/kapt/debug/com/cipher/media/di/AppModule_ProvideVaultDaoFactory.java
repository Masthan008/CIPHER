package com.cipher.media.di;

import com.cipher.media.data.local.EncryptedDatabase;
import com.cipher.media.data.local.VaultDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideVaultDaoFactory implements Factory<VaultDao> {
  private final Provider<EncryptedDatabase> databaseProvider;

  public AppModule_ProvideVaultDaoFactory(Provider<EncryptedDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public VaultDao get() {
    return provideVaultDao(databaseProvider.get());
  }

  public static AppModule_ProvideVaultDaoFactory create(
      Provider<EncryptedDatabase> databaseProvider) {
    return new AppModule_ProvideVaultDaoFactory(databaseProvider);
  }

  public static VaultDao provideVaultDao(EncryptedDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVaultDao(database));
  }
}
