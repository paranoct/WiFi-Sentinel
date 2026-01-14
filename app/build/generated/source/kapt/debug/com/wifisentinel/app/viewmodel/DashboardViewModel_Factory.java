package com.wifisentinel.app.viewmodel;

import android.content.Context;
import com.wifisentinel.app.monitor.NetworkMonitor;
import com.wifisentinel.app.report.ReportExporter;
import com.wifisentinel.app.trusted.TrustedNetworkManager;
import com.wifisentinel.core.risk.RiskEngine;
import com.wifisentinel.core.storage.NetworkRepository;
import com.wifisentinel.core.storage.settings.SettingsRepository;
import com.wifisentinel.core.wifi.WifiScanner;
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
    "KotlinInternalInJava",
    "cast"
})
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<Context> contextProvider;

  private final Provider<NetworkRepository> repositoryProvider;

  private final Provider<RiskEngine> riskEngineProvider;

  private final Provider<TrustedNetworkManager> trustedNetworkManagerProvider;

  private final Provider<NetworkMonitor> networkMonitorProvider;

  private final Provider<ReportExporter> reportExporterProvider;

  private final Provider<WifiScanner> wifiScannerProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public DashboardViewModel_Factory(Provider<Context> contextProvider,
      Provider<NetworkRepository> repositoryProvider, Provider<RiskEngine> riskEngineProvider,
      Provider<TrustedNetworkManager> trustedNetworkManagerProvider,
      Provider<NetworkMonitor> networkMonitorProvider,
      Provider<ReportExporter> reportExporterProvider, Provider<WifiScanner> wifiScannerProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.repositoryProvider = repositoryProvider;
    this.riskEngineProvider = riskEngineProvider;
    this.trustedNetworkManagerProvider = trustedNetworkManagerProvider;
    this.networkMonitorProvider = networkMonitorProvider;
    this.reportExporterProvider = reportExporterProvider;
    this.wifiScannerProvider = wifiScannerProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(contextProvider.get(), repositoryProvider.get(), riskEngineProvider.get(), trustedNetworkManagerProvider.get(), networkMonitorProvider.get(), reportExporterProvider.get(), wifiScannerProvider.get(), settingsRepositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(Provider<Context> contextProvider,
      Provider<NetworkRepository> repositoryProvider, Provider<RiskEngine> riskEngineProvider,
      Provider<TrustedNetworkManager> trustedNetworkManagerProvider,
      Provider<NetworkMonitor> networkMonitorProvider,
      Provider<ReportExporter> reportExporterProvider, Provider<WifiScanner> wifiScannerProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new DashboardViewModel_Factory(contextProvider, repositoryProvider, riskEngineProvider, trustedNetworkManagerProvider, networkMonitorProvider, reportExporterProvider, wifiScannerProvider, settingsRepositoryProvider);
  }

  public static DashboardViewModel newInstance(Context context, NetworkRepository repository,
      RiskEngine riskEngine, TrustedNetworkManager trustedNetworkManager,
      NetworkMonitor networkMonitor, ReportExporter reportExporter, WifiScanner wifiScanner,
      SettingsRepository settingsRepository) {
    return new DashboardViewModel(context, repository, riskEngine, trustedNetworkManager, networkMonitor, reportExporter, wifiScanner, settingsRepository);
  }
}
