package com.cipher.media.di;

import com.cipher.media.cloud.CloudinaryManager;
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
public final class CloudModule_ProvideCloudinaryManagerFactory implements Factory<CloudinaryManager> {
  @Override
  public CloudinaryManager get() {
    return provideCloudinaryManager();
  }

  public static CloudModule_ProvideCloudinaryManagerFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CloudinaryManager provideCloudinaryManager() {
    return Preconditions.checkNotNullFromProvides(CloudModule.INSTANCE.provideCloudinaryManager());
  }

  private static final class InstanceHolder {
    private static final CloudModule_ProvideCloudinaryManagerFactory INSTANCE = new CloudModule_ProvideCloudinaryManagerFactory();
  }
}
