package com.cipher.media.ui.video;

import com.cipher.media.domain.usecase.GetLocalMediaUseCase;
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
public final class VideoBrowserViewModel_Factory implements Factory<VideoBrowserViewModel> {
  private final Provider<GetLocalMediaUseCase> getLocalMediaUseCaseProvider;

  public VideoBrowserViewModel_Factory(
      Provider<GetLocalMediaUseCase> getLocalMediaUseCaseProvider) {
    this.getLocalMediaUseCaseProvider = getLocalMediaUseCaseProvider;
  }

  @Override
  public VideoBrowserViewModel get() {
    return newInstance(getLocalMediaUseCaseProvider.get());
  }

  public static VideoBrowserViewModel_Factory create(
      Provider<GetLocalMediaUseCase> getLocalMediaUseCaseProvider) {
    return new VideoBrowserViewModel_Factory(getLocalMediaUseCaseProvider);
  }

  public static VideoBrowserViewModel newInstance(GetLocalMediaUseCase getLocalMediaUseCase) {
    return new VideoBrowserViewModel(getLocalMediaUseCase);
  }
}
