package com.wifisentinel.app.net

import com.wifisentinel.core.net.DnsProbe
import com.wifisentinel.core.net.DohProviders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import java.net.InetAddress
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsAwareDnsProbe @Inject constructor(
) : DnsProbe {
    private val client = OkHttpClient.Builder().build()
    private var cachedDoh: DnsOverHttps? = null
    private val googleProvider = DohProviders.defaults.firstOrNull { it.id == GOOGLE_DOH_PROVIDER_ID }
        ?: DohProviders.defaults.first()

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

    private fun doh(): DnsOverHttps {
        val cached = cachedDoh
        return if (cached != null) {
            cached
        } else {
            val doh = DnsOverHttps.Builder()
                .client(client)
                .url(googleProvider.url.toHttpUrl())
                .build()
            cachedDoh = doh
            doh
        }
    }

    private companion object {
        const val GOOGLE_DOH_PROVIDER_ID = "google"
    }
}
