package com.cipher.media.di;

import android.content.Context;
import com.cipher.media.data.repository.EqualizerRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideEqualizerRepositoryFactory implements Factory<EqualizerRepository> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideEqualizerRepositoryFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public EqualizerRepository get() {
    return provideEqualizerRepository(contextProvider.get());
  }

  public static AppModule_ProvideEqualizerRepositoryFactory create(
      Provider<Context> contextProvider) {
    return new AppModule_ProvideEqualizerRepositoryFactory(contextProvider);
  }

  public static EqualizerRepository provideEqualizerRepository(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideEqualizerRepository(context));
  }
}
