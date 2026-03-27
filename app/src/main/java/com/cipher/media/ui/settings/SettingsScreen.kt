package com.cipher.media.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.ui.settings.components.StorageInfo
import com.cipher.media.ui.settings.components.ThemeSelector
import com.cipher.media.ui.theme.*

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onStealthSetup: () -> Unit,
    onIntruderLog: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()
    var showClearCacheDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { viewModel.refreshStorageInfo() }

    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = CIPHEROnSurface)
                }
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = CIPHEROnSurface
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // APPEARANCE
            SectionHeader("APPEARANCE")
            ThemeSelector(
                currentTheme = settings.themeMode,
                onThemeChange = { viewModel.setTheme(it) }
            )

            Spacer(Modifier.height(8.dp))

            // SECURITY
            SectionHeader("SECURITY")
            SettingsNavItem(
                icon = Icons.Default.Calculate,
                title = "Stealth Mode",
                subtitle = "Calculator disguise & secret code",
                onClick = onStealthSetup
            )
            SettingsNavItem(
                icon = Icons.Default.Warning,
                title = "Break-in Alerts",
                subtitle = "View intruder detection logs",
                onClick = onIntruderLog
            )

            SettingsToggleItem(
                icon = Icons.Default.Fingerprint,
                title = "Biometric Every Time",
                subtitle = "Require biometric on each vault open",
                checked = settings.biometricEveryTime,
                onCheckedChange = { viewModel.setBiometricMode(it) }
            )

            Spacer(Modifier.height(8.dp))

            // AUTO-LOCK
            SectionHeader("VAULT AUTO-LOCK")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AutoLockDuration.entries.forEach { duration ->
                    FilterChip(
                        selected = settings.autoLock == duration,
                        onClick = { viewModel.setAutoLock(duration) },
                        label = {
                            Text(when (duration) {
                                AutoLockDuration.ONE_MIN -> "1 min"
                                AutoLockDuration.FIVE_MIN -> "5 min"
                                AutoLockDuration.NEVER -> "Never"
                            })
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CIPHERPrimary,
                            selectedLabelColor = CIPHEROnPrimary
                        )
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // STORAGE
            SectionHeader("STORAGE")
            StorageInfo(vaultSize = settings.vaultSize, cacheSize = settings.cacheSize)
            Spacer(Modifier.height(8.dp))

            SettingsNavItem(
                icon = Icons.Default.DeleteSweep,
                title = "Clear Cache",
                subtitle = "Free up temporary files",
                onClick = { showClearCacheDialog = true }
            )

            Spacer(Modifier.height(16.dp))

            // ABOUT
            SectionHeader("ABOUT")
            SettingsNavItem(
                icon = Icons.Default.Info,
                title = "CIPHER",
                subtitle = "Version 1.0.0 • Privacy-first media player",
                onClick = { }
            )
        }
    }

    if (showClearCacheDialog) {
        AlertDialog(
            onDismissRequest = { showClearCacheDialog = false },
            containerColor = CIPHERSurface,
            title = { Text("Clear Cache?", color = CIPHEROnSurface, fontWeight = FontWeight.Bold) },
            text = { Text("This will remove all cached data.", color = CIPHEROnSurfaceVariant) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.clearCache()
                    showClearCacheDialog = false
                }) { Text("CLEAR", color = CIPHERPrimary) }
            },
            dismissButton = {
                TextButton(onClick = { showClearCacheDialog = false }) {
                    Text("CANCEL", color = CIPHEROnSurfaceVariant)
                }
            }
        )
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold, letterSpacing = 1.sp
        ),
        color = CIPHERPrimary,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
}

@Composable
private fun SettingsNavItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(icon, null, tint = CIPHEROnSurfaceVariant, modifier = Modifier.size(24.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge, color = CIPHEROnSurface)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurfaceVariant)
        }
        Icon(Icons.Default.ChevronRight, null, tint = CIPHEROnSurfaceVariant)
    }
}

@Composable
private fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(icon, null, tint = CIPHEROnSurfaceVariant, modifier = Modifier.size(24.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge, color = CIPHEROnSurface)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurfaceVariant)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = CIPHERPrimary,
                checkedTrackColor = CIPHERPrimary.copy(alpha = 0.4f)
            )
        )
    }
}
