package com.wifisentinel.app.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import com.wifisentinel.app.net.OkHttpCaptivePortalProbe
import com.wifisentinel.app.net.SettingsAwareDnsProbe
import com.wifisentinel.core.net.CaptivePortalProbe
import com.wifisentinel.core.net.DnsProbe
import com.wifisentinel.core.storage.settings.SettingsRepository
import com.wifisentinel.core.wifi.AndroidNetworkObserver
import com.wifisentinel.core.wifi.AndroidNetworkSnapshotProvider
import com.wifisentinel.core.wifi.AndroidWifiScanner
import com.wifisentinel.core.wifi.NetworkObserver
import com.wifisentinel.core.wifi.NetworkSnapshotProvider
import com.wifisentinel.core.wifi.WifiScanner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    fun provideWifiManager(@ApplicationContext context: Context): WifiManager {
        return context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    @Provides
    @Singleton
    fun provideSnapshotProvider(
        connectivityManager: ConnectivityManager,
        wifiManager: WifiManager
    ): NetworkSnapshotProvider {
        return AndroidNetworkSnapshotProvider(connectivityManager, wifiManager)
    }

    @Provides
    @Singleton
    fun provideWifiScanner(wifiManager: WifiManager): WifiScanner {
        return AndroidWifiScanner(wifiManager)
    }

    @Provides
    @Singleton
    fun provideNetworkObserver(
        connectivityManager: ConnectivityManager,
        snapshotProvider: NetworkSnapshotProvider
    ): NetworkObserver {
        return AndroidNetworkObserver(connectivityManager, snapshotProvider)
    }

    @Provides
    @Singleton
    fun provideDnsProbe(settingsRepository: SettingsRepository): DnsProbe {
        return SettingsAwareDnsProbe(settingsRepository)
    }

    @Provides
    @Singleton
    fun provideCaptivePortalProbe(): CaptivePortalProbe {
        return OkHttpCaptivePortalProbe()
    }
}
