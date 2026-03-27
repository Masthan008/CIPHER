package com.cipher.media.di;

import com.cipher.media.ui.audio.queue.QueueRepository;
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
public final class AppModule_ProvideQueueRepositoryFactory implements Factory<QueueRepository> {
  private final Provider<QueueDao> queueDaoProvider;

  public AppModule_ProvideQueueRepositoryFactory(Provider<QueueDao> queueDaoProvider) {
    this.queueDaoProvider = queueDaoProvider;
  }

  @Override
  public QueueRepository get() {
    return provideQueueRepository(queueDaoProvider.get());
  }

  public static AppModule_ProvideQueueRepositoryFactory create(
      Provider<QueueDao> queueDaoProvider) {
    return new AppModule_ProvideQueueRepositoryFactory(queueDaoProvider);
  }

  public static QueueRepository provideQueueRepository(QueueDao queueDao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideQueueRepository(queueDao));
  }
}
