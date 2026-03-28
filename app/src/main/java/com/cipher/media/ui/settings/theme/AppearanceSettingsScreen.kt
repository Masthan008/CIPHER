package com.cipher.media.ui.settings.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cipher.media.data.model.audiofx.Tier
import com.cipher.media.ui.settings.theme.components.*
import com.cipher.media.ui.settings.theme.model.*
import com.cipher.media.ui.theme.*

/**
 * Complete Appearance & Settings screen combining:
 * - Theme selector (10 themes)
 * - Accent color picker
 * - Now Playing layout
 * - Audio settings
 * - Backup/Restore
 * - Stats dashboard
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceSettingsScreen(
    themes: List<ThemeConfig>,
    selectedThemeId: String,
    selectedAccent: AccentColor,
    selectedLayout: NowPlayingLayout,
    userTier: Tier,
    extendedSettings: ExtendedSettings,
    onThemeSelected: (ThemeConfig) -> Unit,
    onAccentSelected: (AccentColor) -> Unit,
    onLayoutSelected: (NowPlayingLayout) -> Unit,
    onGaplessChanged: (Boolean) -> Unit,
    onSkipSilenceChanged: (Boolean) -> Unit,
    onCrossfadeChanged: (Boolean) -> Unit,
    onResumeHeadsetChanged: (Boolean) -> Unit,
    onIncognitoChanged: (Boolean) -> Unit,
    onMaterialYouChanged: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text("Appearance & Settings", fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = CIPHEROnSurface)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CIPHERBackground)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.md)
        ) {
            // ── Theme Section ──
            SectionHeader("🎨 Theme")
            ThemeSelector(
                themes = themes,
                selectedThemeId = selectedThemeId,
                userTier = userTier,
                onThemeSelected = onThemeSelected
            )

            Spacer(Modifier.height(Spacing.lg))

            // ── Accent Color ──
            AccentColorPicker(
                colors = if (userTier == Tier.FREE) AccentColor.FREE_COLORS else AccentColor.PRO_COLORS,
                selectedAccent = selectedAccent,
                userTier = userTier,
                onAccentSelected = onAccentSelected
            )

            // Material You toggle
            if (android.os.Build.VERSION.SDK_INT >= 31) {
                Spacer(Modifier.height(Spacing.sm))
                SettingsSwitch(
                    label = "Material You (Dynamic Colors)",
                    description = "Use wallpaper-based colors",
                    checked = extendedSettings.materialYou,
                    onCheckedChange = onMaterialYouChanged,
                    enabled = userTier != Tier.FREE
                )
            }

            Spacer(Modifier.height(Spacing.lg))

            // ── Now Playing ──
            NowPlayingLayoutPicker(
                selectedLayout = selectedLayout,
                userTier = userTier,
                onLayoutSelected = onLayoutSelected
            )

            Spacer(Modifier.height(Spacing.lg))
            HorizontalDivider(color = CIPHERDivider)
            Spacer(Modifier.height(Spacing.lg))

            // ── Audio Settings ──
            SectionHeader("🔊 Audio")

            SettingsSwitch("Gapless Playback", "Seamless transitions between tracks",
                extendedSettings.gaplessPlayback, onGaplessChanged)
            SettingsSwitch("Skip Silence", "Skip silent portions in tracks",
                extendedSettings.skipSilence, onSkipSilenceChanged)
            SettingsSwitch("Crossfade", "Fade between tracks",
                extendedSettings.crossfadeEnabled, onCrossfadeChanged, userTier != Tier.FREE)
            SettingsSwitch("Resume on Headset", "Auto-resume when headphones connected",
                extendedSettings.resumeOnHeadset, onResumeHeadsetChanged)

            Spacer(Modifier.height(Spacing.lg))
            HorizontalDivider(color = CIPHERDivider)
            Spacer(Modifier.height(Spacing.lg))

            // ── Privacy ──
            SectionHeader("🔒 Privacy")
            SettingsSwitch("Incognito Mode", "Don't track play history",
                extendedSettings.incognitoMode, onIncognitoChanged)

            Spacer(Modifier.height(Spacing.lg))
            HorizontalDivider(color = CIPHERDivider)
            Spacer(Modifier.height(Spacing.lg))

            // ── Stats ──
            StatsDashboard(userTier = userTier)

            Spacer(Modifier.height(Spacing.lg))
            HorizontalDivider(color = CIPHERDivider)
            Spacer(Modifier.height(Spacing.lg))

            // ── Backup ──
            BackupRestoreSection(userTier = userTier)

            Spacer(Modifier.height(Spacing.xl))
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = CIPHERPrimary,
        modifier = Modifier.padding(vertical = Spacing.sm)
    )
}

@Composable
private fun SettingsSwitch(
    label: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                label,
                style = MaterialTheme.typography.bodyLarge,
                color = if (enabled) CIPHEROnSurface else CIPHERTextDisabled
            )
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = CIPHEROnSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = { if (enabled) onCheckedChange(it) },
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = CIPHERPrimary,
                checkedTrackColor = CIPHERPrimary.copy(alpha = 0.3f)
            )
        )
    }
}
