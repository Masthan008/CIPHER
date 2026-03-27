package com.cipher.media.ui.video.enhancement.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SpeedSelector(
    currentSpeed: Float,
    isPro: Boolean,
    pitchCorrectionEnabled: Boolean,
    viewModel: VideoEnhancementViewModel,
    onDismiss: () -> Unit
) {
    val speeds = viewModel.getAvailableSpeeds()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = CIPHERBackground,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = { BottomSheetDefaults.DragHandle(color = CIPHEROnSurfaceVariant) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Playback Speed",
                    style = MaterialTheme.typography.titleLarge,
                    color = CIPHEROnSurface,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, "Close", tint = CIPHEROnSurface)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Speed chips in a flow
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                speeds.forEach { speed ->
                    val isSelected = currentSpeed == speed
                    val isProSpeed = speed < 0.5f || speed > 2.0f

                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setSpeed(speed) },
                        label = {
                            Text(
                                "${speed}x",
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CIPHERPrimary,
                            selectedLabelColor = CIPHEROnPrimary,
                            containerColor = if (isProSpeed && !isPro) CIPHERSurface.copy(alpha = 0.5f) else CIPHERSurface,
                            labelColor = if (isProSpeed && !isPro) CIPHERTextDisabled else CIPHEROnSurfaceVariant
                        ),
                        modifier = Modifier.height(40.dp)
                    )
                }
            }

            // Pitch correction toggle (Pro only)
            if (isPro) {
                Spacer(Modifier.height(20.dp))
                HorizontalDivider(color = CIPHERDivider)
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.MusicNote, null, tint = CIPHERPrimary, modifier = Modifier.size(20.dp))
                        Column {
                            Text("Pitch Correction", color = CIPHEROnSurface, style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "Keep original voice pitch at any speed",
                                color = CIPHEROnSurfaceVariant,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    Switch(
                        checked = pitchCorrectionEnabled,
                        onCheckedChange = { viewModel.togglePitchCorrection() },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = CIPHERPrimary,
                            checkedThumbColor = CIPHEROnPrimary
                        )
                    )
                }
            } else {
                Spacer(Modifier.height(12.dp))
                // Upgrade hint
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(ProGold.copy(alpha = 0.15f))
                        .padding(12.dp)
                ) {
                    Text(
                        "⚡ Upgrade to Pro for 0.25x–4.0x speeds with pitch correction",
                        color = ProGold,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
