package com.cipher.media.ui.video.audiofx.components

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.billing.ProVideoFeature
import com.cipher.media.billing.Tier
import com.cipher.media.billing.PaywallDialog
import com.cipher.media.ui.theme.CIPHERPrimary
import com.cipher.media.ui.video.audio.VideoAudioEffectsManager

/**
 * 10-Band Equalizer Dialog for Pro Video Features
 */
@Composable
fun TenBandEqualizerDialog(
    onDismiss: () -> Unit,
    userTier: Tier,
    onShowPaywall: () -> Unit,
    viewModel: com.cipher.media.ui.audio.audiofx.EqualizerViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()
    val userTierState by viewModel.userTier.collectAsState()

    if (userTier == Tier.FREE) {
        PaywallDialog(
            feature = ProVideoFeature.TEN_BAND_EQ,
            onUpgrade = onShowPaywall,
            onDismiss = onDismiss
        )
        return
    }

    Dialog(onDismissRequest = onDismiss) {
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
                    Text("10-Band Equalizer", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Presets
                Text("Presets", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    val presets = listOf(
                                "Flat" to listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f),
                                "Bass" to listOf(6f, 6f, 4f, 2f, 0f, 0f, 0f, 0f, 2f, 4f),
                                "Vocal" to listOf(-2f, -2f, 0f, 3f, 6f, 6f, 3f, 0f, -2f, -2f),
                                "Rock" to listOf(4f, 3f, 2f, 0f, -2f, -2f, 0f, 2f, 3f, 4f)
                            ).forEach { (name, preset) ->
                        Button(
                            onClick = { 
                                preset.forEachIndexed { index, gain ->
                                    viewModel.setBandGain(index, gain.toInt())
                                }
                            }
                        ) {
                            Text(name)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 10 sliders
                val frequencies = listOf("31", "63", "125", "250", "500", "1K", "2K", "4K", "8K", "16K")
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    frequencies.forEachIndexed { index, freq ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(freq, fontSize = MaterialTheme.typography.bodySmall.fontSize)
                            
                            // Vertical slider placeholder
                            Slider(
                                value = settings.customBands.getOrElse(index) { 0 }.toFloat(),
                                onValueChange = { viewModel.setBandGain(index, it.toInt()) },
                                valueRange = -15f..15f,
                                modifier = Modifier.weight(1f)
                            )
                            
                            Text(
                                "${settings.customBands.getOrElse(index) { 0 }}",
                                fontSize = MaterialTheme.typography.bodySmall.fontSize
                            )
                        }
                    }
                }
            }
        }
    }
}
