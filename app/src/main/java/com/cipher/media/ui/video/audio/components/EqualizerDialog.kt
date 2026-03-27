package com.cipher.media.ui.video.audio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.video.audio.ReverbRoom
import com.cipher.media.ui.video.audio.VideoAudioUiState
import com.cipher.media.ui.video.audio.VideoEqualizerViewModel
import com.cipher.media.ui.video.audio.model.EQPreset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EqualizerDialog(
    uiState: VideoAudioUiState,
    viewModel: VideoEqualizerViewModel,
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
                .padding(horizontal = Spacing.md)
                .padding(bottom = Spacing.xl)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Audio & Effects",
                    style = MaterialTheme.typography.titleLarge,
                    color = CIPHEROnSurface,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = CIPHEROnSurface)
                }
            }
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            // Audio Track Selector
            if (uiState.availableAudioTracks.isNotEmpty()) {
                AudioInfoDisplay(
                    tracks = uiState.availableAudioTracks,
                    onTrackSelected = { viewModel.selectAudioTrack(it) },
                    isHiRes = uiState.isHiRes,
                    isProUser = uiState.isPro,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(Spacing.lg))
            }
            
            // Tier Logic for EQ
            if (uiState.isPro) {
                ProEqualizerSection(uiState, viewModel)
            } else {
                FreeEqualizerSection(uiState, viewModel)
            }
            
            Spacer(modifier = Modifier.height(Spacing.xl))
            
            // Common / Tiered Effects (BassBoost & Loudness)
            EffectsSection(uiState, viewModel)
            
            // Pro Only Effects (Virtualizer, Reverb)
            if (uiState.isPro) {
                Spacer(modifier = Modifier.height(Spacing.lg))
                ProEffectsSection(uiState, viewModel)
            }
            
            Spacer(modifier = Modifier.height(Spacing.xl))
        }
    }
}

@Composable
private fun FreeEqualizerSection(
    uiState: VideoAudioUiState,
    viewModel: VideoEqualizerViewModel
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("3-Band Equalizer", style = MaterialTheme.typography.titleMedium, color = CIPHEROnSurface, fontWeight = FontWeight.Bold)
            UpgradeBadge()
        }
        
        Spacer(Modifier.height(Spacing.md))
        
        // 3-Band simplified UI (maps to 10 band engine)
        // We pick 3 representative gains, index 1 (low), 4 (mid), 8 (high)
        val lowGain = uiState.bandGains.getOrElse(1) { 0 }
        val midGain = uiState.bandGains.getOrElse(4) { 0 }
        val highGain = uiState.bandGains.getOrElse(8) { 0 }
        
        Row(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            BandSlider("LOW", lowGain, { viewModel.setFreeTier3Band(it, midGain, highGain) })
            BandSlider("MID", midGain, { viewModel.setFreeTier3Band(lowGain, it, highGain) })
            BandSlider("HIGH", highGain, { viewModel.setFreeTier3Band(lowGain, midGain, it) })
        }
    }
}

@Composable
private fun ProEqualizerSection(
    uiState: VideoAudioUiState,
    viewModel: VideoEqualizerViewModel
) {
    val labels = listOf("31", "63", "125", "250", "500", "1k", "2k", "4k", "8k", "16k")
    
    Column {
        Text("10-Band Equalizer", style = MaterialTheme.typography.titleMedium, color = CIPHEROnSurface, fontWeight = FontWeight.Bold)
        
        Spacer(Modifier.height(Spacing.sm))
        
        PresetSelector(
            presets = EQPreset.ALL_PRESETS,
            selectedPreset = uiState.selectedPreset,
            onPresetSelected = { viewModel.selectPreset(it) }
        )
        
        Spacer(Modifier.height(Spacing.md))
        
        Row(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            uiState.bandGains.forEachIndexed { index, gain ->
                BandSlider(
                    label = labels.getOrElse(index) { "" },
                    valueMillibels = gain,
                    onValueChange = { viewModel.setBandGain(index, it) }
                )
            }
        }
    }
}

