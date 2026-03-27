package com.cipher.media.ui.video.streaming.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*

/**
 * Bottom sheet showing discovered Cast/DLNA devices
 * for the user to select a casting target.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastDeviceSelector(
    isCasting: Boolean,
    castDeviceName: String?,
    hasCastDevices: Boolean,
    onCastRequested: () -> Unit,
    onStopCasting: () -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = CIPHERSurface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.lg, vertical = Spacing.md)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Cast, null, tint = CIPHERPrimary, modifier = Modifier.size(28.dp))
                Spacer(Modifier.width(Spacing.sm))
                Text("Cast To Device", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
            }

            Spacer(Modifier.height(Spacing.lg))

            if (isCasting) {
                // Currently casting — show connected device
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CIPHERPrimary.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.md),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CastConnected, null, tint = CIPHERPrimary, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.width(Spacing.md))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(castDeviceName ?: "Connected Device", fontWeight = FontWeight.SemiBold, color = CIPHEROnSurface)
                            Text("Currently casting", color = CIPHERPrimary, fontSize = 13.sp)
                        }
                    }
                }

                Spacer(Modifier.height(Spacing.md))

                Button(
                    onClick = onStopCasting,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = CIPHERError),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.StopCircle, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Stop Casting")
                }
            } else if (hasCastDevices) {
                // Devices available — show cast button
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCastRequested() },
                    colors = CardDefaults.cardColors(containerColor = CIPHERBackground),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Spacing.md),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Tv, null, tint = CIPHERPrimary, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.width(Spacing.md))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Chromecast Devices", fontWeight = FontWeight.SemiBold, color = CIPHEROnSurface)
                            Text("Tap to connect", color = CIPHEROnSurfaceVariant, fontSize = 13.sp)
                        }
                        Icon(Icons.Default.ChevronRight, null, tint = CIPHEROnSurfaceVariant)
                    }
                }

                Spacer(Modifier.height(Spacing.sm))

                Text(
                    "Make sure your Chromecast device is on the same Wi-Fi network",
                    color = CIPHEROnSurfaceVariant,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = Spacing.sm)
                )
            } else {
                // No devices found
                Column(
                    modifier = Modifier.fillMaxWidth().padding(Spacing.xl),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.SearchOff, null, tint = CIPHEROnSurfaceVariant, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(Spacing.md))
                    Text("No casting devices found", fontWeight = FontWeight.SemiBold, color = CIPHEROnSurface)
                    Spacer(Modifier.height(Spacing.xs))
                    Text("Ensure your device is on the same Wi-Fi", color = CIPHEROnSurfaceVariant, fontSize = 13.sp)
                }
            }

            Spacer(Modifier.height(Spacing.xl))
        }
    }
}
