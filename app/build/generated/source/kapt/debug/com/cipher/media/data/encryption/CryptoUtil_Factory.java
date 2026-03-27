package com.cipher.media.data.encryption;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class CryptoUtil_Factory implements Factory<CryptoUtil> {
  @Override
  public CryptoUtil get() {
    return newInstance();
  }

  public static CryptoUtil_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CryptoUtil newInstance() {
    return new CryptoUtil();
  }

  private static final class InstanceHolder {
    private static final CryptoUtil_Factory INSTANCE = new CryptoUtil_Factory();
  }
}
