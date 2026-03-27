package com.cipher.media.ui.audio.equalizer

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.ui.components.CIPHERIconButton
import com.cipher.media.ui.theme.*

val EQ_BANDS = listOf("31", "63", "125", "250", "500", "1k", "2k", "4k", "8k", "16k")
val EQ_PRESETS = listOf("Flat", "Bass Boost", "Vocal", "Treble", "Rock", "Pop", "Jazz", "Classical")

/**
 * 10-band equalizer: vertical sliders (-15dB to +15dB),
 * preset chips, bass boost/virtualizer toggles.
 */
@Composable
fun EqualizerScreen(
    onBack: () -> Unit,
    viewModel: EqualizerViewModel = hiltViewModel()
) {
    val bandGains by viewModel.bandGains.collectAsState()
    val selectedPreset by viewModel.selectedPresetName.collectAsState()
    val bassBoost by viewModel.bassBoostStrength.collectAsState()
    val virtualizer by viewModel.virtualizerStrength.collectAsState()

    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = Spacing.sm, vertical = Spacing.sm),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CIPHERIconButton(icon = Icons.Default.ArrowBack, onClick = onBack)
                Text("Equalizer", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = Spacing.md)
        ) {
            // Preset chips
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                EQ_PRESETS.forEach { preset ->
                    FilterChip(
                        selected = selectedPreset == preset,
                        onClick = {
                            val presetObj = com.cipher.media.data.model.EqualizerPreset.ALL_PRESETS
                                .find { it.name == preset }
                                ?: com.cipher.media.data.model.EqualizerPreset.FLAT
                            viewModel.selectPreset(presetObj)
                        },
                        label = { Text(preset, style = MaterialTheme.typography.labelSmall) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CIPHERPrimary,
                            selectedLabelColor = CIPHEROnPrimary,
                            containerColor = CIPHERSurface,
                            labelColor = CIPHEROnSurfaceVariant
                        )
                    )
                }
            }

            Spacer(Modifier.height(Spacing.lg))

            // 10-band sliders
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                bandGains.forEachIndexed { index: Int, gain: Int ->
                    BandSlider(
                        label = EQ_BANDS.getOrElse(index) { "" },
                        value = gain.toFloat(),
                        onValueChange = { viewModel.setBandGain(index, it.toInt()) }
                    )
                }
            }

            // dB labels
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = Spacing.xs),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("+15dB", style = MaterialTheme.typography.labelSmall, color = CIPHEROnSurfaceVariant)
                Text("0dB", style = MaterialTheme.typography.labelSmall, color = CIPHEROnSurfaceVariant)
                Text("-15dB", style = MaterialTheme.typography.labelSmall, color = CIPHEROnSurfaceVariant)
            }

            Spacer(Modifier.height(Spacing.xl))

            // Toggles
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CIPHERSurface),
                shape = RoundedCornerShape(Corners.large)
            ) {
                Column(modifier = Modifier.padding(Spacing.md)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Bass Boost", color = CIPHEROnSurface, style = MaterialTheme.typography.bodyLarge)
                        Switch(
                            checked = bassBoost > 0,
                            onCheckedChange = { viewModel.setBassBoost(if (it) 700 else 0) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = CIPHERPrimary,
                                checkedTrackColor = CIPHERPrimary.copy(alpha = 0.3f)
                            )
                        )
                    }

                    HorizontalDivider(color = CIPHERDivider)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Virtualizer", color = CIPHEROnSurface, style = MaterialTheme.typography.bodyLarge)
                        Switch(
                            checked = virtualizer > 0,
                            onCheckedChange = { viewModel.setVirtualizer(if (it) 700 else 0) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = CIPHERPrimary,
                                checkedTrackColor = CIPHERPrimary.copy(alpha = 0.3f)
                            )
                        )
                    }
                }
            }
        }
    }
}
