package com.wifisentinel.feature.trusted

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wifisentinel.core.wifi.NetworkCategory
import com.wifisentinel.core.wifi.ScanNet
import com.wifisentinel.core.wifi.SecurityType
import com.wifisentinel.core.wifi.TrustedNetworkProfile
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.wifisentinel.feature.trusted.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrustedNetworksScreen(
    state: TrustedUiState,
    onTabSelected: (NetworkCategory) -> Unit,
    onAddCurrent: () -> Unit,
    onAddFromScan: () -> Unit,
    onSelectScanCandidate: (ScanNet) -> Unit,
    onConfirmAdd: (TrustedAddCandidate, NetworkCategory, Boolean) -> Unit,
    onDismissSheets: () -> Unit,
    onMoveProfile: (String, NetworkCategory) -> Unit,
    onAcceptFingerprint: (String) -> Unit,
    onDeleteProfile: (String) -> Unit,
    onUpdateProfile: (TrustedNetworkProfile) -> Unit
) {
    var editingProfile by remember { mutableStateOf<TrustedNetworkProfile?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TabRow(selectedTabIndex = state.selectedCategory.ordinal) {
            NetworkCategory.values().forEach { category ->
                Tab(
                    selected = state.selectedCategory == category,
                    onClick = { onTabSelected(category) },
                    text = { Text(text = categoryLabel(category)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onAddCurrent, modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.trusted_add_current_network))
            }
            Button(
                onClick = onAddFromScan,
                enabled = !state.isScanning,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(
                        if (state.isScanning) R.string.trusted_scanning else R.string.trusted_add_from_scan
                    )
                )
            }
        }

        if (state.errorMessageResId != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(state.errorMessageResId), color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(R.string.trusted_title), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        if (state.profiles.isEmpty()) {
            Text(text = emptyStateText(state.selectedCategory))
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(state.profiles, key = { it.id }) { profile ->
                    TrustedProfileCard(
                        profile = profile,
                        onMove = onMoveProfile,
                        onAcceptFingerprint = onAcceptFingerprint,
                        onDelete = onDeleteProfile,
                        onEdit = { editingProfile = it }
                    )
                }
            }
        }
    }

    val pending = state.pendingAdd
    if (state.scanResults.isNotEmpty() && pending == null) {
        ModalBottomSheet(onDismissRequest = onDismissSheets) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = stringResource(R.string.trusted_select_network), style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(modifier = Modifier.heightIn(max = 360.dp)) {
                    items(state.scanResults) { net ->
                        ScanResultRow(net = net, onSelect = onSelectScanCandidate)
                    }
                }
            }
        }
    }

    if (pending != null) {
        var sheetCategory by remember(pending) { mutableStateOf(state.selectedCategory) }
        var meshMode by remember(pending) { mutableStateOf(false) }
        ModalBottomSheet(onDismissRequest = onDismissSheets) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = stringResource(R.string.trusted_add_network), style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                val ssidValue = pending.ssid ?: stringResource(R.string.trusted_hidden_ssid)
                val bssidValue = pending.bssid ?: stringResource(R.string.trusted_value_unknown)
                Text(text = stringResource(R.string.trusted_label_ssid, ssidValue))
                Text(text = stringResource(R.string.trusted_label_bssid, bssidValue))
                Text(text = stringResource(R.string.trusted_label_security, securityLabel(pending.securityType)))
                Text(text = stringResource(R.string.trusted_label_band, bandLabel(pending.frequencyMhz)))

                Spacer(modifier = Modifier.height(12.dp))
                Text(text = stringResource(R.string.trusted_label_category))
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    NetworkCategory.values().forEach { category ->
                        val selected = sheetCategory == category
                        OutlinedButton(onClick = { sheetCategory = category }, enabled = !selected) {
                            Text(text = categoryLabel(category))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = meshMode, onCheckedChange = { meshMode = it })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(R.string.trusted_label_mesh))
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onConfirmAdd(pending, sheetCategory, meshMode) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.trusted_add_button))
                }
            }
        }
    }

    if (editingProfile != null) {
        val profile = editingProfile ?: return
        var displayName by remember(profile.id) { mutableStateOf(profile.displayName) }
        var meshMode by remember(profile.id) { mutableStateOf(profile.meshMode) }
        ModalBottomSheet(onDismissRequest = { editingProfile = null }) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = stringResource(R.string.trusted_edit_title), style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = displayName,
                    onValueChange = { displayName = it },
                    label = { Text(text = stringResource(R.string.trusted_label_name)) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = meshMode, onCheckedChange = { meshMode = it })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = stringResource(R.string.trusted_chip_mesh))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        onUpdateProfile(profile.copy(displayName = displayName, meshMode = meshMode))
                        editingProfile = null
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.trusted_save_button))
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TrustedProfileCard(
    profile: TrustedNetworkProfile,
    onMove: (String, NetworkCategory) -> Unit,
    onAcceptFingerprint: (String) -> Unit,
    onDelete: (String) -> Unit,
    onEdit: (TrustedNetworkProfile) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    val formatter = remember { SimpleDateFormat("dd MMM HH:mm", Locale.getDefault()) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.fillMaxWidth(0.85f)) {
                    Text(text = profile.displayName, style = MaterialTheme.typography.titleSmall)
                    val securityList = profile.expectedSecurity
                        .map { securityLabel(it) }
                        .ifEmpty { listOf(stringResource(R.string.trusted_security_unknown)) }
                        .joinToString()
                    Text(
                        text = stringResource(
                            R.string.trusted_profile_subtitle,
                            securityList,
                            formatter.format(Date(profile.lastSeenMs))
                        )
                    )
                }
                IconButton(onClick = { menuExpanded = true }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = stringResource(R.string.trusted_menu)
                    )
                }
                DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.trusted_menu_edit)) },
                        onClick = {
                            menuExpanded = false
                            onEdit(profile)
                        }
                    )
                    NetworkCategory.values().forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(R.string.trusted_menu_move_to, categoryLabel(category)))
                            },
                            onClick = {
                                menuExpanded = false
                                onMove(profile.id, category)
                            },
                            enabled = profile.category != category
                        )
                    }
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.trusted_menu_update_fingerprint)) },
                        onClick = {
                            menuExpanded = false
                            onAcceptFingerprint(profile.id)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.trusted_menu_delete)) },
                        onClick = {
                            menuExpanded = false
                            onDelete(profile.id)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(
                    R.string.trusted_label_ssid,
                    profile.ssid ?: stringResource(R.string.trusted_hidden_ssid)
                )
            )
            Text(text = stringResource(R.string.trusted_bssid_count, profile.allowedBssids.size))

            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (profile.meshMode) {
                    AssistChip(onClick = {}, label = { Text(text = stringResource(R.string.trusted_chip_mesh)) })
                }
                if (profile.pinnedDns.isNotEmpty()) {
                    AssistChip(onClick = {}, label = { Text(text = stringResource(R.string.trusted_chip_dns_pinned)) })
                }
            }
        }
    }
}

