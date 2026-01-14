package com.wifisentinel.app.di;

import com.wifisentinel.core.net.DnsProbe;
import com.wifisentinel.core.storage.settings.SettingsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NetworkModule_ProvideDnsProbeFactory implements Factory<DnsProbe> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public NetworkModule_ProvideDnsProbeFactory(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public DnsProbe get() {
    return provideDnsProbe(settingsRepositoryProvider.get());
  }

  public static NetworkModule_ProvideDnsProbeFactory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new NetworkModule_ProvideDnsProbeFactory(settingsRepositoryProvider);
  }

  public static DnsProbe provideDnsProbe(SettingsRepository settingsRepository) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideDnsProbe(settingsRepository));
  }
}
