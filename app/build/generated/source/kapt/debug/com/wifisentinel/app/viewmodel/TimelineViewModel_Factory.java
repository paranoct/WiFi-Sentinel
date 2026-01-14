package com.wifisentinel.app.viewmodel;

import com.wifisentinel.app.report.ReportExporter;
import com.wifisentinel.core.storage.NetworkRepository;
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
public final class TimelineViewModel_Factory implements Factory<TimelineViewModel> {
  private final Provider<NetworkRepository> repositoryProvider;

  private final Provider<ReportExporter> reportExporterProvider;

  public TimelineViewModel_Factory(Provider<NetworkRepository> repositoryProvider,
      Provider<ReportExporter> reportExporterProvider) {
    this.repositoryProvider = repositoryProvider;
    this.reportExporterProvider = reportExporterProvider;
  }

  @Override
  public TimelineViewModel get() {
    return newInstance(repositoryProvider.get(), reportExporterProvider.get());
  }

  public static TimelineViewModel_Factory create(Provider<NetworkRepository> repositoryProvider,
      Provider<ReportExporter> reportExporterProvider) {
    return new TimelineViewModel_Factory(repositoryProvider, reportExporterProvider);
  }

  public static TimelineViewModel newInstance(NetworkRepository repository,
      ReportExporter reportExporter) {
    return new TimelineViewModel(repository, reportExporter);
  }
}
