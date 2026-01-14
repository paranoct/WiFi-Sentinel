package com.wifisentinel.core.net

interface DnsProbe {
    suspend fun resolveSystem(domain: String): List<String>
    suspend fun resolveDoh(domain: String): List<String>
}
