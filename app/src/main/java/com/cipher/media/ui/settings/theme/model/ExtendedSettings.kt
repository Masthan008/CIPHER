package com.cipher.media.ui.settings.theme.model

/**
 * Extended app settings for the full settings screen.
 */
data class ExtendedSettings(
    // Audio
    val gaplessPlayback: Boolean = true,
    val skipSilence: Boolean = false,
    val crossfadeEnabled: Boolean = false,
    val crossfadeDurationSec: Int = 3,
    val resumeOnHeadset: Boolean = true,
    val autoPlay: Boolean = false,

    // Library
    val scanFolders: List<String> = emptyList(),
    val ignoreFolders: List<String> = emptyList(),
    val showFileSize: Boolean = false,
    val showBitrate: Boolean = false,

    // Privacy
    val incognitoMode: Boolean = false,
    val scrobblingEnabled: Boolean = false,

    // Storage
    val downloadAlbumArt: Boolean = true,
    val maxCacheMb: Int = 200,

    // Theme
    val themeId: String = "dark",
    val accentColor: AccentColor = AccentColor.SAFFRON,
    val nowPlayingLayout: NowPlayingLayout = NowPlayingLayout.STANDARD,
    val appFont: AppFont = AppFont.SYSTEM,
    val animSpeed: AnimSpeed = AnimSpeed.NORMAL,
    val materialYou: Boolean = false,

    // Stats
    val totalListeningTimeMs: Long = 0L,
    val totalSongsPlayed: Int = 0
)
