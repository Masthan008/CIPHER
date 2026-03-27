package com.cipher.media.ui.stealth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
 * Stealth setup: toggle cards for calculator mode, secret code, decoy PIN,
 * intruder detection, self-destruct.
 */
@Composable
fun StealthSetupScreen(
    onBack: () -> Unit,
    viewModel: StealthViewModel = hiltViewModel()
) {
    // Read plain properties as state
    var calcMode by remember { mutableStateOf(viewModel.isStealthEnabled) }
    var intruderDetection by remember { mutableStateOf(viewModel.isIntruderDetectionEnabled) }
    var selfDestruct by remember { mutableStateOf(viewModel.isSelfDestructEnabled) }

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
                Text("Stealth Settings", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
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
                description = "Type a secret code in calculator to open vault (${viewModel.secretCode})",
                checked = calcMode,
                onCheckedChange = { }
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

            StealthToggleCard(
                icon = Icons.Default.DeleteForever,
                title = "Self-Destruct",
                description = "Wipe vault data after too many failed attempts",
                checked = selfDestruct,
                onCheckedChange = {
                    selfDestruct = it
                    viewModel.setSelfDestruct(it)
                },
                tint = CIPHERError
            )
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
    tint: Color = CIPHERPrimary
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CIPHERSurface),
        shape = RoundedCornerShape(Corners.large)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = tint, modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(Spacing.md))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = CIPHEROnSurface)
                Text(description, style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurfaceVariant)
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = tint,
                    checkedTrackColor = tint.copy(alpha = 0.3f)
                )
            )
        }
    }
}
