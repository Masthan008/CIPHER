package com.cipher.media.ui.audio.audiofx;

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
  private final Provider<AudioEffectsManager> audioFxManagerProvider;

  public EqualizerViewModel_Factory(Provider<AudioEffectsManager> audioFxManagerProvider) {
    this.audioFxManagerProvider = audioFxManagerProvider;
  }

  @Override
  public EqualizerViewModel get() {
    return newInstance(audioFxManagerProvider.get());
  }

  public static EqualizerViewModel_Factory create(
      Provider<AudioEffectsManager> audioFxManagerProvider) {
    return new EqualizerViewModel_Factory(audioFxManagerProvider);
  }

  public static EqualizerViewModel newInstance(AudioEffectsManager audioFxManager) {
    return new EqualizerViewModel(audioFxManager);
  }
}
