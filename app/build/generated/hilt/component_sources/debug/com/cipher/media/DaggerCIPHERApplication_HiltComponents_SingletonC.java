package com.cipher.media;

import android.app.Activity;
import android.app.Service;
import android.content.ContentResolver;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.cipher.media.ads.AdManager;
import com.cipher.media.billing.BillingManager;
import com.cipher.media.billing.BillingRepository;
import com.cipher.media.cloud.CloudMetadataRepository;
import com.cipher.media.cloud.CloudinaryManager;
import com.cipher.media.cloud.VaultSyncEngine;
import com.cipher.media.data.encryption.CryptoUtil;
import com.cipher.media.data.encryption.KeystoreManager;
import com.cipher.media.data.encryption.VaultEncryptionManager;
import com.cipher.media.data.local.EncryptedDatabase;
import com.cipher.media.data.local.IntruderLogDao;
import com.cipher.media.data.local.MediaDatabase;
import com.cipher.media.data.local.VaultDao;
import com.cipher.media.data.repository.EqualizerRepository;
import com.cipher.media.data.repository.MediaRepository;
import com.cipher.media.di.AppModule_ProvideAdManagerFactory;
import com.cipher.media.di.AppModule_ProvideBillingManagerFactory;
import com.cipher.media.di.AppModule_ProvideBillingRepositoryFactory;
import com.cipher.media.di.AppModule_ProvideContentResolverFactory;
import com.cipher.media.di.AppModule_ProvideCryptoUtilFactory;
import com.cipher.media.di.AppModule_ProvideEncryptedDatabaseFactory;
import com.cipher.media.di.AppModule_ProvideEqualizerRepositoryFactory;
import com.cipher.media.di.AppModule_ProvideIntruderLogDaoFactory;
import com.cipher.media.di.AppModule_ProvideKeystoreManagerFactory;
import com.cipher.media.di.AppModule_ProvideMediaDatabaseFactory;
import com.cipher.media.di.AppModule_ProvideMediaRepositoryFactory;
import com.cipher.media.di.AppModule_ProvideQueueDaoFactory;
import com.cipher.media.di.AppModule_ProvideQueueRepositoryFactory;
import com.cipher.media.di.AppModule_ProvideVaultDaoFactory;
import com.cipher.media.di.AppModule_ProvideVaultEncryptionManagerFactory;
import com.cipher.media.di.CloudModule_ProvideCloudMetadataRepositoryFactory;
import com.cipher.media.di.CloudModule_ProvideCloudinaryManagerFactory;
import com.cipher.media.di.CloudModule_ProvideVaultSyncEngineFactory;
import com.cipher.media.di.PlayerModule_ProvideExoPlayerBuilderFactory;
import com.cipher.media.domain.usecase.GetLocalMediaUseCase;
import com.cipher.media.service.AudioPlaybackService;
import com.cipher.media.service.AudioPlaybackService_MembersInjector;
import com.cipher.media.ui.audio.AudioPlayerViewModel;
import com.cipher.media.ui.audio.AudioPlayerViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cipher.media.ui.audio.equalizer.EqualizerViewModel;
import com.cipher.media.ui.audio.equalizer.EqualizerViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cipher.media.ui.audio.queue.QueueRepository;
import com.cipher.media.ui.audio.queue.QueueViewModel;
import com.cipher.media.ui.audio.queue.QueueViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cipher.media.ui.audio.queue.model.QueueDao;
import com.cipher.media.ui.premium.PremiumViewModel;
import com.cipher.media.ui.premium.PremiumViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cipher.media.ui.search.SearchViewModel;
import com.cipher.media.ui.search.SearchViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cipher.media.ui.settings.SettingsViewModel;
import com.cipher.media.ui.settings.SettingsViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cipher.media.ui.settings.cloud.CloudSyncViewModel;
import com.cipher.media.ui.settings.cloud.CloudSyncViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cipher.media.ui.stealth.StealthViewModel;
import com.cipher.media.ui.stealth.StealthViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cipher.media.ui.vault.VaultViewModel;
import com.cipher.media.ui.vault.VaultViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cipher.media.ui.video.VideoBrowserViewModel;
import com.cipher.media.ui.video.VideoBrowserViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cipher.media.ui.video.VideoPlayerViewModel;
import com.cipher.media.ui.video.VideoPlayerViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cipher.media.ui.video.audio.VideoEqualizerViewModel;
import com.cipher.media.ui.video.audio.VideoEqualizerViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel;
import com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel_HiltModules_KeyModule_ProvideFactory;
import com.cipher.media.ui.video.subtitle.SubtitleViewModel;
import com.cipher.media.ui.video.subtitle.SubtitleViewModel_HiltModules_KeyModule_ProvideFactory;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerCIPHERApplication_HiltComponents_SingletonC {
  private DaggerCIPHERApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public CIPHERApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements CIPHERApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public CIPHERApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements CIPHERApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public CIPHERApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements CIPHERApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public CIPHERApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements CIPHERApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public CIPHERApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements CIPHERApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public CIPHERApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements CIPHERApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public CIPHERApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements CIPHERApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public CIPHERApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends CIPHERApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends CIPHERApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends CIPHERApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends CIPHERApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return ImmutableSet.<String>of(AudioPlayerViewModel_HiltModules_KeyModule_ProvideFactory.provide(), CloudSyncViewModel_HiltModules_KeyModule_ProvideFactory.provide(), EqualizerViewModel_HiltModules_KeyModule_ProvideFactory.provide(), PremiumViewModel_HiltModules_KeyModule_ProvideFactory.provide(), QueueViewModel_HiltModules_KeyModule_ProvideFactory.provide(), SearchViewModel_HiltModules_KeyModule_ProvideFactory.provide(), SettingsViewModel_HiltModules_KeyModule_ProvideFactory.provide(), StealthViewModel_HiltModules_KeyModule_ProvideFactory.provide(), SubtitleViewModel_HiltModules_KeyModule_ProvideFactory.provide(), VaultViewModel_HiltModules_KeyModule_ProvideFactory.provide(), VideoBrowserViewModel_HiltModules_KeyModule_ProvideFactory.provide(), VideoEnhancementViewModel_HiltModules_KeyModule_ProvideFactory.provide(), VideoEqualizerViewModel_HiltModules_KeyModule_ProvideFactory.provide(), VideoPlayerViewModel_HiltModules_KeyModule_ProvideFactory.provide());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends CIPHERApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AudioPlayerViewModel> audioPlayerViewModelProvider;

    private Provider<CloudSyncViewModel> cloudSyncViewModelProvider;

    private Provider<EqualizerViewModel> equalizerViewModelProvider;

    private Provider<PremiumViewModel> premiumViewModelProvider;

    private Provider<QueueViewModel> queueViewModelProvider;

    private Provider<SearchViewModel> searchViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<StealthViewModel> stealthViewModelProvider;

    private Provider<SubtitleViewModel> subtitleViewModelProvider;

    private Provider<VaultViewModel> vaultViewModelProvider;

    private Provider<VideoBrowserViewModel> videoBrowserViewModelProvider;

    private Provider<VideoEnhancementViewModel> videoEnhancementViewModelProvider;

    private Provider<VideoEqualizerViewModel> videoEqualizerViewModelProvider;

    private Provider<VideoPlayerViewModel> videoPlayerViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private GetLocalMediaUseCase getLocalMediaUseCase() {
      return new GetLocalMediaUseCase(singletonCImpl.provideMediaRepositoryProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.audioPlayerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.cloudSyncViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.equalizerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.premiumViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.queueViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.searchViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.stealthViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.subtitleViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.vaultViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.videoBrowserViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.videoEnhancementViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
      this.videoEqualizerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 12);
      this.videoPlayerViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 13);
    }

    @Override
    public Map<String, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(14).put("com.cipher.media.ui.audio.AudioPlayerViewModel", ((Provider) audioPlayerViewModelProvider)).put("com.cipher.media.ui.settings.cloud.CloudSyncViewModel", ((Provider) cloudSyncViewModelProvider)).put("com.cipher.media.ui.audio.equalizer.EqualizerViewModel", ((Provider) equalizerViewModelProvider)).put("com.cipher.media.ui.premium.PremiumViewModel", ((Provider) premiumViewModelProvider)).put("com.cipher.media.ui.audio.queue.QueueViewModel", ((Provider) queueViewModelProvider)).put("com.cipher.media.ui.search.SearchViewModel", ((Provider) searchViewModelProvider)).put("com.cipher.media.ui.settings.SettingsViewModel", ((Provider) settingsViewModelProvider)).put("com.cipher.media.ui.stealth.StealthViewModel", ((Provider) stealthViewModelProvider)).put("com.cipher.media.ui.video.subtitle.SubtitleViewModel", ((Provider) subtitleViewModelProvider)).put("com.cipher.media.ui.vault.VaultViewModel", ((Provider) vaultViewModelProvider)).put("com.cipher.media.ui.video.VideoBrowserViewModel", ((Provider) videoBrowserViewModelProvider)).put("com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel", ((Provider) videoEnhancementViewModelProvider)).put("com.cipher.media.ui.video.audio.VideoEqualizerViewModel", ((Provider) videoEqualizerViewModelProvider)).put("com.cipher.media.ui.video.VideoPlayerViewModel", ((Provider) videoPlayerViewModelProvider)).build();
    }

    @Override
    public Map<String, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<String, Object>of();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.cipher.media.ui.audio.AudioPlayerViewModel 
          return (T) new AudioPlayerViewModel(singletonCImpl.provideMediaRepositoryProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 1: // com.cipher.media.ui.settings.cloud.CloudSyncViewModel 
          return (T) new CloudSyncViewModel(singletonCImpl.provideVaultSyncEngineProvider.get(), singletonCImpl.provideCloudMetadataRepositoryProvider.get(), singletonCImpl.provideBillingRepositoryProvider.get());

          case 2: // com.cipher.media.ui.audio.equalizer.EqualizerViewModel 
          return (T) new EqualizerViewModel(singletonCImpl.provideEqualizerRepositoryProvider.get());

          case 3: // com.cipher.media.ui.premium.PremiumViewModel 
          return (T) new PremiumViewModel(singletonCImpl.provideBillingManagerProvider.get(), singletonCImpl.provideBillingRepositoryProvider.get());

          case 4: // com.cipher.media.ui.audio.queue.QueueViewModel 
          return (T) new QueueViewModel(singletonCImpl.provideQueueRepositoryProvider.get(), singletonCImpl.provideBillingRepositoryProvider.get());

          case 5: // com.cipher.media.ui.search.SearchViewModel 
          return (T) new SearchViewModel(singletonCImpl.provideContentResolverProvider.get());

          case 6: // com.cipher.media.ui.settings.SettingsViewModel 
          return (T) new SettingsViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 7: // com.cipher.media.ui.stealth.StealthViewModel 
          return (T) new StealthViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideIntruderLogDaoProvider.get());

          case 8: // com.cipher.media.ui.video.subtitle.SubtitleViewModel 
          return (T) new SubtitleViewModel(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // com.cipher.media.ui.vault.VaultViewModel 
          return (T) new VaultViewModel(singletonCImpl.provideVaultDaoProvider.get(), singletonCImpl.provideVaultEncryptionManagerProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 10: // com.cipher.media.ui.video.VideoBrowserViewModel 
          return (T) new VideoBrowserViewModel(viewModelCImpl.getLocalMediaUseCase());

          case 11: // com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel 
          return (T) new VideoEnhancementViewModel(singletonCImpl.provideBillingRepositoryProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 12: // com.cipher.media.ui.video.audio.VideoEqualizerViewModel 
          return (T) new VideoEqualizerViewModel(singletonCImpl.provideBillingRepositoryProvider.get());

          case 13: // com.cipher.media.ui.video.VideoPlayerViewModel 
          return (T) new VideoPlayerViewModel(singletonCImpl.exoPlayerBuilder(), singletonCImpl.provideBillingRepositoryProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends CIPHERApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends CIPHERApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectAudioPlaybackService(AudioPlaybackService audioPlaybackService) {
      injectAudioPlaybackService2(audioPlaybackService);
    }

    @CanIgnoreReturnValue
    private AudioPlaybackService injectAudioPlaybackService2(AudioPlaybackService instance) {
      AudioPlaybackService_MembersInjector.injectEqRepository(instance, singletonCImpl.provideEqualizerRepositoryProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends CIPHERApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<AdManager> provideAdManagerProvider;

    private Provider<ContentResolver> provideContentResolverProvider;

    private Provider<MediaRepository> provideMediaRepositoryProvider;

    private Provider<CloudinaryManager> provideCloudinaryManagerProvider;

    private Provider<CloudMetadataRepository> provideCloudMetadataRepositoryProvider;

    private Provider<KeystoreManager> provideKeystoreManagerProvider;

    private Provider<EncryptedDatabase> provideEncryptedDatabaseProvider;

    private Provider<VaultDao> provideVaultDaoProvider;

    private Provider<BillingManager> provideBillingManagerProvider;

    private Provider<BillingRepository> provideBillingRepositoryProvider;

    private Provider<VaultSyncEngine> provideVaultSyncEngineProvider;

    private Provider<EqualizerRepository> provideEqualizerRepositoryProvider;

    private Provider<MediaDatabase> provideMediaDatabaseProvider;

    private Provider<QueueDao> provideQueueDaoProvider;

    private Provider<QueueRepository> provideQueueRepositoryProvider;

    private Provider<IntruderLogDao> provideIntruderLogDaoProvider;

    private Provider<CryptoUtil> provideCryptoUtilProvider;

    private Provider<VaultEncryptionManager> provideVaultEncryptionManagerProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private androidx.media3.exoplayer.ExoPlayer.Builder exoPlayerBuilder() {
      return PlayerModule_ProvideExoPlayerBuilderFactory.provideExoPlayerBuilder(ApplicationContextModule_ProvideContextFactory.provideContext(applicationContextModule));
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideAdManagerProvider = DoubleCheck.provider(new SwitchingProvider<AdManager>(singletonCImpl, 0));
      this.provideContentResolverProvider = DoubleCheck.provider(new SwitchingProvider<ContentResolver>(singletonCImpl, 2));
      this.provideMediaRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<MediaRepository>(singletonCImpl, 1));
      this.provideCloudinaryManagerProvider = DoubleCheck.provider(new SwitchingProvider<CloudinaryManager>(singletonCImpl, 4));
      this.provideCloudMetadataRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<CloudMetadataRepository>(singletonCImpl, 5));
      this.provideKeystoreManagerProvider = DoubleCheck.provider(new SwitchingProvider<KeystoreManager>(singletonCImpl, 8));
      this.provideEncryptedDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<EncryptedDatabase>(singletonCImpl, 7));
      this.provideVaultDaoProvider = DoubleCheck.provider(new SwitchingProvider<VaultDao>(singletonCImpl, 6));
      this.provideBillingManagerProvider = DoubleCheck.provider(new SwitchingProvider<BillingManager>(singletonCImpl, 10));
      this.provideBillingRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<BillingRepository>(singletonCImpl, 9));
      this.provideVaultSyncEngineProvider = DoubleCheck.provider(new SwitchingProvider<VaultSyncEngine>(singletonCImpl, 3));
      this.provideEqualizerRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<EqualizerRepository>(singletonCImpl, 11));
      this.provideMediaDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<MediaDatabase>(singletonCImpl, 14));
      this.provideQueueDaoProvider = DoubleCheck.provider(new SwitchingProvider<QueueDao>(singletonCImpl, 13));
      this.provideQueueRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<QueueRepository>(singletonCImpl, 12));
      this.provideIntruderLogDaoProvider = DoubleCheck.provider(new SwitchingProvider<IntruderLogDao>(singletonCImpl, 15));
      this.provideCryptoUtilProvider = DoubleCheck.provider(new SwitchingProvider<CryptoUtil>(singletonCImpl, 17));
      this.provideVaultEncryptionManagerProvider = DoubleCheck.provider(new SwitchingProvider<VaultEncryptionManager>(singletonCImpl, 16));
    }

    @Override
    public void injectCIPHERApplication(CIPHERApplication cIPHERApplication) {
      injectCIPHERApplication2(cIPHERApplication);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @CanIgnoreReturnValue
    private CIPHERApplication injectCIPHERApplication2(CIPHERApplication instance) {
      CIPHERApplication_MembersInjector.injectAdManager(instance, provideAdManagerProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.cipher.media.ads.AdManager 
          return (T) AppModule_ProvideAdManagerFactory.provideAdManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 1: // com.cipher.media.data.repository.MediaRepository 
          return (T) AppModule_ProvideMediaRepositoryFactory.provideMediaRepository(singletonCImpl.provideContentResolverProvider.get());

          case 2: // android.content.ContentResolver 
          return (T) AppModule_ProvideContentResolverFactory.provideContentResolver(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.cipher.media.cloud.VaultSyncEngine 
          return (T) CloudModule_ProvideVaultSyncEngineFactory.provideVaultSyncEngine(singletonCImpl.provideCloudinaryManagerProvider.get(), singletonCImpl.provideCloudMetadataRepositoryProvider.get(), singletonCImpl.provideVaultDaoProvider.get(), singletonCImpl.provideBillingRepositoryProvider.get());

          case 4: // com.cipher.media.cloud.CloudinaryManager 
          return (T) CloudModule_ProvideCloudinaryManagerFactory.provideCloudinaryManager();

          case 5: // com.cipher.media.cloud.CloudMetadataRepository 
          return (T) CloudModule_ProvideCloudMetadataRepositoryFactory.provideCloudMetadataRepository();

          case 6: // com.cipher.media.data.local.VaultDao 
          return (T) AppModule_ProvideVaultDaoFactory.provideVaultDao(singletonCImpl.provideEncryptedDatabaseProvider.get());

          case 7: // com.cipher.media.data.local.EncryptedDatabase 
          return (T) AppModule_ProvideEncryptedDatabaseFactory.provideEncryptedDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideKeystoreManagerProvider.get());

          case 8: // com.cipher.media.data.encryption.KeystoreManager 
          return (T) AppModule_ProvideKeystoreManagerFactory.provideKeystoreManager();

          case 9: // com.cipher.media.billing.BillingRepository 
          return (T) AppModule_ProvideBillingRepositoryFactory.provideBillingRepository(singletonCImpl.provideBillingManagerProvider.get());

          case 10: // com.cipher.media.billing.BillingManager 
          return (T) AppModule_ProvideBillingManagerFactory.provideBillingManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 11: // com.cipher.media.data.repository.EqualizerRepository 
          return (T) AppModule_ProvideEqualizerRepositoryFactory.provideEqualizerRepository(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 12: // com.cipher.media.ui.audio.queue.QueueRepository 
          return (T) AppModule_ProvideQueueRepositoryFactory.provideQueueRepository(singletonCImpl.provideQueueDaoProvider.get());

          case 13: // com.cipher.media.ui.audio.queue.model.QueueDao 
          return (T) AppModule_ProvideQueueDaoFactory.provideQueueDao(singletonCImpl.provideMediaDatabaseProvider.get());

          case 14: // com.cipher.media.data.local.MediaDatabase 
          return (T) AppModule_ProvideMediaDatabaseFactory.provideMediaDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 15: // com.cipher.media.data.local.IntruderLogDao 
          return (T) AppModule_ProvideIntruderLogDaoFactory.provideIntruderLogDao(singletonCImpl.provideEncryptedDatabaseProvider.get());

          case 16: // com.cipher.media.data.encryption.VaultEncryptionManager 
          return (T) AppModule_ProvideVaultEncryptionManagerFactory.provideVaultEncryptionManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideKeystoreManagerProvider.get(), singletonCImpl.provideCryptoUtilProvider.get());

          case 17: // com.cipher.media.data.encryption.CryptoUtil 
          return (T) AppModule_ProvideCryptoUtilFactory.provideCryptoUtil();

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
