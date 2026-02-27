package com.wifisentinel.app.replay

import com.wifisentinel.core.net.CaptivePortalCheck
import com.wifisentinel.core.wifi.Band
import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.NetworkSnapshot
import com.wifisentinel.core.wifi.ScanNet
import com.wifisentinel.core.wifi.SecurityType
import com.wifisentinel.core.wifi.TrustedNetworkProfile
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

object ReplayPayloadCodec {
    fun buildPayloadJson(payload: ReplayPayload, maskSensitive: Boolean): JSONObject {
        val root = JSONObject()
        root.put("version", 1)
        root.put("snapshot", buildSnapshotJson(payload.snapshot, maskSensitive))
        root.put("scanResults", buildScanResultsJson(payload.scanResults, maskSensitive))
        payload.dnsCheck?.let { root.put("dnsCheck", buildDnsCheckJson(it)) }
        payload.portalCheck?.let { root.put("portalCheck", buildPortalCheckJson(it)) }
        if (payload.trustedProfiles.isNotEmpty()) {
            root.put("trustedProfiles", buildTrustedProfilesJson(payload.trustedProfiles, maskSensitive))
        }
        return root
    }

    fun parsePayload(root: JSONObject): ReplayPayload {
        val snapshot = parseSnapshot(root.getJSONObject("snapshot"))
        val scanResults = parseScanResults(root.optJSONArray("scanResults") ?: JSONArray())
        val dnsCheck = root.optJSONObject("dnsCheck")?.let { parseDnsCheck(it) }
        val portalCheck = root.optJSONObject("portalCheck")?.let { parsePortalCheck(it) }
        val trustedProfiles = root.optJSONArray("trustedProfiles")?.let { parseTrustedProfiles(it) }.orEmpty()
        return ReplayPayload(
            snapshot = snapshot,
            scanResults = scanResults,
            dnsCheck = dnsCheck,
            portalCheck = portalCheck,
            trustedProfiles = trustedProfiles
        )
    }

    private fun buildSnapshotJson(snapshot: NetworkSnapshot, maskSensitive: Boolean): JSONObject {
        val json = JSONObject()
        json.put("id", snapshot.id)
        json.put("timestampMs", snapshot.timestampMs)
        json.putNullable("ssid", maskValue(snapshot.ssid, maskSensitive))
        json.putNullable("bssid", maskValue(snapshot.bssid, maskSensitive))
        json.put("securityType", snapshot.securityType.name)
        json.putNullable("frequencyMhz", snapshot.frequencyMhz)
        json.putNullable("rssiDbm", snapshot.rssiDbm)
        json.putNullable("ipV4", snapshot.ipV4)
        json.putNullable("gatewayV4", snapshot.gatewayV4)
        json.put("dnsServers", JSONArray(snapshot.dnsServers))
        json.put("captivePortal", snapshot.captivePortal)
        json.put("networkIdHint", snapshot.networkIdHint)
        return json
    }

    private fun buildScanResultsJson(scanResults: List<ScanNet>, maskSensitive: Boolean): JSONArray {
        val array = JSONArray()
        scanResults.forEach { net ->
            val json = JSONObject()
            json.putNullable("ssid", maskValue(net.ssid, maskSensitive))
            json.putNullable("bssid", maskValue(net.bssid, maskSensitive))
            json.putNullable("frequencyMhz", net.frequencyMhz)
            json.putNullable("rssiDbm", net.rssiDbm)
            json.put("securityType", net.securityType.name)
            json.putNullable("channelWidth", net.channelWidth)
            json.putNullable("wifiStandard", net.wifiStandard)
            array.put(json)
        }
        return array
    }

    private fun buildDnsCheckJson(payload: DnsCheckPayload): JSONObject {
        val json = JSONObject()
        val domainsJson = JSONObject()
        payload.domains.forEach { (domain, data) ->
            val item = JSONObject()
            item.put("systemAnswers", JSONArray(data.systemAnswers))
            item.put("dohAnswers", JSONArray(data.dohAnswers))
            domainsJson.put(domain, item)
        }
        json.put("domains", domainsJson)
        return json
    }

    private fun buildPortalCheckJson(payload: CaptivePortalCheck): JSONObject {
        val json = JSONObject()
        json.put("redirectDomains", JSONArray(payload.redirectDomains))
        json.put("usedHttp", payload.usedHttp)
        json.put("hasPunycode", payload.hasPunycode)
        return json
    }

    private fun buildTrustedProfilesJson(
        profiles: List<TrustedNetworkProfile>,
        maskSensitive: Boolean
    ): JSONArray {
        val array = JSONArray()
        profiles.forEach { profile ->
            val json = JSONObject()
            json.put("id", profile.id)
            json.put("displayName", profile.displayName)
            json.putNullable("ssid", maskValue(profile.ssid, maskSensitive))
            json.put("category", profile.category.name)
            json.put("meshMode", profile.meshMode)
            json.put("allowedBssids", JSONArray(profile.allowedBssids.map { maskValue(it, maskSensitive) }))
            json.put("expectedSecurity", JSONArray(profile.expectedSecurity.map { it.name }))
            json.put("expectedFreqBands", JSONArray(profile.expectedFreqBands.map { it.name }))
            json.put("pinnedDns", JSONArray(profile.pinnedDns))
            json.put("createdAtMs", profile.createdAtMs)
            json.put("lastSeenMs", profile.lastSeenMs)
            json.put("maxNewBssidPerDay", profile.maxNewBssidPerDay)
            json.put("bssidLearning", profile.bssidLearning)
            array.put(json)
        }
        return array
    }

