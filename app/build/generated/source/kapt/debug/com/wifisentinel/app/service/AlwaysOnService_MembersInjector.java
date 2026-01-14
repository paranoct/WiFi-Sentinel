package com.wifisentinel.app.service;

import com.wifisentinel.app.monitor.NetworkMonitor;
import com.wifisentinel.app.notifications.NotificationHelper;
import com.wifisentinel.core.storage.settings.SettingsRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AlwaysOnService_MembersInjector implements MembersInjector<AlwaysOnService> {
  private final Provider<NetworkMonitor> networkMonitorProvider;

  private final Provider<NotificationHelper> notificationHelperProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public AlwaysOnService_MembersInjector(Provider<NetworkMonitor> networkMonitorProvider,
      Provider<NotificationHelper> notificationHelperProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.networkMonitorProvider = networkMonitorProvider;
    this.notificationHelperProvider = notificationHelperProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  public static MembersInjector<AlwaysOnService> create(
      Provider<NetworkMonitor> networkMonitorProvider,
      Provider<NotificationHelper> notificationHelperProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new AlwaysOnService_MembersInjector(networkMonitorProvider, notificationHelperProvider, settingsRepositoryProvider);
  }

  @Override
  public void injectMembers(AlwaysOnService instance) {
    injectNetworkMonitor(instance, networkMonitorProvider.get());
    injectNotificationHelper(instance, notificationHelperProvider.get());
    injectSettingsRepository(instance, settingsRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.wifisentinel.app.service.AlwaysOnService.networkMonitor")
  public static void injectNetworkMonitor(AlwaysOnService instance, NetworkMonitor networkMonitor) {
    instance.networkMonitor = networkMonitor;
  }

  @InjectedFieldSignature("com.wifisentinel.app.service.AlwaysOnService.notificationHelper")
  public static void injectNotificationHelper(AlwaysOnService instance,
      NotificationHelper notificationHelper) {
    instance.notificationHelper = notificationHelper;
  }

  @InjectedFieldSignature("com.wifisentinel.app.service.AlwaysOnService.settingsRepository")
  public static void injectSettingsRepository(AlwaysOnService instance,
      SettingsRepository settingsRepository) {
    instance.settingsRepository = settingsRepository;
  }
}