@Composable
private fun EffectsSection(
    uiState: VideoAudioUiState,
    viewModel: VideoEqualizerViewModel
) {
    Surface(
        color = CIPHERSurface,
        shape = RoundedCornerShape(Corners.large),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(Spacing.md)) {
            // Bass Boost
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Bass Boost", color = CIPHEROnSurface, style = MaterialTheme.typography.bodyLarge)
                    if (!uiState.isPro) {
                        Text("Capped at 50% for Free Tier", color = CIPHEROnSurfaceVariant, style = MaterialTheme.typography.bodySmall)
                    }
                }
                Text("${(uiState.bassBoostStrength / 10f).roundToInt()}%", color = CIPHERPrimary)
            }
            Slider(
                value = uiState.bassBoostStrength.toFloat(),
                onValueChange = { viewModel.setBassBoost(it.roundToInt()) },
                valueRange = 0f..1000f,
                colors = SliderDefaults.colors(thumbColor = CIPHERPrimary, activeTrackColor = CIPHERPrimary)
            )
            
            Spacer(Modifier.height(Spacing.md))
            HorizontalDivider(color = CIPHERDivider)
            Spacer(Modifier.height(Spacing.md))
            
            // Loudness Enhancer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Loudness Enhancer", color = CIPHEROnSurface, style = MaterialTheme.typography.bodyLarge)
                Text("${uiState.loudnessEnhancerGain / 100} dB", color = CIPHERPrimary)
            }
            Slider(
                value = uiState.loudnessEnhancerGain.toFloat(),
                onValueChange = { viewModel.setLoudnessEnhancer(it.roundToInt()) },
                valueRange = 0f..1500f, // Up to +15dB boost
                colors = SliderDefaults.colors(thumbColor = CIPHERPrimary, activeTrackColor = CIPHERPrimary)
            )
        }
    }
}

@Composable
private fun ProEffectsSection(
    uiState: VideoAudioUiState,
    viewModel: VideoEqualizerViewModel
) {
    Surface(
        color = CIPHERPrimary.copy(alpha = 0.05f),
        shape = RoundedCornerShape(Corners.large),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(Spacing.md)) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Star, contentDescription = null, tint = ProGold, modifier = Modifier.size(16.dp))
                Text("PRO EFFECTS", color = ProGold, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            }
            
            Spacer(Modifier.height(Spacing.md))
            
            // Virtualizer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("3D Virtualizer", color = CIPHEROnSurface, style = MaterialTheme.typography.bodyLarge)
                Text("${(uiState.virtualizerStrength / 10f).roundToInt()}%", color = CIPHERPrimary)
            }
            Slider(
                value = uiState.virtualizerStrength.toFloat(),
                onValueChange = { viewModel.setVirtualizer(it.roundToInt()) },
                valueRange = 0f..1000f,
                colors = SliderDefaults.colors(thumbColor = CIPHERPrimary, activeTrackColor = CIPHERPrimary)
            )
            
            Spacer(Modifier.height(Spacing.md))
            HorizontalDivider(color = CIPHERDivider)
            Spacer(Modifier.height(Spacing.md))
            
            // Reverb
            Text("Reverb Environment", color = CIPHEROnSurface, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(Spacing.sm))
            
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                ReverbRoom.values().forEach { room ->
                    FilterChip(
                        selected = uiState.reverbPreset == room,
                        onClick = { viewModel.setReverb(room) },
                        label = { Text(room.label, style = MaterialTheme.typography.labelSmall) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CIPHERPrimary,
                            selectedLabelColor = CIPHEROnPrimary,
                            containerColor = CIPHERPrimary.copy(alpha = 0.1f),
                            labelColor = CIPHEROnSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun UpgradeBadge() {
    Box(
        modifier = Modifier
            .background(com.cipher.media.ui.theme.ProGold.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Star, contentDescription = null, tint = com.cipher.media.ui.theme.ProGold, modifier = Modifier.size(12.dp))
            Spacer(Modifier.width(4.dp))
            Text("PRO FOR 10-BAND EQ", style = MaterialTheme.typography.labelSmall, color = com.cipher.media.ui.theme.ProGold, fontWeight = FontWeight.Bold)
        }
    }
}
