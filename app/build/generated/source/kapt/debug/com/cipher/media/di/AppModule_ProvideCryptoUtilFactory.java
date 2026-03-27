package com.cipher.media.di;

import com.cipher.media.data.encryption.CryptoUtil;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AppModule_ProvideCryptoUtilFactory implements Factory<CryptoUtil> {
  @Override
  public CryptoUtil get() {
    return provideCryptoUtil();
  }

  public static AppModule_ProvideCryptoUtilFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CryptoUtil provideCryptoUtil() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideCryptoUtil());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideCryptoUtilFactory INSTANCE = new AppModule_ProvideCryptoUtilFactory();
  }
}
