package com.wifisentinel.app.monitor;

import android.content.Context;
import com.wifisentinel.app.notifications.NotificationHelper;
import com.wifisentinel.core.detectors.Detector;
import com.wifisentinel.core.storage.NetworkRepository;
import com.wifisentinel.core.storage.settings.SettingsRepository;
import com.wifisentinel.core.wifi.NetworkObserver;
import com.wifisentinel.core.wifi.NetworkSnapshotProvider;
import com.wifisentinel.core.wifi.WifiScanner;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata({
    "dagger.hilt.android.qualifiers.ApplicationContext",
    "com.wifisentinel.app.di.ApplicationScope"
})
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
public final class NetworkMonitor_Factory implements Factory<NetworkMonitor> {
  private final Provider<Context> contextProvider;

  private final Provider<NetworkObserver> networkObserverProvider;

  private final Provider<NetworkSnapshotProvider> snapshotProvider;

  private final Provider<WifiScanner> wifiScannerProvider;

  private final Provider<NetworkRepository> repositoryProvider;

  private final Provider<List<Detector>> detectorsProvider;

  private final Provider<NotificationHelper> notificationHelperProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<CoroutineScope> appScopeProvider;

  public NetworkMonitor_Factory(Provider<Context> contextProvider,
      Provider<NetworkObserver> networkObserverProvider,
      Provider<NetworkSnapshotProvider> snapshotProvider, Provider<WifiScanner> wifiScannerProvider,
      Provider<NetworkRepository> repositoryProvider, Provider<List<Detector>> detectorsProvider,
      Provider<NotificationHelper> notificationHelperProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<CoroutineScope> appScopeProvider) {
    this.contextProvider = contextProvider;
    this.networkObserverProvider = networkObserverProvider;
    this.snapshotProvider = snapshotProvider;
    this.wifiScannerProvider = wifiScannerProvider;
    this.repositoryProvider = repositoryProvider;
    this.detectorsProvider = detectorsProvider;
    this.notificationHelperProvider = notificationHelperProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.appScopeProvider = appScopeProvider;
  }

  @Override
  public NetworkMonitor get() {
    return newInstance(contextProvider.get(), networkObserverProvider.get(), snapshotProvider.get(), wifiScannerProvider.get(), repositoryProvider.get(), detectorsProvider.get(), notificationHelperProvider.get(), settingsRepositoryProvider.get(), appScopeProvider.get());
  }

  public static NetworkMonitor_Factory create(Provider<Context> contextProvider,
      Provider<NetworkObserver> networkObserverProvider,
      Provider<NetworkSnapshotProvider> snapshotProvider, Provider<WifiScanner> wifiScannerProvider,
      Provider<NetworkRepository> repositoryProvider, Provider<List<Detector>> detectorsProvider,
      Provider<NotificationHelper> notificationHelperProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<CoroutineScope> appScopeProvider) {
    return new NetworkMonitor_Factory(contextProvider, networkObserverProvider, snapshotProvider, wifiScannerProvider, repositoryProvider, detectorsProvider, notificationHelperProvider, settingsRepositoryProvider, appScopeProvider);
  }

  public static NetworkMonitor newInstance(Context context, NetworkObserver networkObserver,
      NetworkSnapshotProvider snapshotProvider, WifiScanner wifiScanner,
      NetworkRepository repository, List<Detector> detectors, NotificationHelper notificationHelper,
      SettingsRepository settingsRepository, CoroutineScope appScope) {
    return new NetworkMonitor(context, networkObserver, snapshotProvider, wifiScanner, repository, detectors, notificationHelper, settingsRepository, appScope);
  }
}
