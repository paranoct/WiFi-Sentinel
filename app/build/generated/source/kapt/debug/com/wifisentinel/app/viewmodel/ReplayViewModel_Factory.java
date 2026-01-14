package com.wifisentinel.app.viewmodel;

import com.wifisentinel.app.replay.ReplayManager;
import com.wifisentinel.app.report.ReportExporter;
import com.wifisentinel.core.storage.settings.SettingsRepository;
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
public final class ReplayViewModel_Factory implements Factory<ReplayViewModel> {
  private final Provider<ReplayManager> replayManagerProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<ReportExporter> reportExporterProvider;

  public ReplayViewModel_Factory(Provider<ReplayManager> replayManagerProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ReportExporter> reportExporterProvider) {
    this.replayManagerProvider = replayManagerProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.reportExporterProvider = reportExporterProvider;
  }

  @Override
  public ReplayViewModel get() {
    return newInstance(replayManagerProvider.get(), settingsRepositoryProvider.get(), reportExporterProvider.get());
  }

  public static ReplayViewModel_Factory create(Provider<ReplayManager> replayManagerProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<ReportExporter> reportExporterProvider) {
    return new ReplayViewModel_Factory(replayManagerProvider, settingsRepositoryProvider, reportExporterProvider);
  }

  public static ReplayViewModel newInstance(ReplayManager replayManager,
      SettingsRepository settingsRepository, ReportExporter reportExporter) {
    return new ReplayViewModel(replayManager, settingsRepository, reportExporter);
  }
}
