package com.cipher.media.di;

import com.cipher.media.cloud.CloudMetadataRepository;
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
public final class CloudModule_ProvideCloudMetadataRepositoryFactory implements Factory<CloudMetadataRepository> {
  @Override
  public CloudMetadataRepository get() {
    return provideCloudMetadataRepository();
  }

  public static CloudModule_ProvideCloudMetadataRepositoryFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CloudMetadataRepository provideCloudMetadataRepository() {
    return Preconditions.checkNotNullFromProvides(CloudModule.INSTANCE.provideCloudMetadataRepository());
  }

  private static final class InstanceHolder {
    private static final CloudModule_ProvideCloudMetadataRepositoryFactory INSTANCE = new CloudModule_ProvideCloudMetadataRepositoryFactory();
  }
}
