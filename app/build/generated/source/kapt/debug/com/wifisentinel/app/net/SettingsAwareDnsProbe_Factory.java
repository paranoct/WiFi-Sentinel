package com.wifisentinel.app.net;

import com.wifisentinel.core.storage.settings.SettingsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SettingsAwareDnsProbe_Factory implements Factory<SettingsAwareDnsProbe> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public SettingsAwareDnsProbe_Factory(Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public SettingsAwareDnsProbe get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static SettingsAwareDnsProbe_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new SettingsAwareDnsProbe_Factory(settingsRepositoryProvider);
  }

  public static SettingsAwareDnsProbe newInstance(SettingsRepository settingsRepository) {
    return new SettingsAwareDnsProbe(settingsRepository);
  }
}
