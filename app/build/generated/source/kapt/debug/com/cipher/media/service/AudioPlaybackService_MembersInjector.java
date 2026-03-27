package com.cipher.media.service;

import com.cipher.media.data.repository.EqualizerRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AudioPlaybackService_MembersInjector implements MembersInjector<AudioPlaybackService> {
  private final Provider<EqualizerRepository> eqRepositoryProvider;

  public AudioPlaybackService_MembersInjector(Provider<EqualizerRepository> eqRepositoryProvider) {
    this.eqRepositoryProvider = eqRepositoryProvider;
  }

  public static MembersInjector<AudioPlaybackService> create(
      Provider<EqualizerRepository> eqRepositoryProvider) {
    return new AudioPlaybackService_MembersInjector(eqRepositoryProvider);
  }

  @Override
  public void injectMembers(AudioPlaybackService instance) {
    injectEqRepository(instance, eqRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.cipher.media.service.AudioPlaybackService.eqRepository")
  public static void injectEqRepository(AudioPlaybackService instance,
      EqualizerRepository eqRepository) {
    instance.eqRepository = eqRepository;
  }
}
