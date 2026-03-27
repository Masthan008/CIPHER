package com.cipher.media.ui.stealth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.ui.theme.*

/**
 * Settings screen for stealth mode configuration.
 */
@Composable
fun StealthSetupScreen(
    onBack: () -> Unit,
    viewModel: StealthViewModel = hiltViewModel()
) {
    var stealthEnabled by remember { mutableStateOf(viewModel.isStealthEnabled) }
    var intruderEnabled by remember { mutableStateOf(viewModel.isIntruderDetectionEnabled) }
    var selfDestructEnabled by remember { mutableStateOf(viewModel.isSelfDestructEnabled) }
    var secretCode by remember { mutableStateOf(viewModel.secretCode) }
    var showSecretCodeDialog by remember { mutableStateOf(false) }
    var showDecoyPinDialog by remember { mutableStateOf(false) }
    var showSelfDestructWarning by remember { mutableStateOf(false) }
    var newCode by remember { mutableStateOf("") }

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
                    text = "Stealth Settings",
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
            // Calculator Disguise
            Text(
                text = "DISGUISE",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold, letterSpacing = 1.sp
                ),
                color = CIPHERPrimary,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

            SettingsToggle(
                icon = Icons.Default.Calculate,
                title = "Calculator Mode",
                subtitle = "App appears as a calculator in launcher",
                checked = stealthEnabled,
                onCheckedChange = {
                    stealthEnabled = it
                    viewModel.setStealthEnabled(it)
                }
            )

            SettingsItem(
                icon = Icons.Default.Pin,
                title = "Secret Code",
                subtitle = "Current: $secretCode",
                onClick = { showSecretCodeDialog = true }
            )

            SettingsItem(
                icon = Icons.Default.VisibilityOff,
                title = "Decoy PIN",
                subtitle = if (viewModel.decoyPinHash != null) "Configured" else "Not set",
                onClick = { showDecoyPinDialog = true }
            )

            Divider(color = CIPHEROutlineVariant, modifier = Modifier.padding(vertical = 12.dp))

            // Security
            Text(
                text = "SECURITY",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold, letterSpacing = 1.sp
                ),
                color = CIPHERPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            SettingsToggle(
                icon = Icons.Default.CameraAlt,
                title = "Intruder Detection",
                subtitle = "Take photo on failed unlock attempts",
                checked = intruderEnabled,
                onCheckedChange = {
                    intruderEnabled = it
                    viewModel.setIntruderDetection(it)
                }
            )

            SettingsToggle(
                icon = Icons.Default.DeleteForever,
                title = "Self-Destruct",
                subtitle = "Wipe vault after ${viewModel.selfDestructAttempts} failed attempts",
                checked = selfDestructEnabled,
                onCheckedChange = { enabled ->
                    if (enabled) {
                        showSelfDestructWarning = true
                    } else {
                        selfDestructEnabled = false
                        viewModel.setSelfDestruct(false)
                    }
                }
            )
        }
    }

    // Secret Code Dialog
    if (showSecretCodeDialog) {
        AlertDialog(
            onDismissRequest = { showSecretCodeDialog = false },
            containerColor = CIPHERSurface,
            title = { Text("Set Secret Code", color = CIPHEROnSurface, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        "Enter digits on calculator, then press = to unlock vault",
                        color = CIPHEROnSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = newCode,
                        onValueChange = { if (it.all { c -> c.isDigit() }) newCode = it },
                        label = { Text("Code (digits only)") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newCode.length >= 4) {
                        viewModel.setSecretCode(newCode)
                        secretCode = newCode
                        newCode = ""
                        showSecretCodeDialog = false
                    }
                }) { Text("SAVE", color = CIPHERPrimary) }
            },
            dismissButton = {
                TextButton(onClick = { showSecretCodeDialog = false }) {
                    Text("CANCEL", color = CIPHEROnSurfaceVariant)
                }
            }
        )
    }

    // Self-Destruct Warning
    if (showSelfDestructWarning) {
        AlertDialog(
            onDismissRequest = { showSelfDestructWarning = false },
            containerColor = CIPHERSurface,
            icon = { Icon(Icons.Default.Warning, null, tint = CIPHERError) },
            title = { Text("Enable Self-Destruct?", color = CIPHERError, fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "After ${viewModel.selfDestructAttempts} failed unlock attempts, ALL vault data will be permanently destroyed. This cannot be undone.",
                    color = CIPHEROnSurfaceVariant
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    selfDestructEnabled = true
                    viewModel.setSelfDestruct(true)
                    showSelfDestructWarning = false
                }) { Text("ENABLE", color = CIPHERError) }
            },
            dismissButton = {
                TextButton(onClick = { showSelfDestructWarning = false }) {
                    Text("CANCEL", color = CIPHEROnSurfaceVariant)
                }
            }
        )
    }
}

@Composable
private fun SettingsToggle(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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

@Composable
private fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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
