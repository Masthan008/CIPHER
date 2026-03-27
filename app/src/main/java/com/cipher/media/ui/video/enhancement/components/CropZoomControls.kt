package com.cipher.media.ui.video.enhancement.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.video.enhancement.CropMode
import com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CropZoomDialog(
    isPro: Boolean,
    currentZoom: Float,
    currentCropMode: CropMode,
    viewModel: VideoEnhancementViewModel,
    onDismiss: () -> Unit
) {
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
                    "Crop & Zoom",
                    style = MaterialTheme.typography.titleLarge,
                    color = CIPHEROnSurface,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, "Close", tint = CIPHEROnSurface)
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Crop Mode chips ──
            Text("Crop Mode", color = CIPHEROnSurface, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CropMode.values().forEach { mode ->
                    val isSelected = currentCropMode == mode
                    val isLocked = mode.proOnly && !isPro

                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            if (!isLocked) viewModel.setCropMode(mode)
                        },
                        label = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(mode.label)
                                if (isLocked) {
                                    Icon(Icons.Default.Lock, null, modifier = Modifier.size(12.dp))
                                }
                            }
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CIPHERPrimary,
                            selectedLabelColor = CIPHEROnPrimary,
                            containerColor = if (isLocked) CIPHERSurface.copy(alpha = 0.4f) else CIPHERSurface,
                            labelColor = if (isLocked) CIPHERTextDisabled else CIPHEROnSurfaceVariant
                        )
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Zoom Slider (Pro only) ──
            Text("Zoom", color = CIPHEROnSurface, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            if (isPro) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(Icons.Default.ZoomOut, null, tint = CIPHEROnSurfaceVariant)
                    Slider(
                        value = currentZoom,
                        onValueChange = { viewModel.setZoomLevel(it) },
                        valueRange = 1f..3f,
                        steps = 7,
                        colors = SliderDefaults.colors(
                            thumbColor = CIPHERPrimary,
                            activeTrackColor = CIPHERPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Icon(Icons.Default.ZoomIn, null, tint = CIPHEROnSurfaceVariant)
                    Text(
                        "${String.format("%.1f", currentZoom)}x",
                        color = CIPHERPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(8.dp))

                // Reset button
                if (currentZoom > 1f) {
                    OutlinedButton(
                        onClick = { viewModel.resetZoomPan() },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = CIPHEROnSurface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.RestartAlt, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Reset Zoom & Pan")
                    }
                }

                Spacer(Modifier.height(8.dp))
                Text(
                    "Tip: When zoomed in, drag on the video to pan around",
                    color = CIPHEROnSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(ProGold.copy(alpha = 0.15f))
                        .padding(12.dp)
                ) {
                    Text(
                        "⚡ Upgrade to Pro for zoom (1x–3x) with pan navigation",
                        color = ProGold,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
