package com.wifisentinel.app.di;

import android.content.Context;
import com.wifisentinel.core.storage.db.WiFiSentinelDatabase;
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
public final class StorageModule_ProvideDatabaseFactory implements Factory<WiFiSentinelDatabase> {
  private final Provider<Context> contextProvider;

  public StorageModule_ProvideDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public WiFiSentinelDatabase get() {
    return provideDatabase(contextProvider.get());
  }

  public static StorageModule_ProvideDatabaseFactory create(Provider<Context> contextProvider) {
    return new StorageModule_ProvideDatabaseFactory(contextProvider);
  }

  public static WiFiSentinelDatabase provideDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(StorageModule.INSTANCE.provideDatabase(context));
  }
}
