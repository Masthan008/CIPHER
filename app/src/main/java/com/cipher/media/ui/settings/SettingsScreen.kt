package com.cipher.media.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.ui.components.CIPHERIconButton
import com.cipher.media.ui.theme.*

/**
 * Settings: grouped sections (Appearance, Privacy, Playback, Storage, About).
 */
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onNavigateTo: (String) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()

    LaunchedEffect(Unit) { viewModel.refreshStorageInfo() }

    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(Spacing.sm),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CIPHERIconButton(icon = Icons.Default.ArrowBack, onClick = onBack)
                Text("Settings", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            // Appearance
            SettingsSection("Appearance") {
                SettingsItem(Icons.Default.DarkMode, "Theme", settings.themeMode.name) {
                    val next = when (settings.themeMode) {
                        ThemeMode.DARK -> ThemeMode.LIGHT
                        ThemeMode.LIGHT -> ThemeMode.SYSTEM
                        ThemeMode.SYSTEM -> ThemeMode.DARK
                    }
                    viewModel.setTheme(next)
                }
                SettingsItem(Icons.Default.Language, "Language", "English") { }
            }

            // Privacy
            SettingsSection("Privacy") {
                SettingsItem(Icons.Default.Lock, "Auto-Lock", settings.autoLock.name) { }
                SettingsItem(Icons.Default.Fingerprint, "Biometric Every Time", if (settings.biometricEveryTime) "On" else "Off") {
                    viewModel.setBiometricMode(!settings.biometricEveryTime)
                }
                SettingsItem(Icons.Default.CameraFront, "Intruder Alerts", "View log") {
                    onNavigateTo("intruder_log")
                }
            }

            // Playback
            SettingsSection("Playback") {
                SettingsItem(Icons.Default.ScreenRotation, "Auto-Rotate", "On") { }
                SettingsItem(Icons.Default.Speed, "Default Speed", "1x") { }
                SettingsItem(Icons.Default.Subtitles, "Subtitle Size", "Medium") { }
            }

            // Storage
            val cacheMb = "%.1f MB".format(settings.cacheSize / 1_048_576.0)
            SettingsSection("Storage") {
                SettingsItem(Icons.Default.Storage, "Cache", cacheMb) { }
                SettingsItem(Icons.Default.DeleteSweep, "Clear Cache", "") { viewModel.clearCache() }
            }

            // Premium
            SettingsSection("Premium") {
                SettingsItem(Icons.Default.Star, "Go Premium", "Remove ads & more", tint = CIPHERPrimary) {
                    onNavigateTo("premium")
                }
            }

            // About
            SettingsSection("About") {
                SettingsItem(Icons.Default.Info, "Version", "1.0.0") { }
                SettingsItem(Icons.Default.Policy, "Privacy Policy", "") { }
                SettingsItem(Icons.Default.StarRate, "Rate App", "") { }
            }

            Spacer(Modifier.height(Spacing.xxl))
        }
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(title, style = MaterialTheme.typography.labelLarge, color = CIPHERPrimary, modifier = Modifier.padding(bottom = Spacing.sm))
        Card(colors = CardDefaults.cardColors(containerColor = CIPHERSurface), shape = RoundedCornerShape(Corners.large)) {
            Column(modifier = Modifier.padding(vertical = Spacing.xs)) { content() }
        }
    }
}

@Composable
private fun SettingsItem(
    icon: ImageVector, title: String, value: String,
    tint: Color = CIPHEROnSurfaceVariant, onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
            .padding(horizontal = Spacing.md, vertical = Spacing.sm + Spacing.xs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = tint, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(Spacing.md))
        Text(title, style = MaterialTheme.typography.bodyLarge, color = CIPHEROnSurface, modifier = Modifier.weight(1f))
        if (value.isNotEmpty()) {
            Text(value, style = MaterialTheme.typography.bodyMedium, color = CIPHEROnSurfaceVariant)
        }
        Spacer(Modifier.width(Spacing.xs))
        Icon(Icons.Default.ChevronRight, null, tint = CIPHEROutline, modifier = Modifier.size(20.dp))
    }
}
