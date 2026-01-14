package com.wifisentinel.app.viewmodel;

import com.wifisentinel.core.storage.NetworkRepository;
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
public final class NetworkDetailsViewModel_Factory implements Factory<NetworkDetailsViewModel> {
  private final Provider<NetworkRepository> repositoryProvider;

  public NetworkDetailsViewModel_Factory(Provider<NetworkRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public NetworkDetailsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static NetworkDetailsViewModel_Factory create(
      Provider<NetworkRepository> repositoryProvider) {
    return new NetworkDetailsViewModel_Factory(repositoryProvider);
  }

  public static NetworkDetailsViewModel newInstance(NetworkRepository repository) {
    return new NetworkDetailsViewModel(repository);
  }
}
