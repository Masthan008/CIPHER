package com.cipher.media.ui.navigation

/**
 * Sealed class defining all navigation destinations.
 */
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object Auth : Screen("auth")
    data object VideoBrowser : Screen("video_browser")
    data object AudioBrowser : Screen("audio_browser")
    data object AudioPlayer : Screen("audio_player")
    data object Equalizer : Screen("equalizer")
    data object VaultSetup : Screen("vault_setup")
    data object VaultAuth : Screen("vault_auth")
    data object VaultBrowser : Screen("vault_browser")
    data object Calculator : Screen("calculator")
    data object StealthSetup : Screen("stealth_setup")
    data object IntruderLog : Screen("intruder_log")
    data object Settings : Screen("settings")
    data object Search : Screen("search")
    data object Premium : Screen("premium")
    data object CloudSync : Screen("cloud_sync")
    data object VaultImageViewer : Screen("vault_image/{itemId}") {
        fun createRoute(itemId: String): String = "vault_image/$itemId"
    }
    data object VaultVideoPlayer : Screen("vault_video/{itemId}") {
        fun createRoute(itemId: String): String = "vault_video/$itemId"
    }
    data object VideoPlayer : Screen("video_player/{videoUri}") {
        fun createRoute(videoUri: String): String = "video_player/$videoUri"
    }
    data object DecoyVault : Screen("decoy_vault")
    data object NetworkPlayer : Screen("network_player/{streamUrl}") {
        fun createRoute(streamUrl: String): String = "network_player/$streamUrl"
    }
}
