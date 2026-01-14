package com.wifisentinel.app.work;

import androidx.hilt.work.WorkerAssistedFactory;
import androidx.work.ListenableWorker;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.processing.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(SingletonComponent.class)
@OriginatingElement(
    topLevelClass = NetworkHealthWorker.class
)
public interface NetworkHealthWorker_HiltModule {
  @Binds
  @IntoMap
  @StringKey("com.wifisentinel.app.work.NetworkHealthWorker")
  WorkerAssistedFactory<? extends ListenableWorker> bind(
      NetworkHealthWorker_AssistedFactory factory);
}
