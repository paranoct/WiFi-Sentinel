package com.wifisentinel.app.net

import com.wifisentinel.core.net.CaptivePortalCheck
import com.wifisentinel.core.net.CaptivePortalProbe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkHttpCaptivePortalProbe @Inject constructor() : CaptivePortalProbe {
    private val client = OkHttpClient.Builder()
        .followRedirects(false)
        .followSslRedirects(false)
        .build()

    override suspend fun check(): CaptivePortalCheck? = withContext(Dispatchers.IO) {
        val chain = mutableListOf<String>()
        var usedHttp = false
        var hasPunycode = false

        var url = TEST_URL
        repeat(MAX_REDIRECTS) {
            val request = Request.Builder().url(url).get().build()
            try {
                client.newCall(request).execute().use { response ->
                    val currentUrl = response.request.url
                    val host = currentUrl.host
                    if (host.isNotBlank()) {
                        chain.add(host)
                        if (host.contains("xn--", ignoreCase = true)) {
                            hasPunycode = true
                        }
                    }
                    if (currentUrl.scheme == "http") {
                        usedHttp = true
                    }

                    val location = response.header("Location")
                    if (!response.isRedirect || location.isNullOrBlank()) {
                        return@withContext chain.takeIf { it.isNotEmpty() }?.let {
                            CaptivePortalCheck(it.distinct(), usedHttp, hasPunycode)
                        }
                    }
                    val next = location.toHttpUrlOrNull() ?: return@withContext chain.takeIf { it.isNotEmpty() }?.let {
                        CaptivePortalCheck(it.distinct(), usedHttp, hasPunycode)
                    }
                    if (next.scheme == "http") {
                        usedHttp = true
                    }
                    url = next.toString()
                }
            } catch (_: Exception) {
                return@withContext null
            }
        }

        chain.takeIf { it.isNotEmpty() }?.let { CaptivePortalCheck(it.distinct(), usedHttp, hasPunycode) }
    }

    private companion object {
        const val MAX_REDIRECTS = 5
        const val TEST_URL = "https://connectivitycheck.gstatic.com/generate_204"
    }
}
