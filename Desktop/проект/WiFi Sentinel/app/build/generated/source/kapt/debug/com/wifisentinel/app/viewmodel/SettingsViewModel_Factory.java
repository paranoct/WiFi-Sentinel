package com.wifisentinel.app.viewmodel;

import com.wifisentinel.app.service.AlwaysOnServiceController;
import com.wifisentinel.app.work.BackgroundScanScheduler;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<BackgroundScanScheduler> backgroundScanSchedulerProvider;

  private final Provider<AlwaysOnServiceController> alwaysOnServiceControllerProvider;

  public SettingsViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<BackgroundScanScheduler> backgroundScanSchedulerProvider,
      Provider<AlwaysOnServiceController> alwaysOnServiceControllerProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.backgroundScanSchedulerProvider = backgroundScanSchedulerProvider;
    this.alwaysOnServiceControllerProvider = alwaysOnServiceControllerProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(settingsRepositoryProvider.get(), backgroundScanSchedulerProvider.get(), alwaysOnServiceControllerProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<BackgroundScanScheduler> backgroundScanSchedulerProvider,
      Provider<AlwaysOnServiceController> alwaysOnServiceControllerProvider) {
    return new SettingsViewModel_Factory(settingsRepositoryProvider, backgroundScanSchedulerProvider, alwaysOnServiceControllerProvider);
  }

  public static SettingsViewModel newInstance(SettingsRepository settingsRepository,
      BackgroundScanScheduler backgroundScanScheduler,
      AlwaysOnServiceController alwaysOnServiceController) {
    return new SettingsViewModel(settingsRepository, backgroundScanScheduler, alwaysOnServiceController);
  }
}
