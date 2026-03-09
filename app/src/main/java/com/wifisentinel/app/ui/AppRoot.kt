package com.wifisentinel.app.ui

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.SystemClock
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wifisentinel.app.R
import com.wifisentinel.app.viewmodel.DashboardViewModel
import com.wifisentinel.app.viewmodel.NetworkDetailsViewModel
import com.wifisentinel.app.viewmodel.ReplayViewModel
import com.wifisentinel.app.viewmodel.SettingsViewModel
import com.wifisentinel.app.viewmodel.TimelineViewModel
import com.wifisentinel.app.viewmodel.TrustedNetworksViewModel
import com.wifisentinel.app.permissions.WifiPermissions
import com.wifisentinel.app.permissions.NotificationPermissions
import com.wifisentinel.feature.dashboard.DashboardScreen
import com.wifisentinel.feature.networkdetails.NetworkDetailsScreen
import com.wifisentinel.feature.settings.ReplayScreen
import com.wifisentinel.feature.settings.SettingsScreen
import com.wifisentinel.feature.timeline.TimelineScreen
import com.wifisentinel.feature.trusted.TrustedNetworksScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppRoot() {
    val kontekst = LocalContext.current
    val obyazatelnyeRazresheniya = remember { WifiPermissions.requiredPermissions() }
    var razresheniyaVydany by remember {
        mutableStateOf(WifiPermissions.hasRequiredPermissions(kontekst))
    }
    val razresheniyaUvedomleniy = remember { NotificationPermissions.requiredPermissions() }
    var uvedomleniyaRazresheny by remember {
        mutableStateOf(NotificationPermissions.hasRequiredPermissions(kontekst))
    }
    var zaprosUvedomleniyUzheByl by remember { mutableStateOf(false) }
    val launcherRazresheniy = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        razresheniyaVydany = WifiPermissions.hasRequiredPermissions(kontekst)
    }
    val launcherRazresheniyUvedomleniy = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        uvedomleniyaRazresheny = NotificationPermissions.hasRequiredPermissions(kontekst)
    }

    val navKontroller = rememberNavController()
    val zapisStekaNavigatsii by navKontroller.currentBackStackEntryAsState()
    val tekushiyMarshrut = zapisStekaNavigatsii?.destination?.route?.substringBefore("?")
    var vremyaPoslednegoKlikaMs by remember { mutableStateOf(0L) }

    val punktyNavigatsii = listOf(
        NavItem(AppRoutes.Dashboard, R.string.nav_dashboard, Icons.Filled.Home),
        NavItem(AppRoutes.Trusted, R.string.nav_trusted, Icons.Filled.Info),
        NavItem(AppRoutes.Timeline, R.string.nav_timeline, Icons.AutoMirrored.Filled.List),
        NavItem(AppRoutes.Settings, R.string.nav_settings, Icons.Filled.Settings)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                punktyNavigatsii.forEach { punkt ->
                    val vybran = tekushiyMarshrut == punkt.route
                    NavigationBarItem(
                        selected = vybran,
                        onClick = {
                            val seychas = SystemClock.elapsedRealtime()
                            if (seychas - vremyaPoslednegoKlikaMs < NAV_CLICK_DEBOUNCE_MS) return@NavigationBarItem
                            vremyaPoslednegoKlikaMs = seychas
                            navigateToTopLevelFresh(navKontroller, punkt.route)
                        },
                        icon = { androidx.compose.material3.Icon(punkt.icon, contentDescription = stringResource(punkt.labelResId)) },
                        label = { androidx.compose.material3.Text(stringResource(punkt.labelResId)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navKontroller,
            startDestination = AppRoutes.Dashboard,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppRoutes.Dashboard) {
                val viewModel: DashboardViewModel = hiltViewModel()
                val replayViewModel: ReplayViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val launcherImporta = rememberLauncherForActivityResult(
                    ActivityResultContracts.OpenDocument()
                ) { uriFayla ->
                    if (uriFayla != null) {
                        replayViewModel.importFromUri(uriFayla)
                    }
                }
                LaunchedEffect(viewModel) {
                    viewModel.reportEvents.collectLatest { sobytie ->
                        when (sobytie) {
                            is DashboardViewModel.ReportEvent.Share -> shareReports(kontekst, listOf(sobytie.uri))
                            is DashboardViewModel.ReportEvent.Message -> {
                                Toast.makeText(
                                    kontekst,
                                    kontekst.getString(sobytie.messageResId),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is DashboardViewModel.ReportEvent.OpenWifiSettings -> {
                                openWifiSettings(kontekst, sobytie.ssid, sobytie.action)
                            }
                            is DashboardViewModel.ReportEvent.Error -> {
                                Toast.makeText(
                                    kontekst,
                                    kontekst.getString(sobytie.messageResId),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                LaunchedEffect(replayViewModel) {
                    replayViewModel.events.collectLatest { sobytie ->
                        when (sobytie) {
                            is ReplayViewModel.ReplayEvent.Share -> shareReports(kontekst, listOf(sobytie.uri))
                            is ReplayViewModel.ReplayEvent.Message -> {
                                Toast.makeText(
                                    kontekst,
                                    kontekst.getString(sobytie.messageResId),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is ReplayViewModel.ReplayEvent.Error -> {
                                Toast.makeText(
                                    kontekst,
                                    kontekst.getString(sobytie.messageResId),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                LaunchedEffect(razresheniyaVydany) {
                    if (razresheniyaVydany) {
                        viewModel.refreshSnapshot()
                    }
                }
                LaunchedEffect(razresheniyaUvedomleniy) {
                    if (!zaprosUvedomleniyUzheByl && !uvedomleniyaRazresheny && razresheniyaUvedomleniy.isNotEmpty()) {
                        zaprosUvedomleniyUzheByl = true
                        launcherRazresheniyUvedomleniy.launch(razresheniyaUvedomleniy.toTypedArray())
                    }
                }
                DashboardScreen(
                    state = uiState,
                    onOpenDetails = { navKontroller.navigate(AppRoutes.NetworkDetails) },
                    onAddTrusted = {
                        navKontroller.navigate(AppRoutes.trustedRoute(openAddCurrent = true)) {
                            popUpTo(navKontroller.graph.findStartDestination().id) {
                                inclusive = false
                                saveState = false
                            }
                            restoreState = false
                            launchSingleTop = false
                        }
                    },
                    onToggleAutoJoinBlock = { blocked -> viewModel.setCurrentNetworkAutoJoinBlocked(blocked) },
                    onScanNow = { viewModel.scanNow() },
                    onShareReport = { viewModel.exportCurrentNetworkReport() },
                    onExitDemo = { viewModel.exitDemoMode() },
                    onLoadReplay = { launcherImporta.launch(arrayOf("application/json")) },
                    permissionsMissing = !razresheniyaVydany,
                    onRequestPermissions = { launcherRazresheniy.launch(obyazatelnyeRazresheniya.toTypedArray()) }
                )
            }
            composable(AppRoutes.NetworkDetails) {
                val viewModel: NetworkDetailsViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                NetworkDetailsScreen(state = uiState, onBack = { navKontroller.popBackStack() })
            }
            composable(
                route = AppRoutes.TrustedRoutePattern,
                arguments = listOf(
                    navArgument(AppRoutes.TrustedOpenAddCurrentArg) {
                        type = NavType.BoolType
                        defaultValue = false
                    }
                )
            ) { zapisSteka ->
                val viewModel: TrustedNetworksViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val nadoOtkrytDobavlenieTekushchey = zapisSteka.arguments
                    ?.getBoolean(AppRoutes.TrustedOpenAddCurrentArg)
                    ?: false
                LaunchedEffect(nadoOtkrytDobavlenieTekushchey) {
                    if (nadoOtkrytDobavlenieTekushchey) {
                        viewModel.requestAddCurrent()
                    }
                }
                TrustedNetworksScreen(
                    state = uiState,
                    onTabSelected = { viewModel.onTabSelected(it) },
                    onAddCurrent = { viewModel.requestAddCurrent() },
                    onAddFromScan = { viewModel.requestAddFromScan() },
                    onSelectScanCandidate = { viewModel.selectScanCandidate(it) },
                    onConfirmAdd = { candidate, category, meshMode ->
                        viewModel.confirmAdd(candidate, category, meshMode)
                    },
                    onDismissSheets = { viewModel.dismissSheets() },
                    onMoveProfile = { profileId, category -> viewModel.moveProfile(profileId, category) },
                    onAcceptFingerprint = { profileId -> viewModel.acceptFingerprint(profileId) },
                    onDeleteProfile = { profileId -> viewModel.deleteProfile(profileId) },
                    onUpdateProfile = { profile -> viewModel.updateProfile(profile) }
                )
            }
            composable(AppRoutes.Timeline) {
                val viewModel: TimelineViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(viewModel) {
                    viewModel.reportEvents.collectLatest { sobytie ->
                        when (sobytie) {
                            is TimelineViewModel.ReportEvent.Share -> {
                                shareReports(kontekst, sobytie.uris)
                            }
                            is TimelineViewModel.ReportEvent.Error -> {
                                Toast.makeText(
                                    kontekst,
                                    kontekst.getString(sobytie.messageResId),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                TimelineScreen(
                    state = uiState,
                    onSearchQueryChange = { viewModel.onSearchQueryChange(it) },
                    onToggleFilter = { viewModel.toggleFilter(it) },
                    onSelectNetwork = { viewModel.selectNetwork(it) },
                    onClearSelection = { viewModel.clearSelection() },
                    onDownloadReport = { viewModel.exportNetworkReport(it) },
                    onClearHistory = { viewModel.clearNetworkHistory(it) }
                )
            }
            composable(AppRoutes.Settings) {
                val viewModel: SettingsViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                SettingsScreen(
                    state = uiState,
                    onToggleNotifications = { enabled ->
                        if (enabled && !uvedomleniyaRazresheny && razresheniyaUvedomleniy.isNotEmpty()) {
                            launcherRazresheniyUvedomleniy.launch(razresheniyaUvedomleniy.toTypedArray())
                        }
                        viewModel.setNotificationsEnabled(enabled)
                    },
                    onToggleDnsCheck = { enabled -> viewModel.setDnsCheckEnabled(enabled) },
                    onOpenReplay = { navKontroller.navigate(AppRoutes.Replay) },
                    onThemeChange = { viewModel.setThemeMode(it) },
                    onToggleMaskSensitive = { enabled -> viewModel.setMaskSensitive(enabled) },
                    onToggleAutoDisconnect = { enabled -> viewModel.setAutoDisconnectEnabled(enabled) }
                )
            }
            composable(AppRoutes.Replay) {
                val viewModel: ReplayViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val launcherImporta = rememberLauncherForActivityResult(
                    ActivityResultContracts.OpenDocument()
                ) { uriFayla ->
                    if (uriFayla != null) {
                        viewModel.importFromUri(uriFayla)
                    }
                }
                LaunchedEffect(viewModel) {
                    viewModel.events.collectLatest { sobytie ->
                        when (sobytie) {
                            is ReplayViewModel.ReplayEvent.Share -> shareReports(kontekst, listOf(sobytie.uri))
                            is ReplayViewModel.ReplayEvent.Message -> {
                                Toast.makeText(
                                    kontekst,
                                    kontekst.getString(sobytie.messageResId),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is ReplayViewModel.ReplayEvent.Error -> {
                                Toast.makeText(
                                    kontekst,
                                    kontekst.getString(sobytie.messageResId),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                ReplayScreen(
                    state = uiState,
                    onToggleMaskSensitive = { enabled -> viewModel.setMaskSensitive(enabled) },
                    onLoadFile = { launcherImporta.launch(arrayOf("application/json")) },
                    onExitDemo = { viewModel.exitDemoMode() }
                )
            }
        }
    }
}

private data class NavItem(
    val route: String,
    val labelResId: Int,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private const val NAV_CLICK_DEBOUNCE_MS = 100L

private fun navigateToTopLevelFresh(
    navKontroller: NavHostController,
    marshrut: String
) {
    val startoviyMarshrut = navKontroller.graph.findStartDestination().route
    val nuzhenInclusivePop = marshrut == startoviyMarshrut
    navKontroller.navigate(marshrut) {
        popUpTo(navKontroller.graph.findStartDestination().id) {
            inclusive = nuzhenInclusivePop
            saveState = false
        }
        restoreState = false
        launchSingleTop = false
    }
}

private fun shareReports(kontekst: Context, uriSpisok: List<Uri>) {
    if (uriSpisok.isEmpty()) return
    val dannyeDlyaShara = ClipData.newUri(kontekst.contentResolver, "WiFi Sentinel Report", uriSpisok.first())
    uriSpisok.drop(1).forEach { uri ->
        dannyeDlyaShara.addItem(ClipData.Item(uri))
    }

    val intentSharenga = if (uriSpisok.size == 1) {
        Intent(Intent.ACTION_SEND).apply {
            val uri = uriSpisok.first()
            type = kontekst.contentResolver.getType(uri) ?: "application/octet-stream"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            clipData = dannyeDlyaShara
        }
    } else {
        Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            type = "*/*"
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(uriSpisok))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            clipData = dannyeDlyaShara
        }
    }
    kontekst.startActivity(Intent.createChooser(intentSharenga, kontekst.getString(R.string.share_report_title)))
}

private fun openWifiSettings(
    kontekst: Context,
    ssid: String?,
    deystvie: DashboardViewModel.ReportEvent.WifiSettingsAction
) {
    val intentNastroek = Intent(Settings.ACTION_WIFI_SETTINGS).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    kontekst.startActivity(intentNastroek)
    val soobshenie = when (deystvie) {
        DashboardViewModel.ReportEvent.WifiSettingsAction.FORGET_NETWORK -> {
            if (ssid.isNullOrBlank()) {
                kontekst.getString(R.string.toast_forget_open_settings)
            } else {
                kontekst.getString(R.string.toast_forget_open_settings_network, ssid)
            }
        }
        DashboardViewModel.ReportEvent.WifiSettingsAction.ENABLE_AUTOJOIN -> {
            if (ssid.isNullOrBlank()) {
                kontekst.getString(R.string.toast_autojoin_enable_open_settings)
            } else {
                kontekst.getString(R.string.toast_autojoin_enable_open_settings_network, ssid)
            }
        }
    }
    Toast.makeText(kontekst, soobshenie, Toast.LENGTH_LONG).show()
}
