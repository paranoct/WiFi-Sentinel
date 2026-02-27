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
    val context = LocalContext.current
    val requiredPermissions = remember { WifiPermissions.requiredPermissions() }
    var permissionsGranted by remember {
        mutableStateOf(WifiPermissions.hasRequiredPermissions(context))
    }
    val notificationPermissions = remember { NotificationPermissions.requiredPermissions() }
    var notificationsGranted by remember {
        mutableStateOf(NotificationPermissions.hasRequiredPermissions(context))
    }
    var notificationRequested by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        permissionsGranted = WifiPermissions.hasRequiredPermissions(context)
    }
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        notificationsGranted = NotificationPermissions.hasRequiredPermissions(context)
    }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("?")
    var lastNavClickMs by remember { mutableStateOf(0L) }

    val navItems = listOf(
        NavItem(AppRoutes.Dashboard, R.string.nav_dashboard, Icons.Filled.Home),
        NavItem(AppRoutes.Trusted, R.string.nav_trusted, Icons.Filled.Info),
        NavItem(AppRoutes.Timeline, R.string.nav_timeline, Icons.AutoMirrored.Filled.List),
        NavItem(AppRoutes.Settings, R.string.nav_settings, Icons.Filled.Settings)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                navItems.forEach { item ->
                    val selected = currentRoute == item.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            val now = SystemClock.elapsedRealtime()
                            if (now - lastNavClickMs < NAV_CLICK_DEBOUNCE_MS) return@NavigationBarItem
                            lastNavClickMs = now
                            navigateToTopLevelFresh(navController, item.route)
                        },
                        icon = { androidx.compose.material3.Icon(item.icon, contentDescription = stringResource(item.labelResId)) },
                        label = { androidx.compose.material3.Text(stringResource(item.labelResId)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.Dashboard,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppRoutes.Dashboard) {
                val viewModel: DashboardViewModel = hiltViewModel()
                val replayViewModel: ReplayViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val importLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.OpenDocument()
                ) { uri ->
                    if (uri != null) {
                        replayViewModel.importFromUri(uri)
                    }
                }
                LaunchedEffect(viewModel) {
                    viewModel.reportEvents.collectLatest { event ->
                        when (event) {
                            is DashboardViewModel.ReportEvent.Share -> shareReports(context, listOf(event.uri))
                            is DashboardViewModel.ReportEvent.Message -> {
                                Toast.makeText(
                                    context,
                                    context.getString(event.messageResId),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is DashboardViewModel.ReportEvent.OpenWifiSettings -> {
                                openWifiSettings(context, event.ssid, event.action)
                            }
                            is DashboardViewModel.ReportEvent.Error -> {
                                Toast.makeText(
                                    context,
                                    context.getString(event.messageResId),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                LaunchedEffect(replayViewModel) {
                    replayViewModel.events.collectLatest { event ->
                        when (event) {
                            is ReplayViewModel.ReplayEvent.Share -> shareReports(context, listOf(event.uri))
                            is ReplayViewModel.ReplayEvent.Message -> {
                                Toast.makeText(
                                    context,
                                    context.getString(event.messageResId),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is ReplayViewModel.ReplayEvent.Error -> {
                                Toast.makeText(
                                    context,
                                    context.getString(event.messageResId),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                LaunchedEffect(permissionsGranted) {
                    if (permissionsGranted) {
                        viewModel.refreshSnapshot()
                    }
                }
                LaunchedEffect(notificationPermissions) {
                    if (!notificationRequested && !notificationsGranted && notificationPermissions.isNotEmpty()) {
                        notificationRequested = true
                        notificationPermissionLauncher.launch(notificationPermissions.toTypedArray())
                    }
                }
                DashboardScreen(
                    state = uiState,
                    onOpenDetails = { navController.navigate(AppRoutes.NetworkDetails) },
                    onAddTrusted = {
                        navController.navigate(AppRoutes.trustedRoute(openAddCurrent = true)) {
                            popUpTo(navController.graph.findStartDestination().id) {
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
                    onLoadReplay = { importLauncher.launch(arrayOf("application/json")) },
                    permissionsMissing = !permissionsGranted,
                    onRequestPermissions = { permissionLauncher.launch(requiredPermissions.toTypedArray()) }
                )
            }
            composable(AppRoutes.NetworkDetails) {
                val viewModel: NetworkDetailsViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                NetworkDetailsScreen(state = uiState, onBack = { navController.popBackStack() })
            }
            composable(
                route = AppRoutes.TrustedRoutePattern,
                arguments = listOf(
                    navArgument(AppRoutes.TrustedOpenAddCurrentArg) {
                        type = NavType.BoolType
                        defaultValue = false
                    }
                )
            ) { backStackEntry ->
                val viewModel: TrustedNetworksViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val openAddCurrent = backStackEntry.arguments
                    ?.getBoolean(AppRoutes.TrustedOpenAddCurrentArg)
                    ?: false
                LaunchedEffect(openAddCurrent) {
                    if (openAddCurrent) {
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
                    viewModel.reportEvents.collectLatest { event ->
                        when (event) {
                            is TimelineViewModel.ReportEvent.Share -> {
                                shareReports(context, event.uris)
                            }
                            is TimelineViewModel.ReportEvent.Error -> {
                                Toast.makeText(
                                    context,
                                    context.getString(event.messageResId),
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
                        if (enabled && !notificationsGranted && notificationPermissions.isNotEmpty()) {
                            notificationPermissionLauncher.launch(notificationPermissions.toTypedArray())
                        }
                        viewModel.setNotificationsEnabled(enabled)
                    },
                    onToggleDnsCheck = { enabled -> viewModel.setDnsCheckEnabled(enabled) },
                    onOpenReplay = { navController.navigate(AppRoutes.Replay) },
                    onThemeChange = { viewModel.setThemeMode(it) },
                    onToggleMaskSensitive = { enabled -> viewModel.setMaskSensitive(enabled) },
                    onToggleAutoDisconnect = { enabled -> viewModel.setAutoDisconnectEnabled(enabled) }
                )
            }
            composable(AppRoutes.Replay) {
                val viewModel: ReplayViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val importLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.OpenDocument()
                ) { uri ->
                    if (uri != null) {
                        viewModel.importFromUri(uri)
                    }
                }
                LaunchedEffect(viewModel) {
                    viewModel.events.collectLatest { event ->
                        when (event) {
                            is ReplayViewModel.ReplayEvent.Share -> shareReports(context, listOf(event.uri))
                            is ReplayViewModel.ReplayEvent.Message -> {
                                Toast.makeText(
                                    context,
                                    context.getString(event.messageResId),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is ReplayViewModel.ReplayEvent.Error -> {
                                Toast.makeText(
                                    context,
                                    context.getString(event.messageResId),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                ReplayScreen(
                    state = uiState,
                    onToggleMaskSensitive = { enabled -> viewModel.setMaskSensitive(enabled) },
                    onLoadFile = { importLauncher.launch(arrayOf("application/json")) },
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
    navController: NavHostController,
    route: String
) {
    val startRoute = navController.graph.findStartDestination().route
    val inclusivePop = route == startRoute
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            inclusive = inclusivePop
            saveState = false
        }
        restoreState = false
        launchSingleTop = false
    }
}

private fun shareReports(context: Context, uris: List<Uri>) {
    if (uris.isEmpty()) return
    val shareClipData = ClipData.newUri(context.contentResolver, "WiFi Sentinel Report", uris.first())
    uris.drop(1).forEach { uri ->
        shareClipData.addItem(ClipData.Item(uri))
    }

    val intent = if (uris.size == 1) {
        Intent(Intent.ACTION_SEND).apply {
            val uri = uris.first()
            type = context.contentResolver.getType(uri) ?: "application/octet-stream"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            clipData = shareClipData
        }
    } else {
        Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            type = "*/*"
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, ArrayList(uris))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            clipData = shareClipData
        }
    }
    context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_report_title)))
}

private fun openWifiSettings(
    context: Context,
    ssid: String?,
    action: DashboardViewModel.ReportEvent.WifiSettingsAction
) {
    val settingsIntent = Intent(Settings.ACTION_WIFI_SETTINGS).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(settingsIntent)
    val message = when (action) {
        DashboardViewModel.ReportEvent.WifiSettingsAction.FORGET_NETWORK -> {
            if (ssid.isNullOrBlank()) {
                context.getString(R.string.toast_forget_open_settings)
            } else {
                context.getString(R.string.toast_forget_open_settings_network, ssid)
            }
        }
        DashboardViewModel.ReportEvent.WifiSettingsAction.ENABLE_AUTOJOIN -> {
            if (ssid.isNullOrBlank()) {
                context.getString(R.string.toast_autojoin_enable_open_settings)
            } else {
                context.getString(R.string.toast_autojoin_enable_open_settings_network, ssid)
            }
        }
    }
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}
