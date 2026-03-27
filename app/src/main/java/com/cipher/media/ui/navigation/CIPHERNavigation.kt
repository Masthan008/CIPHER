package com.cipher.media.ui.navigation

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cipher.media.ui.audio.AudioBrowserScreen
import com.cipher.media.ui.audio.AudioPlayerScreen
import com.cipher.media.ui.audio.AudioPlayerViewModel
import com.cipher.media.ui.audio.equalizer.EqualizerScreen
import com.cipher.media.ui.auth.AuthScreen
import com.cipher.media.ui.auth.OnboardingScreen
import com.cipher.media.ui.auth.SplashScreen
import com.cipher.media.ui.components.MiniPlayer
import com.cipher.media.ui.premium.PremiumScreen
import com.cipher.media.ui.search.SearchScreen
import com.cipher.media.ui.settings.SettingsScreen
import com.cipher.media.ui.stealth.CalculatorScreen
import com.cipher.media.ui.stealth.StealthSetupScreen
import com.cipher.media.ui.stealth.StealthViewModel
import com.cipher.media.ui.theme.CIPHERBackground
import com.cipher.media.ui.theme.CIPHERDivider
import com.cipher.media.ui.theme.CIPHEROnSurface
import com.cipher.media.ui.theme.CIPHEROnSurfaceVariant
import com.cipher.media.ui.theme.CIPHERPrimary
import com.cipher.media.ui.theme.CIPHERSurface
import com.cipher.media.ui.theme.Spacing
import com.cipher.media.ui.vault.VaultAuthScreen
import com.cipher.media.ui.vault.VaultBrowserScreen
import com.cipher.media.ui.vault.VaultSetupScreen
import com.cipher.media.ui.vault.VaultViewModel
import com.cipher.media.ui.vault.components.IntruderLogScreen
import com.cipher.media.ui.vault.viewer.EncryptedImageViewer
import com.cipher.media.ui.vault.viewer.EncryptedVideoPlayer
import com.cipher.media.ui.video.VideoBrowserScreen
import com.cipher.media.ui.video.VideoPlayerScreen

data class BottomNavItem(val screen: Screen, val label: String, val icon: ImageVector)

val bottomNavItems = listOf(
    BottomNavItem(Screen.VideoBrowser, "Video", Icons.Default.VideoLibrary),
    BottomNavItem(Screen.AudioBrowser, "Music", Icons.Default.MusicNote),
    BottomNavItem(Screen.VaultAuth, "Vault", Icons.Default.Lock)
)

// Nav transition specs
private val enterAnim: EnterTransition = fadeIn(tween(300, easing = FastOutSlowInEasing)) +
    slideInHorizontally(initialOffsetX = { it / 6 }, animationSpec = tween(300, easing = FastOutSlowInEasing))
private val exitAnim: ExitTransition = fadeOut(tween(300, easing = FastOutSlowInEasing)) +
    slideOutHorizontally(targetOffsetX = { -it / 6 }, animationSpec = tween(300, easing = FastOutSlowInEasing))
private val popEnterAnim: EnterTransition = fadeIn(tween(300, easing = FastOutSlowInEasing)) +
    slideInHorizontally(initialOffsetX = { -it / 6 }, animationSpec = tween(300, easing = FastOutSlowInEasing))
private val popExitAnim: ExitTransition = fadeOut(tween(300, easing = FastOutSlowInEasing)) +
    slideOutHorizontally(targetOffsetX = { it / 6 }, animationSpec = tween(300, easing = FastOutSlowInEasing))

