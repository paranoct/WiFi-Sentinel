package com.wifisentinel.app.di;

import com.wifisentinel.core.risk.RiskEngine;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class RiskModule_ProvideRiskEngineFactory implements Factory<RiskEngine> {
  @Override
  public RiskEngine get() {
    return provideRiskEngine();
  }

  public static RiskModule_ProvideRiskEngineFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RiskEngine provideRiskEngine() {
    return Preconditions.checkNotNullFromProvides(RiskModule.INSTANCE.provideRiskEngine());
  }

  private static final class InstanceHolder {
    private static final RiskModule_ProvideRiskEngineFactory INSTANCE = new RiskModule_ProvideRiskEngineFactory();
  }
}