@Composable
private fun ScanResultRow(net: ScanNet, onSelect: (ScanNet) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect(net) }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = net.ssid ?: stringResource(R.string.trusted_hidden_network),
                style = MaterialTheme.typography.titleSmall
            )
            Text(text = stringResource(R.string.trusted_scan_security, securityLabel(net.securityType)))
        }
        Text(text = signalLabel(net.rssiDbm))
    }
}

@Composable
private fun categoryLabel(category: NetworkCategory): String {
    return when (category) {
        NetworkCategory.HOME -> stringResource(R.string.trusted_category_home)
        NetworkCategory.WORK -> stringResource(R.string.trusted_category_work)
        NetworkCategory.PUBLIC -> stringResource(R.string.trusted_category_public)
    }
}

@Composable
private fun emptyStateText(category: NetworkCategory): String {
    return when (category) {
        NetworkCategory.HOME -> stringResource(R.string.trusted_empty_home)
        NetworkCategory.WORK -> stringResource(R.string.trusted_empty_work)
        NetworkCategory.PUBLIC -> stringResource(R.string.trusted_empty_public)
    }
}

@Composable
private fun securityLabel(type: SecurityType): String {
    return when (type) {
        SecurityType.OPEN -> stringResource(R.string.trusted_security_open)
        SecurityType.WEP -> stringResource(R.string.trusted_security_wep)
        SecurityType.WPA2 -> stringResource(R.string.trusted_security_wpa2)
        SecurityType.WPA3 -> stringResource(R.string.trusted_security_wpa3)
        SecurityType.WPA2_WPA3 -> stringResource(R.string.trusted_security_wpa2_wpa3)
        SecurityType.UNKNOWN -> stringResource(R.string.trusted_security_unknown)
    }
}

@Composable
private fun bandLabel(frequencyMhz: Int?): String {
    return when {
        frequencyMhz == null -> stringResource(R.string.trusted_band_unknown)
        frequencyMhz < 3000 -> stringResource(R.string.trusted_band_2g4)
        frequencyMhz < 6000 -> stringResource(R.string.trusted_band_5g)
        else -> stringResource(R.string.trusted_band_6g)
    }
}

@Composable
private fun signalLabel(rssiDbm: Int?): String {
    if (rssiDbm == null) return stringResource(R.string.trusted_value_unknown)
    val percent = ((rssiDbm + 100) * 2).coerceIn(0, 100)
    return stringResource(R.string.trusted_signal_percent, percent)
}
