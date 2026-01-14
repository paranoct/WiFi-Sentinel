package com.wifisentinel.app.trusted;

import android.content.Context;
import com.wifisentinel.core.storage.NetworkRepository;
import com.wifisentinel.core.wifi.NetworkSnapshotProvider;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class TrustedNetworkManager_Factory implements Factory<TrustedNetworkManager> {
  private final Provider<Context> contextProvider;

  private final Provider<NetworkSnapshotProvider> snapshotProvider;

  private final Provider<NetworkRepository> repositoryProvider;

  public TrustedNetworkManager_Factory(Provider<Context> contextProvider,
      Provider<NetworkSnapshotProvider> snapshotProvider,
      Provider<NetworkRepository> repositoryProvider) {
    this.contextProvider = contextProvider;
    this.snapshotProvider = snapshotProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public TrustedNetworkManager get() {
    return newInstance(contextProvider.get(), snapshotProvider.get(), repositoryProvider.get());
  }

  public static TrustedNetworkManager_Factory create(Provider<Context> contextProvider,
      Provider<NetworkSnapshotProvider> snapshotProvider,
      Provider<NetworkRepository> repositoryProvider) {
    return new TrustedNetworkManager_Factory(contextProvider, snapshotProvider, repositoryProvider);
  }

  public static TrustedNetworkManager newInstance(Context context,
      NetworkSnapshotProvider snapshotProvider, NetworkRepository repository) {
    return new TrustedNetworkManager(context, snapshotProvider, repository);
  }
}
