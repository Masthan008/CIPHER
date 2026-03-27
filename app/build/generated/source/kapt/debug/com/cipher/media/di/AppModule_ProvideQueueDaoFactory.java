package com.cipher.media.di;

import com.cipher.media.data.local.MediaDatabase;
import com.cipher.media.ui.audio.queue.model.QueueDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AppModule_ProvideQueueDaoFactory implements Factory<QueueDao> {
  private final Provider<MediaDatabase> databaseProvider;

  public AppModule_ProvideQueueDaoFactory(Provider<MediaDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public QueueDao get() {
    return provideQueueDao(databaseProvider.get());
  }

  public static AppModule_ProvideQueueDaoFactory create(Provider<MediaDatabase> databaseProvider) {
    return new AppModule_ProvideQueueDaoFactory(databaseProvider);
  }

  public static QueueDao provideQueueDao(MediaDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideQueueDao(database));
  }
}