    private fun parseSnapshot(json: JSONObject): NetworkSnapshot {
        return NetworkSnapshot(
            id = json.optString("id").ifBlank { UUID.randomUUID().toString() },
            timestampMs = json.optLong("timestampMs", System.currentTimeMillis()),
            ssid = json.optNullableString("ssid"),
            bssid = json.optNullableString("bssid"),
            securityType = parseSecurityType(json.optString("securityType")),
            frequencyMhz = json.optNullableInt("frequencyMhz"),
            rssiDbm = json.optNullableInt("rssiDbm"),
            ipV4 = json.optNullableString("ipV4"),
            gatewayV4 = json.optNullableString("gatewayV4"),
            dnsServers = json.optJSONArray("dnsServers")?.toStringList().orEmpty(),
            captivePortal = json.optBoolean("captivePortal", false),
            networkIdHint = json.optString("networkIdHint", "")
        )
    }

    private fun parseScanResults(array: JSONArray): List<ScanNet> {
        val results = mutableListOf<ScanNet>()
        for (i in 0 until array.length()) {
            val json = array.optJSONObject(i) ?: continue
            results.add(
                ScanNet(
                    ssid = json.optNullableString("ssid"),
                    bssid = json.optNullableString("bssid"),
                    frequencyMhz = json.optNullableInt("frequencyMhz"),
                    rssiDbm = json.optNullableInt("rssiDbm"),
                    securityType = parseSecurityType(json.optString("securityType")),
                    channelWidth = json.optNullableInt("channelWidth"),
                    wifiStandard = json.optNullableInt("wifiStandard")
                )
            )
        }
        return results
    }

    private fun parseDnsCheck(json: JSONObject): DnsCheckPayload {
        val domainsJson = json.optJSONObject("domains") ?: JSONObject()
        val map = LinkedHashMap<String, DnsDomainPayload>()
        domainsJson.keys().forEach { domain ->
            val item = domainsJson.optJSONObject(domain) ?: return@forEach
            val system = item.optJSONArray("systemAnswers")?.toStringList().orEmpty()
            val doh = item.optJSONArray("dohAnswers")?.toStringList().orEmpty()
            map[domain] = DnsDomainPayload(system, doh)
        }
        return DnsCheckPayload(map)
    }

    private fun parsePortalCheck(json: JSONObject): CaptivePortalCheck {
        val redirects = json.optJSONArray("redirectDomains")?.toStringList().orEmpty()
        val usedHttp = json.optBoolean("usedHttp", false)
        val hasPunycode = json.optBoolean("hasPunycode", false)
        return CaptivePortalCheck(
            redirectDomains = redirects,
            usedHttp = usedHttp,
            hasPunycode = hasPunycode
        )
    }

    private fun parseTrustedProfiles(array: JSONArray): List<TrustedNetworkProfile> {
        val profiles = mutableListOf<TrustedNetworkProfile>()
        for (i in 0 until array.length()) {
            val json = array.optJSONObject(i) ?: continue
            profiles.add(
                TrustedNetworkProfile(
                    id = json.optString("id", UUID.randomUUID().toString()),
                    displayName = json.optString("displayName"),
                    ssid = json.optNullableString("ssid"),
                    category = parseCategory(json.optString("category")),
                    meshMode = json.optBoolean("meshMode", false),
                    allowedBssids = json.optJSONArray("allowedBssids")?.toStringList().orEmpty().toSet(),
                    expectedSecurity = json.optJSONArray("expectedSecurity")?.toStringList().orEmpty()
                        .map { parseSecurityType(it) }.toSet(),
                    expectedFreqBands = json.optJSONArray("expectedFreqBands")?.toStringList().orEmpty()
                        .mapNotNull { parseBand(it) }.toSet(),
                    pinnedDns = json.optJSONArray("pinnedDns")?.toStringList().orEmpty(),
                    createdAtMs = json.optLong("createdAtMs", System.currentTimeMillis()),
                    lastSeenMs = json.optLong("lastSeenMs", System.currentTimeMillis()),
                    maxNewBssidPerDay = json.optInt("maxNewBssidPerDay", 3),
                    bssidLearning = json.optBoolean("bssidLearning", false)
                )
            )
        }
        return profiles
    }

    private fun parseSecurityType(value: String): SecurityType {
        return runCatching { SecurityType.valueOf(value) }.getOrDefault(SecurityType.UNKNOWN)
    }

    private fun parseCategory(value: String): NetworkCategory {
        return runCatching { NetworkCategory.valueOf(value) }.getOrDefault(NetworkCategory.PUBLIC)
    }

    private fun parseBand(value: String): Band? {
        return runCatching { Band.valueOf(value) }.getOrNull()
    }

    private fun maskValue(value: String?, enabled: Boolean): String? {
        if (!enabled || value.isNullOrBlank()) return value
        val trimmed = value.trim()
        if (trimmed.length <= 3) return "***"
        return trimmed.take(3) + "***"
    }

    private fun JSONObject.putNullable(key: String, value: Any?) {
        if (value == null) {
            put(key, JSONObject.NULL)
        } else {
            put(key, value)
        }
    }

    private fun JSONObject.optNullableString(key: String): String? {
        return if (isNull(key)) null else optString(key)
    }

    private fun JSONObject.optNullableInt(key: String): Int? {
        return if (isNull(key)) null else optInt(key)
    }

    private fun JSONArray.toStringList(): List<String> {
        val list = mutableListOf<String>()
        for (i in 0 until length()) {
            val value = optString(i, "")
            if (value.isNotBlank()) list.add(value)
        }
        return list
    }
}
