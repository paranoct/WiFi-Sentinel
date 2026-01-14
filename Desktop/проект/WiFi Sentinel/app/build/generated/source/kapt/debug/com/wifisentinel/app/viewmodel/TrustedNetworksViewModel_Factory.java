package com.wifisentinel.app.viewmodel;

import com.wifisentinel.app.monitor.NetworkMonitor;
import com.wifisentinel.app.trusted.TrustedNetworkManager;
import com.wifisentinel.core.storage.NetworkRepository;
import com.wifisentinel.core.wifi.WifiScanner;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
    "KotlinInternalInJava",
    "cast"
})
public final class TrustedNetworksViewModel_Factory implements Factory<TrustedNetworksViewModel> {
  private final Provider<NetworkRepository> repositoryProvider;

  private final Provider<TrustedNetworkManager> trustedNetworkManagerProvider;

  private final Provider<NetworkMonitor> networkMonitorProvider;

  private final Provider<WifiScanner> wifiScannerProvider;

  public TrustedNetworksViewModel_Factory(Provider<NetworkRepository> repositoryProvider,
      Provider<TrustedNetworkManager> trustedNetworkManagerProvider,
      Provider<NetworkMonitor> networkMonitorProvider, Provider<WifiScanner> wifiScannerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.trustedNetworkManagerProvider = trustedNetworkManagerProvider;
    this.networkMonitorProvider = networkMonitorProvider;
    this.wifiScannerProvider = wifiScannerProvider;
  }

  @Override
  public TrustedNetworksViewModel get() {
    return newInstance(repositoryProvider.get(), trustedNetworkManagerProvider.get(), networkMonitorProvider.get(), wifiScannerProvider.get());
  }

  public static TrustedNetworksViewModel_Factory create(
      Provider<NetworkRepository> repositoryProvider,
      Provider<TrustedNetworkManager> trustedNetworkManagerProvider,
      Provider<NetworkMonitor> networkMonitorProvider, Provider<WifiScanner> wifiScannerProvider) {
    return new TrustedNetworksViewModel_Factory(repositoryProvider, trustedNetworkManagerProvider, networkMonitorProvider, wifiScannerProvider);
  }

  public static TrustedNetworksViewModel newInstance(NetworkRepository repository,
      TrustedNetworkManager trustedNetworkManager, NetworkMonitor networkMonitor,
      WifiScanner wifiScanner) {
    return new TrustedNetworksViewModel(repository, trustedNetworkManager, networkMonitor, wifiScanner);
  }
}
