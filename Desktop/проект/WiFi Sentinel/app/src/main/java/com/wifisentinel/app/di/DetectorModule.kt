package com.wifisentinel.app.di

import com.wifisentinel.core.detectors.CaptivePortalDetector
import com.wifisentinel.core.detectors.Detector
import com.wifisentinel.core.detectors.DisconnectAnomalyDetector
import com.wifisentinel.core.detectors.DnsIntegrityDetector
import com.wifisentinel.core.detectors.EvilTwinDetector
import com.wifisentinel.core.detectors.GatewayAnomalyDetector
import com.wifisentinel.core.detectors.MeshNewBssidDetector
import com.wifisentinel.core.detectors.PinnedDnsDetector
import com.wifisentinel.core.detectors.UnusualBehaviorDetector
import com.wifisentinel.core.detectors.WeakSecurityDetector
import com.wifisentinel.core.detectors.LookalikeSsidDetector
import com.wifisentinel.core.net.CaptivePortalProbe
import com.wifisentinel.core.net.DnsProbe
import com.wifisentinel.core.storage.settings.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetectorModule {
    @Provides
    @Singleton
    fun provideDetectors(
        dnsProbe: DnsProbe,
        portalProbe: CaptivePortalProbe,
        settingsRepository: SettingsRepository
    ): List<Detector> {
        return listOf(
            EvilTwinDetector(),
            CaptivePortalDetector(portalProbe),
            WeakSecurityDetector(),
            DnsIntegrityDetector(
                dnsProbe = dnsProbe,
                enabledProvider = { settingsRepository.settings.first().dnsCheckEnabled }
            ),
            PinnedDnsDetector(),
            GatewayAnomalyDetector(),
            DisconnectAnomalyDetector(),
            MeshNewBssidDetector(),
            UnusualBehaviorDetector(),
            LookalikeSsidDetector()
        )
    }
}
