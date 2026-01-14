package com.wifisentinel.app.di;

import android.content.Context;
import com.wifisentinel.core.storage.settings.SettingsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class StorageModule_ProvideSettingsRepositoryFactory implements Factory<SettingsRepository> {
  private final Provider<Context> contextProvider;

  public StorageModule_ProvideSettingsRepositoryFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SettingsRepository get() {
    return provideSettingsRepository(contextProvider.get());
  }

  public static StorageModule_ProvideSettingsRepositoryFactory create(
      Provider<Context> contextProvider) {
    return new StorageModule_ProvideSettingsRepositoryFactory(contextProvider);
  }

  public static SettingsRepository provideSettingsRepository(Context context) {
    return Preconditions.checkNotNullFromProvides(StorageModule.INSTANCE.provideSettingsRepository(context));
  }
}
