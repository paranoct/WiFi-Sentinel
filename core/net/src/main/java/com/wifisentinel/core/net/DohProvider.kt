package com.wifisentinel.core.net

data class DohProvider(
    val id: String,
    val title: String,
    val url: String
)

object DohProviders {
    val defaults = listOf(
        DohProvider("cloudflare", "Cloudflare", "https://cloudflare-dns.com/dns-query"),
        DohProvider("google", "Google", "https://dns.google/dns-query"),
        DohProvider("quad9", "Quad9", "https://dns.quad9.net/dns-query")
    )
}
