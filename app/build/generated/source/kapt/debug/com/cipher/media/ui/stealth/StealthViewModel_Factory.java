package com.cipher.media.ui.stealth;

import android.content.Context;
import com.cipher.media.data.local.IntruderLogDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class StealthViewModel_Factory implements Factory<StealthViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<IntruderLogDao> intruderLogDaoProvider;

  public StealthViewModel_Factory(Provider<Context> contextProvider,
      Provider<IntruderLogDao> intruderLogDaoProvider) {
    this.contextProvider = contextProvider;
    this.intruderLogDaoProvider = intruderLogDaoProvider;
  }

  @Override
  public StealthViewModel get() {
    return newInstance(contextProvider.get(), intruderLogDaoProvider.get());
  }

  public static StealthViewModel_Factory create(Provider<Context> contextProvider,
      Provider<IntruderLogDao> intruderLogDaoProvider) {
    return new StealthViewModel_Factory(contextProvider, intruderLogDaoProvider);
  }

  public static StealthViewModel newInstance(Context context, IntruderLogDao intruderLogDao) {
    return new StealthViewModel(context, intruderLogDao);
  }
}
