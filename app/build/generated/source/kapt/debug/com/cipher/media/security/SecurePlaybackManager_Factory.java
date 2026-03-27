package com.cipher.media.security;

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
public final class SecurePlaybackManager_Factory implements Factory<SecurePlaybackManager> {
  private final Provider<Context> contextProvider;

  public SecurePlaybackManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SecurePlaybackManager get() {
    return newInstance(contextProvider.get());
  }

  public static SecurePlaybackManager_Factory create(Provider<Context> contextProvider) {
    return new SecurePlaybackManager_Factory(contextProvider);
  }

  public static SecurePlaybackManager newInstance(Context context) {
    return new SecurePlaybackManager(context);
  }
}
