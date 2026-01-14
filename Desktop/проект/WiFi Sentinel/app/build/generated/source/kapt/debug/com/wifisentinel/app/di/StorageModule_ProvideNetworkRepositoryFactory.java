package com.wifisentinel.app.di;

import com.wifisentinel.core.storage.NetworkRepository;
import com.wifisentinel.core.storage.db.WiFiSentinelDatabase;
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
public final class StorageModule_ProvideNetworkRepositoryFactory implements Factory<NetworkRepository> {
  private final Provider<WiFiSentinelDatabase> dbProvider;

  public StorageModule_ProvideNetworkRepositoryFactory(Provider<WiFiSentinelDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public NetworkRepository get() {
    return provideNetworkRepository(dbProvider.get());
  }

  public static StorageModule_ProvideNetworkRepositoryFactory create(
      Provider<WiFiSentinelDatabase> dbProvider) {
    return new StorageModule_ProvideNetworkRepositoryFactory(dbProvider);
  }

  public static NetworkRepository provideNetworkRepository(WiFiSentinelDatabase db) {
    return Preconditions.checkNotNullFromProvides(StorageModule.INSTANCE.provideNetworkRepository(db));
  }
}
