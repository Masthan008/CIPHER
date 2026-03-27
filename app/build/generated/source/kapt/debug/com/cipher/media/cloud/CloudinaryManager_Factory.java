package com.cipher.media.cloud;

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
public final class CloudinaryManager_Factory implements Factory<CloudinaryManager> {
  @Override
  public CloudinaryManager get() {
    return newInstance();
  }

  public static CloudinaryManager_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CloudinaryManager newInstance() {
    return new CloudinaryManager();
  }

  private static final class InstanceHolder {
    private static final CloudinaryManager_Factory INSTANCE = new CloudinaryManager_Factory();
  }
}
