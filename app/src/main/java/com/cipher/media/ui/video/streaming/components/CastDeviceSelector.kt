package com.cipher.media.ui.video.streaming.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.video.streaming.dlna.DLNADevice

/**
 * Bottom sheet showing discovered Cast + DLNA devices
 * for the user to select a casting target.
 * PRO FEATURE: DLNA devices shown to Pro/Power users.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastDeviceSelector(
    isCasting: Boolean,
    castDeviceName: String?,
    hasCastDevices: Boolean,
    dlnaDevices: List<DLNADevice> = emptyList(),
    isDLNACasting: Boolean = false,
    dlnaCastDeviceName: String? = null,
    isPro: Boolean = false,
    onCastRequested: () -> Unit,
    onDLNADeviceSelected: (DLNADevice) -> Unit = {},
    onStopCasting: () -> Unit,
    onStopDLNA: () -> Unit = {},
    onRefreshDLNA: () -> Unit = {},
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

            // ═════════════════════════════════════════
            //  Active casting status (Chromecast or DLNA)
            // ═════════════════════════════════════════
            if (isCasting || isDLNACasting) {
                val deviceName = if (isCasting) castDeviceName else dlnaCastDeviceName
                val castType = if (isCasting) "Chromecast" else "DLNA"

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CIPHERPrimary.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(Spacing.md),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.CastConnected, null, tint = CIPHERPrimary, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.width(Spacing.md))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(deviceName ?: "Connected Device", fontWeight = FontWeight.SemiBold, color = CIPHEROnSurface)
                            Text("$castType • Currently casting", color = CIPHERPrimary, fontSize = 13.sp)
                        }
                    }
                }

                Spacer(Modifier.height(Spacing.md))

                Button(
                    onClick = { if (isCasting) onStopCasting() else onStopDLNA() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = CIPHERError),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.StopCircle, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Stop Casting")
                }

                Spacer(Modifier.height(Spacing.md))
                HorizontalDivider(color = CIPHERDivider)
                Spacer(Modifier.height(Spacing.md))
            }

            // ═════════════════════════════════════════
            //  Chromecast section
            // ═════════════════════════════════════════
            if (!isCasting && !isDLNACasting) {
                if (hasCastDevices) {
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { onCastRequested() },
                        colors = CardDefaults.cardColors(containerColor = CIPHERBackground),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(Spacing.md),
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
                }

                // ═════════════════════════════════════════
                //  DLNA / UPnP section (PRO FEATURE)
                // ═════════════════════════════════════════
                Spacer(Modifier.height(Spacing.md))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.SettingsInputAntenna, null, tint = CIPHERPrimary, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("DLNA / UPnP Devices", fontWeight = FontWeight.Bold, color = CIPHEROnSurface, fontSize = 15.sp)
                    Spacer(Modifier.weight(1f))
                    if (!isPro) {
                        Text("PRO", color = CIPHERPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(CIPHERPrimary.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    } else {
                        IconButton(onClick = onRefreshDLNA, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.Refresh, null, tint = CIPHEROnSurfaceVariant, modifier = Modifier.size(18.dp))
                        }
                    }
                }

                Spacer(Modifier.height(Spacing.sm))

                if (!isPro) {
                    // Paywall message
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = CIPHERPrimaryContainer),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(Spacing.md),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Lock, null, tint = CIPHERPrimary, modifier = Modifier.size(24.dp))
                            Spacer(Modifier.width(Spacing.sm))
                            Column {
                                Text("Cast to Smart TVs, speakers & more", color = CIPHEROnSurface, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                Text("Upgrade to Pro to unlock DLNA casting", color = CIPHEROnSurfaceVariant, fontSize = 11.sp)
                            }
                        }
                    }
                } else if (dlnaDevices.isEmpty()) {
                    // No DLNA devices found
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(vertical = Spacing.lg),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = CIPHERPrimary,
                            strokeWidth = 2.dp
                        )
                        Spacer(Modifier.height(Spacing.sm))
                        Text("Scanning local network…", color = CIPHEROnSurfaceVariant, fontSize = 13.sp)
                        Spacer(Modifier.height(4.dp))
                        Text("Ensure devices are on the same Wi-Fi", color = CIPHEROnSurfaceVariant.copy(alpha = 0.5f), fontSize = 11.sp)
                    }
                } else {
                    // DLNA device list
                    LazyColumn(modifier = Modifier.heightIn(max = 250.dp)) {
                        items(dlnaDevices) { device ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { onDLNADeviceSelected(device) },
                                colors = CardDefaults.cardColors(containerColor = CIPHERBackground),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(CIPHERPrimary.copy(alpha = 0.1f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.Tv, null, tint = CIPHERPrimary, modifier = Modifier.size(20.dp))
                                    }
                                    Spacer(Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(device.name, color = CIPHEROnSurface, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                                        Text(
                                            "${device.discoveryType} • ${device.host.hostAddress}",
                                            color = CIPHEROnSurfaceVariant,
                                            fontSize = 11.sp
                                        )
                                    }
                                    Icon(Icons.Default.PlayCircle, null, tint = CIPHERPrimary, modifier = Modifier.size(24.dp))
                                }
                            }
                        }
                    }
                }

                // No Chromecast & no DLNA
                if (!hasCastDevices && dlnaDevices.isEmpty() && !isPro) {
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
            }

            Spacer(Modifier.height(Spacing.xl))
        }
    }
}
