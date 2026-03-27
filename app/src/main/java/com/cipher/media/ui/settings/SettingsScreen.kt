package com.cipher.media.ui.settings

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.ui.components.CIPHERIconButton
import com.cipher.media.ui.theme.*

/**
 * Settings: all items are wired to SettingsViewModel for persistent storage.
 */
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onNavigateTo: (String) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()
    val context = LocalContext.current

    // Dialog states
    var showAutoLockDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showSpeedDialog by remember { mutableStateOf(false) }
    var showSubtitleDialog by remember { mutableStateOf(false) }

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
                    Toast.makeText(context, "Theme: ${next.name}", Toast.LENGTH_SHORT).show()
                }
                SettingsItem(Icons.Default.Language, "Language", settings.language) {
                    showLanguageDialog = true
                }
            }

            // Privacy
            SettingsSection("Privacy") {
                SettingsItem(Icons.Default.Lock, "Auto-Lock", when (settings.autoLock) {
                    AutoLockDuration.ONE_MIN -> "1 Minute"
                    AutoLockDuration.FIVE_MIN -> "5 Minutes"
                    AutoLockDuration.NEVER -> "Never"
                }) {
                    showAutoLockDialog = true
                }
                SettingsItem(Icons.Default.Fingerprint, "Biometric Every Time", if (settings.biometricEveryTime) "On" else "Off") {
                    viewModel.setBiometricMode(!settings.biometricEveryTime)
                    Toast.makeText(context, "Biometric: ${if (!settings.biometricEveryTime) "On" else "Off"}", Toast.LENGTH_SHORT).show()
                }
                SettingsItem(Icons.Default.CameraFront, "Intruder Alerts", "View log") {
                    onNavigateTo("intruder_log")
                }
            }

            // Playback
            SettingsSection("Playback") {
                SettingsItem(Icons.Default.ScreenRotation, "Auto-Rotate", if (settings.autoRotate) "On" else "Off") {
                    viewModel.setAutoRotate(!settings.autoRotate)
                    Toast.makeText(context, "Auto-Rotate: ${if (!settings.autoRotate) "On" else "Off"}", Toast.LENGTH_SHORT).show()
                }
                SettingsItem(Icons.Default.Speed, "Default Speed", settings.playbackSpeed) {
                    showSpeedDialog = true
                }
                SettingsItem(Icons.Default.Subtitles, "Subtitle Size", settings.subtitleSize) {
                    showSubtitleDialog = true
                }
            }

            // Storage
            val cacheMb = "%.1f MB".format(settings.cacheSize / 1_048_576.0)
            val vaultMb = "%.1f MB".format(settings.vaultSize / 1_048_576.0)
            SettingsSection("Storage & Cloud") {
                SettingsItem(Icons.Default.Storage, "Cache", cacheMb) { }
                SettingsItem(Icons.Default.FolderSpecial, "Vault Storage", vaultMb) { }
                SettingsItem(Icons.Default.CloudSync, "Cloud Vault Sync", "Backup to Cloudinary") {
                    onNavigateTo("cloud_sync")
                }
                SettingsItem(Icons.Default.DeleteSweep, "Clear Cache", "") {
                    viewModel.clearCache()
                    Toast.makeText(context, "Cache cleared", Toast.LENGTH_SHORT).show()
                }
            }

            // Premium
            SettingsSection("Premium") {
                SettingsItem(Icons.Default.Star, "Go Premium", "Remove ads & more", tint = CIPHERPrimary) {
                    onNavigateTo("premium")
                }
            }

            // About
            SettingsSection("About") {
                SettingsItem(Icons.Default.Info, "Version", "1.0.0") {
                    Toast.makeText(context, "CIPHER v1.0.0 — Your Media, Your Secret", Toast.LENGTH_SHORT).show()
                }
                SettingsItem(Icons.Default.Policy, "Privacy Policy", "") {
                    Toast.makeText(context, "Privacy Policy coming soon", Toast.LENGTH_SHORT).show()
                }
                SettingsItem(Icons.Default.StarRate, "Rate App", "") {
                    Toast.makeText(context, "Thank you for your support!", Toast.LENGTH_SHORT).show()
                }
            }

            Spacer(Modifier.height(Spacing.xxl))
        }
    }

    // --- Dialogs ---

    // Auto-Lock selection dialog
    if (showAutoLockDialog) {
        AlertDialog(
            onDismissRequest = { showAutoLockDialog = false },
            title = { Text("Auto-Lock Duration", color = CIPHEROnSurface) },
            text = {
                Column {
                    AutoLockDuration.entries.forEach { duration ->
                        val label = when (duration) {
                            AutoLockDuration.ONE_MIN -> "1 Minute"
                            AutoLockDuration.FIVE_MIN -> "5 Minutes"
                            AutoLockDuration.NEVER -> "Never"
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setAutoLock(duration)
                                    showAutoLockDialog = false
                                    Toast.makeText(context, "Auto-Lock: $label", Toast.LENGTH_SHORT).show()
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = settings.autoLock == duration,
                                onClick = {
                                    viewModel.setAutoLock(duration)
                                    showAutoLockDialog = false
                                    Toast.makeText(context, "Auto-Lock: $label", Toast.LENGTH_SHORT).show()
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = CIPHERPrimary)
                            )
                            Spacer(Modifier.width(Spacing.sm))
                            Text(label, color = CIPHEROnSurface)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAutoLockDialog = false }) {
                    Text("Cancel", color = CIPHERPrimary)
                }
            },
            containerColor = CIPHERSurface
        )
    }

    // Language selection dialog
    if (showLanguageDialog) {
        val languages = listOf("English", "हिन्दी", "தமிழ்", "తెలుగు", "मराठी", "বাংলা", "ગુજરાતી", "ಕನ್ನಡ", "മലയാളം", "ਪੰਜਾਬੀ", "اردو")
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text("Select Language", color = CIPHEROnSurface) },
            text = {
                Column {
                    languages.forEach { lang ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setLanguage(lang)
                                    showLanguageDialog = false
                                    Toast.makeText(context, "Language: $lang", Toast.LENGTH_SHORT).show()
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = settings.language == lang,
                                onClick = {
                                    viewModel.setLanguage(lang)
                                    showLanguageDialog = false
                                    Toast.makeText(context, "Language: $lang", Toast.LENGTH_SHORT).show()
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = CIPHERPrimary)
                            )
                            Spacer(Modifier.width(Spacing.sm))
                            Text(lang, color = CIPHEROnSurface)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguageDialog = false }) { Text("Cancel", color = CIPHERPrimary) }
            },
            containerColor = CIPHERSurface
        )
    }

    // Playback speed dialog
    if (showSpeedDialog) {
        val speeds = listOf("0.5x", "0.75x", "1x", "1.25x", "1.5x", "2x")
        AlertDialog(
            onDismissRequest = { showSpeedDialog = false },
            title = { Text("Default Playback Speed", color = CIPHEROnSurface) },
            text = {
                Column {
                    speeds.forEach { speed ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setPlaybackSpeed(speed)
                                    showSpeedDialog = false
                                    Toast.makeText(context, "Speed: $speed", Toast.LENGTH_SHORT).show()
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = settings.playbackSpeed == speed,
                                onClick = {
                                    viewModel.setPlaybackSpeed(speed)
                                    showSpeedDialog = false
                                    Toast.makeText(context, "Speed: $speed", Toast.LENGTH_SHORT).show()
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = CIPHERPrimary)
                            )
                            Spacer(Modifier.width(Spacing.sm))
                            Text(speed, color = CIPHEROnSurface)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSpeedDialog = false }) { Text("Cancel", color = CIPHERPrimary) }
            },
            containerColor = CIPHERSurface
        )
    }

    // Subtitle size dialog
    if (showSubtitleDialog) {
        val sizes = listOf("Small", "Medium", "Large", "Extra Large")
        AlertDialog(
            onDismissRequest = { showSubtitleDialog = false },
            title = { Text("Subtitle Size", color = CIPHEROnSurface) },
            text = {
                Column {
                    sizes.forEach { size ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setSubtitleSize(size)
                                    showSubtitleDialog = false
                                    Toast.makeText(context, "Subtitle: $size", Toast.LENGTH_SHORT).show()
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = settings.subtitleSize == size,
                                onClick = {
                                    viewModel.setSubtitleSize(size)
                                    showSubtitleDialog = false
                                    Toast.makeText(context, "Subtitle: $size", Toast.LENGTH_SHORT).show()
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = CIPHERPrimary)
                            )
                            Spacer(Modifier.width(Spacing.sm))
                            Text(size, color = CIPHEROnSurface)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSubtitleDialog = false }) { Text("Cancel", color = CIPHERPrimary) }
            },
            containerColor = CIPHERSurface
        )
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
