package com.cipher.media.ui.video.enhancement.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.video.enhancement.SleepTimerAction
import com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SleepTimerDialog(
    isPro: Boolean,
    currentAction: SleepTimerAction,
    isTimerActive: Boolean,
    timerDisplay: String,
    viewModel: VideoEnhancementViewModel,
    onDismiss: () -> Unit
) {
    val presets = VideoEnhancementViewModel.FREE_SLEEP_PRESETS
    var customMinutes by remember { mutableIntStateOf(30) }

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
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Timer, null, tint = CIPHERPrimary)
                    Text("Sleep Timer", style = MaterialTheme.typography.titleLarge, color = CIPHEROnSurface, fontWeight = FontWeight.Bold)
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, "Close", tint = CIPHEROnSurface)
                }
            }

            // Active timer indicator
            if (isTimerActive) {
                Spacer(Modifier.height(12.dp))
                Surface(
                    color = CIPHERPrimary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Timer Active", color = CIPHERPrimary, fontWeight = FontWeight.Bold)
                            Text(timerDisplay, color = CIPHEROnSurface, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { viewModel.cancelSleepTimer() },
                            colors = ButtonDefaults.buttonColors(containerColor = CIPHERError)
                        ) {
                            Text("Cancel", color = CIPHEROnSurface)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
                return@Column
            }

            Spacer(Modifier.height(16.dp))

            // Timer action selector
            Text("When timer ends:", color = CIPHEROnSurfaceVariant, style = MaterialTheme.typography.bodySmall)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SleepTimerAction.values().forEach { action ->
                    FilterChip(
                        selected = currentAction == action,
                        onClick = { viewModel.setSleepTimerAction(action) },
                        label = { Text(action.label) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CIPHERPrimary,
                            selectedLabelColor = CIPHEROnPrimary,
                            containerColor = CIPHERSurface,
                            labelColor = CIPHEROnSurfaceVariant
                        )
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Preset buttons
            Text("Quick Presets", color = CIPHEROnSurface, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                presets.forEach { minutes ->
                    OutlinedButton(
                        onClick = { viewModel.startSleepTimer(minutes) },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = CIPHEROnSurface),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(CIPHEROutline)
                        )
                    ) {
                        Text("${minutes} min")
                    }
                }
            }

            // Custom timer (Pro only)
            Spacer(Modifier.height(20.dp))
            HorizontalDivider(color = CIPHERDivider)
            Spacer(Modifier.height(16.dp))

            if (isPro) {
                Text("Custom Timer", color = CIPHEROnSurface, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Slider(
                        value = customMinutes.toFloat(),
                        onValueChange = { customMinutes = it.toInt() },
                        valueRange = 1f..240f,
                        colors = SliderDefaults.colors(
                            thumbColor = CIPHERPrimary,
                            activeTrackColor = CIPHERPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "${customMinutes} min",
                        color = CIPHERPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = { viewModel.startSleepTimer(customMinutes) },
                    colors = ButtonDefaults.buttonColors(containerColor = CIPHERPrimary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start Timer", color = CIPHEROnPrimary, fontWeight = FontWeight.Bold)
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(ProGold.copy(alpha = 0.15f))
                        .padding(12.dp)
                ) {
                    Text(
                        "⚡ Upgrade to Pro for custom sleep timers (1–240 min)",
                        color = ProGold,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
