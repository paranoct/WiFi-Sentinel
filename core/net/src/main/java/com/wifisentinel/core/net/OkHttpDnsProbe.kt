package com.wifisentinel.core.net

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import java.net.InetAddress

class OkHttpDnsProbe(
    dohUrl: String
) : DnsProbe {
    private val client = OkHttpClient.Builder().build()
    private val doh = DnsOverHttps.Builder()
        .client(client)
        .url(dohUrl.toHttpUrl())
        .build()

    override suspend fun resolveSystem(domain: String): List<String> = withContext(Dispatchers.IO) {
        try {
            InetAddress.getAllByName(domain).mapNotNull { it.hostAddress }
        } catch (ex: Exception) {
            emptyList()
        }
    }

    override suspend fun resolveDoh(domain: String): List<String> = withContext(Dispatchers.IO) {
        try {
            doh.lookup(domain).mapNotNull { it.hostAddress }
        } catch (ex: Exception) {
            emptyList()
        }
    }
}
