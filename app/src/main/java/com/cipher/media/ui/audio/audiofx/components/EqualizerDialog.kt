package com.cipher.media.ui.audio.audiofx.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.data.model.audiofx.EQPreset
import com.cipher.media.data.model.audiofx.Tier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EqualizerDialog(
    onDismiss: () -> Unit,
    viewModel: com.cipher.media.ui.audio.audiofx.EqualizerViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()
    val userTier by viewModel.userTier.collectAsState()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp)),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Audio Effects", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                
                // Tier Badge Paywall Notice
                if (userTier == Tier.FREE) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Currently on Free Tier. Upgrade to Pro for 10-band EQ, 3D Virtualization, Reverb & more presets.",
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // Presets
                Text("Presets", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                PresetSelector(
                    presets = EQPreset.ALL,
                    selectedPreset = settings.selectedPreset,
                    userTier = userTier,
                    onPresetSelected = { viewModel.applyPreset(it) }
                )
                
                Spacer(modifier = Modifier.height(24.dp))

                // EQ Bands (Free limits to 3 bands, Pro gets 10 bands)
                val displayBands = if (userTier == Tier.FREE) listOf(1, 4, 8) else (0..9).toList()
                val labels = listOf("31", "63", "125", "250", "500", "1k", "2k", "4k", "8k", "16k")
                
                Text(
                    if (userTier == Tier.FREE) "3-Band Equalizer" else "10-Band Equalizer", 
                    style = MaterialTheme.typography.titleMedium, 
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    displayBands.forEach { bandIdx ->
                        val mappedLabel = if (userTier == Tier.FREE) {
                            when (bandIdx) { 1 -> "Bass"; 4 -> "Mid"; 8 -> "Treble"; else -> labels[bandIdx] }
                        } else labels[bandIdx]
                        
                        BandSlider(
                            label = mappedLabel,
                            value = settings.customBands[bandIdx].toFloat(),
                            onValueChange = { viewModel.setBandGain(bandIdx, it.toInt()) }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))

                // Effects
                Text("Effects", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                
                val maxBass = if (userTier == Tier.FREE) 500 else 1000
                BassBoostControl(
                    strength = settings.bassBoostStrength,
                    maxAllowed = maxBass,
                    onValueChange = { viewModel.setBassBoost(it) }
                )
                
                VirtualizerControl(
                    isEnabled = userTier != Tier.FREE,
                    strength = settings.virtualizerStrength,
                    onValueChange = { viewModel.setVirtualizer(it) }
                )
            }
        }
    }
}
