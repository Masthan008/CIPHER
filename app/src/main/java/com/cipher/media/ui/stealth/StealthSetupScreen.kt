package com.cipher.media.ui.stealth

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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.security.AutoLockManager
import com.cipher.media.ui.components.CIPHERIconButton
import com.cipher.media.ui.theme.*

/**
 * Stealth & Security setup: toggle cards for calculator mode, secret code,
 * decoy PIN, intruder detection, self-destruct, and auto-lock.
 * Pro/Power features show lock badges.
 */
@Composable
fun StealthSetupScreen(
    onBack: () -> Unit,
    viewModel: StealthViewModel = hiltViewModel()
) {
    var calcMode by remember { mutableStateOf(viewModel.isStealthEnabled) }
    var intruderDetection by remember { mutableStateOf(viewModel.isIntruderDetectionEnabled) }
    var selfDestruct by remember { mutableStateOf(viewModel.isSelfDestructEnabled) }
    var decoyPin by remember { mutableStateOf(viewModel.decoyPinHash != null) }
    var autoLockEnabled by remember { mutableStateOf(false) }
    var autoLockMinutes by remember { mutableFloatStateOf(5f) }

    // Decoy PIN entry dialog
    var showDecoyPinDialog by remember { mutableStateOf(false) }
    var decoyPinInput by remember { mutableStateOf("") }

    if (showDecoyPinDialog) {
        AlertDialog(
            onDismissRequest = { showDecoyPinDialog = false; decoyPinInput = "" },
            title = { Text("Set Decoy PIN", color = CIPHEROnSurface) },
            text = {
                Column {
                    Text("Enter a 4-6 digit PIN for the decoy vault.", color = CIPHEROnSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = decoyPinInput,
                        onValueChange = { if (it.length <= 6 && it.all { c -> c.isDigit() }) decoyPinInput = it },
                        label = { Text("Decoy PIN") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CIPHERPrimary,
                            cursorColor = CIPHERPrimary,
                            focusedLabelColor = CIPHERPrimary
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (decoyPinInput.length >= 4) {
                        val digest = java.security.MessageDigest.getInstance("SHA-256")
                        val hash = digest.digest(decoyPinInput.toByteArray()).joinToString("") { "%02x".format(it) }
                        viewModel.setDecoyPin(hash)
                        decoyPin = true
                    }
                    showDecoyPinDialog = false
                    decoyPinInput = ""
                }) { Text("Set", color = CIPHERPrimary) }
            },
            dismissButton = {
                TextButton(onClick = { showDecoyPinDialog = false; decoyPinInput = "" }) {
                    Text("Cancel", color = CIPHEROnSurfaceVariant)
                }
            },
            containerColor = CIPHERSurface
        )
    }

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
                Text("Stealth & Security", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Spacing.md)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            // ── FREE TIER ──
            SectionHeader("Free Features")

            StealthToggleCard(
                icon = Icons.Default.Fingerprint,
                title = "Biometric Unlock",
                description = "Use fingerprint to unlock vault",
                checked = true,
                onCheckedChange = { },
                enabled = true
            )

            // ── PRO TIER ──
            SectionHeader("Pro Features", "🔒 PRO")

            StealthToggleCard(
                icon = Icons.Default.Calculate,
                title = "Calculator Mode",
                description = "App appears as a calculator in launcher",
                checked = calcMode,
                onCheckedChange = {
                    calcMode = it
                    viewModel.setStealthEnabled(it)
                }
            )

            StealthToggleCard(
                icon = Icons.Default.Password,
                title = "Secret Code",
                description = "Type secret code in calculator to open vault (${viewModel.secretCode})",
                checked = calcMode,
                onCheckedChange = { }
            )

            StealthToggleCard(
                icon = Icons.Default.ContentCopy,
                title = "Decoy Vault",
                description = if (decoyPin) "Decoy PIN is set" else "Set a secondary PIN for fake vault",
                checked = decoyPin,
                onCheckedChange = {
                    if (it) {
                        showDecoyPinDialog = true
                    } else {
                        viewModel.setDecoyPin(null)
                        decoyPin = false
                    }
                }
            )

            StealthToggleCard(
                icon = Icons.Default.CameraFront,
                title = "Intruder Detection",
                description = "Take photo + GPS on failed unlock attempts",
                checked = intruderDetection,
                onCheckedChange = {
                    intruderDetection = it
                    viewModel.setIntruderDetection(it)
                }
            )

            // Auto-Lock Card with slider
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CIPHERSurface),
                shape = RoundedCornerShape(Corners.large)
            ) {
                Column(modifier = Modifier.padding(Spacing.md)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Timer, null, tint = CIPHERPrimary, modifier = Modifier.size(28.dp))
                        Spacer(Modifier.width(Spacing.md))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Auto-Lock", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = CIPHEROnSurface)
                            Text("Lock vault after inactivity", style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurfaceVariant)
                        }
                        Switch(
                            checked = autoLockEnabled,
                            onCheckedChange = { autoLockEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = CIPHERPrimary,
                                checkedTrackColor = CIPHERPrimary.copy(alpha = 0.3f)
                            )
                        )
                    }
                    if (autoLockEnabled) {
                        Spacer(Modifier.height(Spacing.sm))
                        Text("${autoLockMinutes.toInt()} minutes", color = CIPHERPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Slider(
                            value = autoLockMinutes,
                            onValueChange = { autoLockMinutes = it },
                            valueRange = 1f..60f,
                            steps = 59,
                            colors = SliderDefaults.colors(
                                thumbColor = CIPHERPrimary,
                                activeTrackColor = CIPHERPrimary
                            )
                        )
                    }
                }
            }

            // ── POWER TIER ──
            SectionHeader("Power Features", "🔒 POWER")

            StealthToggleCard(
                icon = Icons.Default.DeleteForever,
                title = "Self-Destruct",
                description = "Wipe vault data after ${viewModel.selfDestructAttempts} failed attempts",
                checked = selfDestruct,
                onCheckedChange = {
                    selfDestruct = it
                    viewModel.setSelfDestruct(it)
                },
                tint = CIPHERError
            )

            StealthToggleCard(
                icon = Icons.Default.VisibilityOff,
                title = "Plausible Deniability",
                description = "Hidden volume inside vault (coming soon)",
                checked = false,
                onCheckedChange = { },
                enabled = false
            )

            StealthToggleCard(
                icon = Icons.Default.Key,
                title = "Hardware Security Key",
                description = "Unlock vault with FIDO2 key (coming soon)",
                checked = false,
                onCheckedChange = { },
                enabled = false
            )

            Spacer(Modifier.height(Spacing.xl))
        }
    }
}

@Composable
private fun SectionHeader(title: String, badge: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = CIPHEROnSurface
        )
        badge?.let {
            Spacer(Modifier.width(Spacing.sm))
            Text(it, fontSize = 12.sp, color = CIPHERPrimary)
        }
    }
}

@Composable
private fun StealthToggleCard(
    icon: ImageVector,
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    tint: Color = CIPHERPrimary,
    enabled: Boolean = true
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) CIPHERSurface else CIPHERSurface.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(Corners.large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = if (enabled) tint else tint.copy(alpha = 0.4f), modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(Spacing.md))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = if (enabled) CIPHEROnSurface else CIPHEROnSurfaceVariant)
                Text(description, style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurfaceVariant)
            }
            Switch(
                checked = checked,
                onCheckedChange = if (enabled) onCheckedChange else { _ -> },
                enabled = enabled,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = tint,
                    checkedTrackColor = tint.copy(alpha = 0.3f)
                )
            )
        }
    }
}

