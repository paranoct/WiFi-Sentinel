package com.wifisentinel.app.net

import com.wifisentinel.core.net.DnsProbe
import com.wifisentinel.core.net.DohProviders
import com.wifisentinel.core.storage.settings.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import java.net.InetAddress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsAwareDnsProbe @Inject constructor(
    private val settingsRepository: SettingsRepository
) : DnsProbe {
    private val client = OkHttpClient.Builder().build()
    private var cachedProviderId: String? = null
    private var cachedDoh: DnsOverHttps? = null

    override suspend fun resolveSystem(domain: String): List<String> = withContext(Dispatchers.IO) {
        try {
            InetAddress.getAllByName(domain).mapNotNull { it.hostAddress }
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun resolveDoh(domain: String): List<String> = withContext(Dispatchers.IO) {
        try {
            doh().lookup(domain).mapNotNull { it.hostAddress }
        } catch (_: Exception) {
            emptyList()
        }
    }

    private suspend fun doh(): DnsOverHttps {
        val settings = settingsRepository.settings.first()
        val provider = DohProviders.defaults.firstOrNull { it.id == settings.dohProviderId }
            ?: DohProviders.defaults.first()

        val cached = cachedDoh
        return if (cached != null && cachedProviderId == provider.id) {
            cached
        } else {
            val doh = DnsOverHttps.Builder()
                .client(client)
                .url(provider.url.toHttpUrl())
                .build()
            cachedProviderId = provider.id
            cachedDoh = doh
            doh
        }
    }
}
