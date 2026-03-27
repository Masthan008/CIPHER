package com.cipher.media.di;

import android.content.Context;
import com.cipher.media.data.local.MediaDatabase;
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
public final class AppModule_ProvideMediaDatabaseFactory implements Factory<MediaDatabase> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideMediaDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public MediaDatabase get() {
    return provideMediaDatabase(contextProvider.get());
  }

  public static AppModule_ProvideMediaDatabaseFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideMediaDatabaseFactory(contextProvider);
  }

  public static MediaDatabase provideMediaDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideMediaDatabase(context));
  }
}
