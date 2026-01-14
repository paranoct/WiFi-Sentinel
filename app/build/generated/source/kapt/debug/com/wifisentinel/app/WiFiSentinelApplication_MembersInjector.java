package com.wifisentinel.app;

import androidx.hilt.work.HiltWorkerFactory;
import com.wifisentinel.app.di.ApplicationScope;
import com.wifisentinel.app.monitor.NetworkMonitor;
import com.wifisentinel.app.service.AlwaysOnServiceController;
import com.wifisentinel.app.work.BackgroundScanScheduler;
import com.wifisentinel.core.storage.settings.SettingsRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

@QualifierMetadata("com.wifisentinel.app.di.ApplicationScope")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class WiFiSentinelApplication_MembersInjector implements MembersInjector<WiFiSentinelApplication> {
  private final Provider<NetworkMonitor> networkMonitorProvider;

  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  private final Provider<BackgroundScanScheduler> backgroundScanSchedulerProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<AlwaysOnServiceController> alwaysOnServiceControllerProvider;

  private final Provider<CoroutineScope> appScopeProvider;

  public WiFiSentinelApplication_MembersInjector(Provider<NetworkMonitor> networkMonitorProvider,
      Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<BackgroundScanScheduler> backgroundScanSchedulerProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<AlwaysOnServiceController> alwaysOnServiceControllerProvider,
      Provider<CoroutineScope> appScopeProvider) {
    this.networkMonitorProvider = networkMonitorProvider;
    this.workerFactoryProvider = workerFactoryProvider;
    this.backgroundScanSchedulerProvider = backgroundScanSchedulerProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.alwaysOnServiceControllerProvider = alwaysOnServiceControllerProvider;
    this.appScopeProvider = appScopeProvider;
  }

  public static MembersInjector<WiFiSentinelApplication> create(
      Provider<NetworkMonitor> networkMonitorProvider,
      Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<BackgroundScanScheduler> backgroundScanSchedulerProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<AlwaysOnServiceController> alwaysOnServiceControllerProvider,
      Provider<CoroutineScope> appScopeProvider) {
    return new WiFiSentinelApplication_MembersInjector(networkMonitorProvider, workerFactoryProvider, backgroundScanSchedulerProvider, settingsRepositoryProvider, alwaysOnServiceControllerProvider, appScopeProvider);
  }

  @Override
  public void injectMembers(WiFiSentinelApplication instance) {
    injectNetworkMonitor(instance, networkMonitorProvider.get());
    injectWorkerFactory(instance, workerFactoryProvider.get());
    injectBackgroundScanScheduler(instance, backgroundScanSchedulerProvider.get());
    injectSettingsRepository(instance, settingsRepositoryProvider.get());
    injectAlwaysOnServiceController(instance, alwaysOnServiceControllerProvider.get());
    injectAppScope(instance, appScopeProvider.get());
  }

  @InjectedFieldSignature("com.wifisentinel.app.WiFiSentinelApplication.networkMonitor")
  public static void injectNetworkMonitor(WiFiSentinelApplication instance,
      NetworkMonitor networkMonitor) {
    instance.networkMonitor = networkMonitor;
  }

  @InjectedFieldSignature("com.wifisentinel.app.WiFiSentinelApplication.workerFactory")
  public static void injectWorkerFactory(WiFiSentinelApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }

  @InjectedFieldSignature("com.wifisentinel.app.WiFiSentinelApplication.backgroundScanScheduler")
  public static void injectBackgroundScanScheduler(WiFiSentinelApplication instance,
      BackgroundScanScheduler backgroundScanScheduler) {
    instance.backgroundScanScheduler = backgroundScanScheduler;
  }

  @InjectedFieldSignature("com.wifisentinel.app.WiFiSentinelApplication.settingsRepository")
  public static void injectSettingsRepository(WiFiSentinelApplication instance,
      SettingsRepository settingsRepository) {
    instance.settingsRepository = settingsRepository;
  }

  @InjectedFieldSignature("com.wifisentinel.app.WiFiSentinelApplication.alwaysOnServiceController")
  public static void injectAlwaysOnServiceController(WiFiSentinelApplication instance,
      AlwaysOnServiceController alwaysOnServiceController) {
    instance.alwaysOnServiceController = alwaysOnServiceController;
  }

  @InjectedFieldSignature("com.wifisentinel.app.WiFiSentinelApplication.appScope")
  @ApplicationScope
  public static void injectAppScope(WiFiSentinelApplication instance, CoroutineScope appScope) {
    instance.appScope = appScope;
  }
}
