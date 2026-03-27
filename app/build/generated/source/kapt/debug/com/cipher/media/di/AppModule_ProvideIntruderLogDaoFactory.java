package com.cipher.media.di;

import com.cipher.media.data.local.EncryptedDatabase;
import com.cipher.media.data.local.IntruderLogDao;
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
public final class AppModule_ProvideIntruderLogDaoFactory implements Factory<IntruderLogDao> {
  private final Provider<EncryptedDatabase> databaseProvider;

  public AppModule_ProvideIntruderLogDaoFactory(Provider<EncryptedDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public IntruderLogDao get() {
    return provideIntruderLogDao(databaseProvider.get());
  }

  public static AppModule_ProvideIntruderLogDaoFactory create(
      Provider<EncryptedDatabase> databaseProvider) {
    return new AppModule_ProvideIntruderLogDaoFactory(databaseProvider);
  }

  public static IntruderLogDao provideIntruderLogDao(EncryptedDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideIntruderLogDao(database));
  }
}
