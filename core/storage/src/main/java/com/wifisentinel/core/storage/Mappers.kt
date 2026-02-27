package com.wifisentinel.core.storage

import com.wifisentinel.core.detectors.Finding
import com.wifisentinel.core.storage.db.entity.EventEntity
import com.wifisentinel.core.storage.db.entity.FindingEntity
import com.wifisentinel.core.storage.db.entity.NetworkSnapshotEntity
import com.wifisentinel.core.storage.db.entity.TrustedNetworkProfileEntity
import com.wifisentinel.core.wifi.NetworkSnapshot
import com.wifisentinel.core.wifi.TrustedNetworkProfile

internal fun NetworkSnapshotEntity.toDomain(): NetworkSnapshot = NetworkSnapshot(
    id = id,
    timestampMs = timestampMs,
    ssid = ssid,
    bssid = bssid,
    securityType = securityType,
    frequencyMhz = frequencyMhz,
    rssiDbm = rssiDbm,
    ipV4 = ipV4,
    gatewayV4 = gatewayV4,
    dnsServers = dnsServers,
    captivePortal = captivePortal,
    networkIdHint = networkIdHint
)

internal fun NetworkSnapshot.toEntity(): NetworkSnapshotEntity = NetworkSnapshotEntity(
    id = id,
    timestampMs = timestampMs,
    ssid = ssid,
    bssid = bssid,
    securityType = securityType,
    frequencyMhz = frequencyMhz,
    rssiDbm = rssiDbm,
    ipV4 = ipV4,
    gatewayV4 = gatewayV4,
    dnsServers = dnsServers,
    captivePortal = captivePortal,
    networkIdHint = networkIdHint
)

internal fun FindingEntity.toDomain(): Finding = Finding(
    id = id,
    snapshotId = snapshotId,
    detectorId = detectorId,
    severity = severity,
    scoreDelta = scoreDelta,
    title = title,
    explanation = explanation,
    evidence = evidence,
    actions = actions,
    dedupKey = dedupKey
)

internal fun Finding.toEntity(timestampMs: Long): FindingEntity = FindingEntity(
    id = id,
    snapshotId = snapshotId,
    timestampMs = timestampMs,
    detectorId = detectorId,
    severity = severity,
    scoreDelta = scoreDelta,
    title = title,
    explanation = explanation,
    evidence = evidence,
    actions = actions,
    dedupKey = dedupKey
)

internal fun TrustedNetworkProfileEntity.toDomain(): TrustedNetworkProfile = TrustedNetworkProfile(
    id = id,
    displayName = displayName,
    ssid = ssid,
    category = category,
    meshMode = meshMode,
    allowedBssids = allowedBssids,
    expectedSecurity = expectedSecurity,
    expectedFreqBands = expectedFreqBands,
    pinnedDns = pinnedDns,
    createdAtMs = createdAtMs,
    lastSeenMs = lastSeenMs,
    maxNewBssidPerDay = maxNewBssidPerDay,
    bssidLearning = bssidLearning
)

internal fun TrustedNetworkProfile.toEntity(): TrustedNetworkProfileEntity = TrustedNetworkProfileEntity(
    id = id,
    displayName = displayName,
    ssid = ssid,
    category = category,
    meshMode = meshMode,
    allowedBssids = allowedBssids,
    expectedSecurity = expectedSecurity,
    expectedFreqBands = expectedFreqBands,
    pinnedDns = pinnedDns,
    createdAtMs = createdAtMs,
    lastSeenMs = lastSeenMs,
    maxNewBssidPerDay = maxNewBssidPerDay,
    bssidLearning = bssidLearning
)

internal fun EventEntity.toDomain(): NetworkEvent = NetworkEvent(
    id = id,
    timestampMs = timestampMs,
    title = title,
    detail = detail,
    severity = severity,
    snapshotId = snapshotId
)

internal fun NetworkEvent.toEntity(): EventEntity = EventEntity(
    id = id,
    timestampMs = timestampMs,
    title = title,
    detail = detail,
    severity = severity,
    snapshotId = snapshotId
)
