package com.wifisentinel.app.report;

import android.content.Context;
import com.wifisentinel.core.risk.RiskEngine;
import com.wifisentinel.core.storage.NetworkRepository;
import com.wifisentinel.core.storage.settings.SettingsRepository;
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
public final class ReportExporter_Factory implements Factory<ReportExporter> {
  private final Provider<NetworkRepository> repositoryProvider;

  private final Provider<RiskEngine> riskEngineProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<Context> contextProvider;

  public ReportExporter_Factory(Provider<NetworkRepository> repositoryProvider,
      Provider<RiskEngine> riskEngineProvider,
      Provider<SettingsRepository> settingsRepositoryProvider, Provider<Context> contextProvider) {
    this.repositoryProvider = repositoryProvider;
    this.riskEngineProvider = riskEngineProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public ReportExporter get() {
    return newInstance(repositoryProvider.get(), riskEngineProvider.get(), settingsRepositoryProvider.get(), contextProvider.get());
  }

  public static ReportExporter_Factory create(Provider<NetworkRepository> repositoryProvider,
      Provider<RiskEngine> riskEngineProvider,
      Provider<SettingsRepository> settingsRepositoryProvider, Provider<Context> contextProvider) {
    return new ReportExporter_Factory(repositoryProvider, riskEngineProvider, settingsRepositoryProvider, contextProvider);
  }

  public static ReportExporter newInstance(NetworkRepository repository, RiskEngine riskEngine,
      SettingsRepository settingsRepository, Context context) {
    return new ReportExporter(repository, riskEngine, settingsRepository, context);
  }
}
