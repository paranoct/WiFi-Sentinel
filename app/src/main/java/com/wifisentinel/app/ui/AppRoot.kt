package com.wifisentinel.app.ui

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
    val currentRoute = navBackStackEntry?.destination?.route

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
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        },
                        icon = { androidx.compose.material3.Icon(item.icon, contentDescription = stringResource(item.labelResId)) },
                        label = {
                            val fontSize = if (item.route == AppRoutes.Trusted) 10.sp else 12.sp
                            androidx.compose.material3.Text(stringResource(item.labelResId), fontSize = fontSize)
                        }
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
                LaunchedEffect(Unit) {
                    viewModel.refreshSnapshot()
                }
                LaunchedEffect(viewModel) {
                    viewModel.reportEvents.collectLatest { event ->
                        when (event) {
                            is DashboardViewModel.ReportEvent.Share -> shareReport(context, event.uri)
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
                            is ReplayViewModel.ReplayEvent.Share -> shareReport(context, event.uri)
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
                        viewModel.addCurrentToTrusted()
                        navController.navigate(AppRoutes.Trusted)
                    },
                    onScanNow = { viewModel.scanNow() },
                    onShareReport = { viewModel.exportReport() },
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
            composable(AppRoutes.Trusted) {
                val viewModel: TrustedNetworksViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
                            is TimelineViewModel.ReportEvent.Share -> shareReport(context, event.uri)
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
                TimelineScreen(state = uiState, onExportReport = { viewModel.exportReport() })
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
                    onSelectDohProvider = { viewModel.setDohProvider(it) },
                    onToggleDnsCheck = { enabled -> viewModel.setDnsCheckEnabled(enabled) },
                    onOpenReplay = { navController.navigate(AppRoutes.Replay) },
                    onScanIntervalChange = { viewModel.setScanInterval(it) },
                    onThemeChange = { viewModel.setThemeMode(it) },
                    onToggleAlwaysOn = { enabled -> viewModel.setAlwaysOnEnabled(enabled) },
                    onToggleMaskSensitive = { enabled -> viewModel.setMaskSensitive(enabled) }
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
                            is ReplayViewModel.ReplayEvent.Share -> shareReport(context, event.uri)
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
                    onSaveCurrent = { viewModel.exportCurrent() },
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

private fun shareReport(context: Context, uri: Uri) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/json"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        clipData = ClipData.newUri(context.contentResolver, "WiFi Sentinel Report", uri)
    }
    context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_report_title)))
}
