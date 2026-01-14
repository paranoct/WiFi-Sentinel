package com.wifisentinel.app.service;

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
public final class BootReceiver_MembersInjector implements MembersInjector<BootReceiver> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<AlwaysOnServiceController> alwaysOnServiceControllerProvider;

  public BootReceiver_MembersInjector(Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<AlwaysOnServiceController> alwaysOnServiceControllerProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.alwaysOnServiceControllerProvider = alwaysOnServiceControllerProvider;
  }

  public static MembersInjector<BootReceiver> create(
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<AlwaysOnServiceController> alwaysOnServiceControllerProvider) {
    return new BootReceiver_MembersInjector(settingsRepositoryProvider, alwaysOnServiceControllerProvider);
  }

  @Override
  public void injectMembers(BootReceiver instance) {
    injectSettingsRepository(instance, settingsRepositoryProvider.get());
    injectAlwaysOnServiceController(instance, alwaysOnServiceControllerProvider.get());
  }

  @InjectedFieldSignature("com.wifisentinel.app.service.BootReceiver.settingsRepository")
  public static void injectSettingsRepository(BootReceiver instance,
      SettingsRepository settingsRepository) {
    instance.settingsRepository = settingsRepository;
  }

  @InjectedFieldSignature("com.wifisentinel.app.service.BootReceiver.alwaysOnServiceController")
  public static void injectAlwaysOnServiceController(BootReceiver instance,
      AlwaysOnServiceController alwaysOnServiceController) {
    instance.alwaysOnServiceController = alwaysOnServiceController;
  }
}
