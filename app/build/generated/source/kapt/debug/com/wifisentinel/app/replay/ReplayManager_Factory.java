package com.wifisentinel.app.replay;

import android.content.Context;
import com.wifisentinel.core.net.CaptivePortalProbe;
import com.wifisentinel.core.net.DnsProbe;
import com.wifisentinel.core.storage.NetworkRepository;
import com.wifisentinel.core.storage.settings.SettingsRepository;
import com.wifisentinel.core.wifi.WifiScanner;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
    "KotlinInternalInJava",
    "cast"
})
public final class ReplayManager_Factory implements Factory<ReplayManager> {
  private final Provider<Context> contextProvider;

  private final Provider<NetworkRepository> repositoryProvider;

  private final Provider<WifiScanner> wifiScannerProvider;

  private final Provider<DnsProbe> dnsProbeProvider;

  private final Provider<CaptivePortalProbe> portalProbeProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public ReplayManager_Factory(Provider<Context> contextProvider,
      Provider<NetworkRepository> repositoryProvider, Provider<WifiScanner> wifiScannerProvider,
      Provider<DnsProbe> dnsProbeProvider, Provider<CaptivePortalProbe> portalProbeProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.repositoryProvider = repositoryProvider;
    this.wifiScannerProvider = wifiScannerProvider;
    this.dnsProbeProvider = dnsProbeProvider;
    this.portalProbeProvider = portalProbeProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public ReplayManager get() {
    return newInstance(contextProvider.get(), repositoryProvider.get(), wifiScannerProvider.get(), dnsProbeProvider.get(), portalProbeProvider.get(), settingsRepositoryProvider.get());
  }

  public static ReplayManager_Factory create(Provider<Context> contextProvider,
      Provider<NetworkRepository> repositoryProvider, Provider<WifiScanner> wifiScannerProvider,
      Provider<DnsProbe> dnsProbeProvider, Provider<CaptivePortalProbe> portalProbeProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new ReplayManager_Factory(contextProvider, repositoryProvider, wifiScannerProvider, dnsProbeProvider, portalProbeProvider, settingsRepositoryProvider);
  }

  public static ReplayManager newInstance(Context context, NetworkRepository repository,
      WifiScanner wifiScanner, DnsProbe dnsProbe, CaptivePortalProbe portalProbe,
      SettingsRepository settingsRepository) {
    return new ReplayManager(context, repository, wifiScanner, dnsProbe, portalProbe, settingsRepository);
  }
}
