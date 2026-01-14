package com.wifisentinel.app.work;

import android.content.Context;
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
public final class BackgroundScanScheduler_Factory implements Factory<BackgroundScanScheduler> {
  private final Provider<Context> contextProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public BackgroundScanScheduler_Factory(Provider<Context> contextProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public BackgroundScanScheduler get() {
    return newInstance(contextProvider.get(), settingsRepositoryProvider.get());
  }

  public static BackgroundScanScheduler_Factory create(Provider<Context> contextProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new BackgroundScanScheduler_Factory(contextProvider, settingsRepositoryProvider);
  }

  public static BackgroundScanScheduler newInstance(Context context,
      SettingsRepository settingsRepository) {
    return new BackgroundScanScheduler(context, settingsRepository);
  }
}