@Composable
fun CIPHERNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context = LocalContext.current

    val audioViewModel: AudioPlayerViewModel = hiltViewModel()
    val currentAudio by audioViewModel.currentAudio.collectAsState(initial = null)
    val isPlaying by audioViewModel.isPlaying.collectAsState(initial = false)

    val stealthViewModel: StealthViewModel = hiltViewModel()

    val prefs = remember {
        context.getSharedPreferences("cipher_vault_prefs", android.content.Context.MODE_PRIVATE)
    }
    val storedPinHash = remember { prefs.getString("vault_pin_hash", null) }

    val startDest = if (stealthViewModel.isStealthEnabled) Screen.Calculator.route else Screen.Splash.route

    val hideBottomBarRoutes = listOf(
        Screen.Splash.route, Screen.Onboarding.route, Screen.Auth.route,
        Screen.AudioPlayer.route, Screen.Equalizer.route,
        Screen.VaultSetup.route, Screen.VaultBrowser.route,
        Screen.Calculator.route, Screen.StealthSetup.route, Screen.IntruderLog.route,
        Screen.Settings.route, Screen.Search.route, Screen.Premium.route
    )
    val showBottomBar = currentDestination?.route?.let { route ->
        !hideBottomBarRoutes.contains(route) &&
        !route.startsWith("video_player") &&
        !route.startsWith("vault_image") &&
        !route.startsWith("vault_video")
    } ?: false

    val showMiniPlayer = currentAudio != null &&
        currentDestination?.route != Screen.AudioPlayer.route &&
        currentDestination?.route?.startsWith("video_player") != true

    Scaffold(
        containerColor = CIPHERBackground,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = CIPHERSurface, contentColor = CIPHERPrimary) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true
                        NavigationBarItem(
                            icon = { Icon(item.icon, item.label) },
                            label = { Text(item.label, style = MaterialTheme.typography.labelSmall) },
                            selected = selected,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = CIPHERPrimary,
                                selectedTextColor = CIPHERPrimary,
                                unselectedIconColor = CIPHEROnSurfaceVariant,
                                unselectedTextColor = CIPHEROnSurfaceVariant,
                                indicatorColor = CIPHERSurface
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = startDest,
                enterTransition = { enterAnim },
                exitTransition = { exitAnim },
                popEnterTransition = { popEnterAnim },
                popExitTransition = { popExitAnim }
            ) {
                // -- Auth Flow --
                composable(Screen.Splash.route) {
                    SplashScreen(onSplashComplete = {
                        navController.navigate(Screen.Onboarding.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    })
                }
                composable(Screen.Onboarding.route) {
                    OnboardingScreen(
                        onNextClick = {
                            navController.navigate(Screen.Auth.route) {
                                popUpTo(Screen.Onboarding.route) { inclusive = true }
                            }
                        },
                        onSkipClick = {
                            navController.navigate(Screen.Auth.route) {
                                popUpTo(Screen.Onboarding.route) { inclusive = true }
                            }
                        }
                    )
                }
                composable(Screen.Auth.route) {
                    AuthScreen(onAuthSuccess = {
                        navController.navigate(Screen.VideoBrowser.route) {
                            popUpTo(Screen.Auth.route) { inclusive = true }
                        }
                    })
                }

                // -- Main Tabs --
                composable(Screen.VideoBrowser.route) {
                    val adManager = remember {
                        (context.applicationContext as com.cipher.media.CIPHERApplication).adManager
                    }
                    VideoBrowserScreen(
                        onVideoClick = { mediaItem ->
                            // Show interstitial every 5th video play
                            val activity = context as? android.app.Activity
                            if (activity != null) {
                                adManager.onVideoPlayed(activity)
                            }
                            val encodedUri = Uri.encode(mediaItem.uri.toString())
                            navController.navigate(Screen.VideoPlayer.createRoute(encodedUri))
                        },
                        onSearchClick = { navController.navigate(Screen.Search.route) },
                        onSettingsClick = { navController.navigate(Screen.Settings.route) }
                    )
                }
                composable(Screen.AudioBrowser.route) {
                    AudioBrowserScreen(
                        onAudioClick = { audio, playlist ->
                            audioViewModel.playAudio(audio, playlist)
                            navController.navigate(Screen.AudioPlayer.route)
                        },
                        onSearchClick = { navController.navigate(Screen.Search.route) },
                        viewModel = audioViewModel
                    )
                }
                composable(Screen.AudioPlayer.route) {
                    AudioPlayerScreen(
                        viewModel = audioViewModel,
                        onBack = { navController.popBackStack() },
                        onOpenEqualizer = { navController.navigate(Screen.Equalizer.route) }
                    )
                }
                composable(Screen.Equalizer.route) {
                    EqualizerScreen(onBack = { navController.popBackStack() })
                }

                // -- Calculator Disguise --
                composable(Screen.Calculator.route) {
                    CalculatorScreen(
                        onSecretTriggered = {
                            navController.navigate(Screen.VaultAuth.route) {
                                popUpTo(Screen.Calculator.route) { inclusive = true }
                            }
                        },
                        viewModel = stealthViewModel
                    )
                }

                // -- Vault Flow --
                composable(Screen.VaultAuth.route) {
                    if (storedPinHash == null) {
                        VaultSetupScreen(onSetupComplete = { pinHash ->
                            prefs.edit().putString("vault_pin_hash", pinHash).apply()
                            navController.navigate(Screen.VaultBrowser.route) {
                                popUpTo(Screen.VaultAuth.route) { inclusive = true }
                            }
                        })
                    } else {
                        val vaultVm: VaultViewModel = hiltViewModel()
                        VaultAuthScreen(
                            onAuthenticated = {
                                navController.navigate(Screen.VaultBrowser.route) {
                                    popUpTo(Screen.VaultAuth.route) { inclusive = true }
                                }
                            },
                            viewModel = vaultVm
                        )
                    }
                }
                composable(Screen.VaultBrowser.route) {
                    val vaultViewModel: VaultViewModel = hiltViewModel()
                    VaultBrowserScreen(
                        onFileClick = { itemId -> },
                        onBack = { navController.popBackStack() },
                        viewModel = vaultViewModel
                    )
                }

                // -- Settings & Search --
                composable(Screen.Settings.route) {
                    SettingsScreen(
                        onBack = { navController.popBackStack() },
                        onNavigateTo = { route ->
                            when (route) {
                                "intruder_log" -> navController.navigate(Screen.IntruderLog.route)
                                "premium" -> navController.navigate(Screen.Premium.route)
                                else -> navController.navigate(Screen.StealthSetup.route)
                            }
                        }
                    )
                }
                composable(Screen.Search.route) {
                    SearchScreen(
                        onBack = { navController.popBackStack() },
                        onVideoClick = { uri ->
                            val encodedUri = Uri.encode(uri)
                            navController.navigate(Screen.VideoPlayer.createRoute(encodedUri))
                        },
                        onAudioClick = { uri ->
                            // For audio, load and play
                            navController.navigate(Screen.AudioPlayer.route)
                        }
                    )
                }
                composable(Screen.StealthSetup.route) {
                    StealthSetupScreen(onBack = { navController.popBackStack() })
                }
                composable(Screen.IntruderLog.route) {
                    IntruderLogScreen(onBack = { navController.popBackStack() })
                }
                composable(Screen.Premium.route) {
                    PremiumScreen(onBack = { navController.popBackStack() })
                }

                // -- Viewers --
                composable(
                    route = Screen.VaultImageViewer.route,
                    arguments = listOf(navArgument("itemId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val itemId = backStackEntry.arguments?.getString("itemId") ?: return@composable
                    val vaultViewModel: VaultViewModel = hiltViewModel()
                    val items by vaultViewModel.vaultItems.collectAsState()
                    items.find { it.id == itemId }?.let { item ->
                        EncryptedImageViewer(
                            item = item, viewModel = vaultViewModel,
                            onBack = { navController.popBackStack() },
                            onDelete = { vaultViewModel.deleteItem(item); navController.popBackStack() }
                        )
                    }
                }
                composable(
                    route = Screen.VaultVideoPlayer.route,
                    arguments = listOf(navArgument("itemId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val itemId = backStackEntry.arguments?.getString("itemId") ?: return@composable
                    val vaultViewModel: VaultViewModel = hiltViewModel()
                    val items by vaultViewModel.vaultItems.collectAsState()
                    items.find { it.id == itemId }?.let { item ->
                        EncryptedVideoPlayer(
                            item = item, viewModel = vaultViewModel,
                            onBack = { navController.popBackStack() },
                            onDelete = { vaultViewModel.deleteItem(item); navController.popBackStack() }
                        )
                    }
                }
                composable(
                    route = Screen.VideoPlayer.route,
                    arguments = listOf(navArgument("videoUri") { type = NavType.StringType })
                ) { backStackEntry ->
                    val videoUri = backStackEntry.arguments?.getString("videoUri") ?: return@composable
                    VideoPlayerScreen(videoUri = Uri.decode(videoUri), onBack = { navController.popBackStack() })
                }
            }

            if (showMiniPlayer) {
                MiniPlayer(
                    currentAudio = currentAudio, isPlaying = isPlaying,
                    onTap = { navController.navigate(Screen.AudioPlayer.route) },
                    onPlayPause = { audioViewModel.togglePlayPause() },
                    onDismiss = { audioViewModel.stopPlayback() },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}
