package com.cipher.media.domain.usecase;

import com.cipher.media.data.repository.MediaRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class GetLocalMediaUseCase_Factory implements Factory<GetLocalMediaUseCase> {
  private final Provider<MediaRepository> repositoryProvider;

  public GetLocalMediaUseCase_Factory(Provider<MediaRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetLocalMediaUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetLocalMediaUseCase_Factory create(Provider<MediaRepository> repositoryProvider) {
    return new GetLocalMediaUseCase_Factory(repositoryProvider);
  }

  public static GetLocalMediaUseCase newInstance(MediaRepository repository) {
    return new GetLocalMediaUseCase(repository);
  }
}
