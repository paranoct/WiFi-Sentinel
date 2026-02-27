package com.wifisentinel.app.di

import com.wifisentinel.core.risk.RiskEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RiskModule {
    @Provides
    @Singleton
    fun provideRiskEngine(): RiskEngine = RiskEngine()
}
