package com.wifisentinel.app.work;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.wifisentinel.app.monitor.NetworkMonitor;
import dagger.internal.DaggerGenerated;
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
public final class NetworkHealthWorker_Factory {
  private final Provider<NetworkMonitor> networkMonitorProvider;

  public NetworkHealthWorker_Factory(Provider<NetworkMonitor> networkMonitorProvider) {
    this.networkMonitorProvider = networkMonitorProvider;
  }

  public NetworkHealthWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, networkMonitorProvider.get());
  }

  public static NetworkHealthWorker_Factory create(
      Provider<NetworkMonitor> networkMonitorProvider) {
    return new NetworkHealthWorker_Factory(networkMonitorProvider);
  }

  public static NetworkHealthWorker newInstance(Context appContext, WorkerParameters workerParams,
      NetworkMonitor networkMonitor) {
    return new NetworkHealthWorker(appContext, workerParams, networkMonitor);
  }
}
