package com.cipher.media.di;

import android.content.Context;
import com.cipher.media.data.encryption.KeystoreManager;
import com.cipher.media.data.local.EncryptedDatabase;
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
public final class AppModule_ProvideEncryptedDatabaseFactory implements Factory<EncryptedDatabase> {
  private final Provider<Context> contextProvider;

  private final Provider<KeystoreManager> keystoreManagerProvider;

  public AppModule_ProvideEncryptedDatabaseFactory(Provider<Context> contextProvider,
      Provider<KeystoreManager> keystoreManagerProvider) {
    this.contextProvider = contextProvider;
    this.keystoreManagerProvider = keystoreManagerProvider;
  }

  @Override
  public EncryptedDatabase get() {
    return provideEncryptedDatabase(contextProvider.get(), keystoreManagerProvider.get());
  }

  public static AppModule_ProvideEncryptedDatabaseFactory create(Provider<Context> contextProvider,
      Provider<KeystoreManager> keystoreManagerProvider) {
    return new AppModule_ProvideEncryptedDatabaseFactory(contextProvider, keystoreManagerProvider);
  }

  public static EncryptedDatabase provideEncryptedDatabase(Context context,
      KeystoreManager keystoreManager) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideEncryptedDatabase(context, keystoreManager));
  }
}
