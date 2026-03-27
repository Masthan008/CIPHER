package com.cipher.media.di;

import android.content.Context;
import com.cipher.media.ads.AdManager;
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
public final class AppModule_ProvideAdManagerFactory implements Factory<AdManager> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideAdManagerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public AdManager get() {
    return provideAdManager(contextProvider.get());
  }

  public static AppModule_ProvideAdManagerFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideAdManagerFactory(contextProvider);
  }

  public static AdManager provideAdManager(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAdManager(context));
  }
}
