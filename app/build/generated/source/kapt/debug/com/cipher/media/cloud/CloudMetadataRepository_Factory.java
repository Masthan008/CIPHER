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
public final class CloudMetadataRepository_Factory implements Factory<CloudMetadataRepository> {
  @Override
  public CloudMetadataRepository get() {
    return newInstance();
  }

  public static CloudMetadataRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CloudMetadataRepository newInstance() {
    return new CloudMetadataRepository();
  }

  private static final class InstanceHolder {
    private static final CloudMetadataRepository_Factory INSTANCE = new CloudMetadataRepository_Factory();
  }
}
