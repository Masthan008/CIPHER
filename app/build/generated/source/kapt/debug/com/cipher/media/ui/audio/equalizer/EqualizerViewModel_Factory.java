package com.cipher.media.ui.audio.equalizer;

import com.cipher.media.data.repository.EqualizerRepository;
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
public final class EqualizerViewModel_Factory implements Factory<EqualizerViewModel> {
  private final Provider<EqualizerRepository> eqRepositoryProvider;

  public EqualizerViewModel_Factory(Provider<EqualizerRepository> eqRepositoryProvider) {
    this.eqRepositoryProvider = eqRepositoryProvider;
  }

  @Override
  public EqualizerViewModel get() {
    return newInstance(eqRepositoryProvider.get());
  }

  public static EqualizerViewModel_Factory create(
      Provider<EqualizerRepository> eqRepositoryProvider) {
    return new EqualizerViewModel_Factory(eqRepositoryProvider);
  }

  public static EqualizerViewModel newInstance(EqualizerRepository eqRepository) {
    return new EqualizerViewModel(eqRepository);
  }
}
