package com.cipher.media.ui.video.streaming;

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
public final class StreamingManager_Factory implements Factory<StreamingManager> {
  private final Provider<Context> contextProvider;

  public StreamingManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public StreamingManager get() {
    return newInstance(contextProvider.get());
  }

  public static StreamingManager_Factory create(Provider<Context> contextProvider) {
    return new StreamingManager_Factory(contextProvider);
  }

  public static StreamingManager newInstance(Context context) {
    return new StreamingManager(context);
  }
}
