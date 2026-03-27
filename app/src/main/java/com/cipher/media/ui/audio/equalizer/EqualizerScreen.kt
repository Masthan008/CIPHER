package com.cipher.media.ui.audio.equalizer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.data.model.EqualizerPreset
import com.cipher.media.ui.theme.*

/**
 * 10-band graphic equalizer screen with preset chips,
 * bass boost slider, and virtualizer slider.
 */
@Composable
fun EqualizerScreen(
    onBack: () -> Unit,
    viewModel: EqualizerViewModel = hiltViewModel()
) {
    val bandGains by viewModel.bandGains.collectAsState()
    val presetName by viewModel.selectedPresetName.collectAsState()
    val eqEnabled by viewModel.isEqEnabled.collectAsState()
    val bassBoost by viewModel.bassBoostStrength.collectAsState()
    val virtualizer by viewModel.virtualizerStrength.collectAsState()

    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = CIPHEROnSurface
                    )
                }
                Text(
                    text = "Equalizer",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = CIPHEROnSurface,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = eqEnabled,
                    onCheckedChange = { viewModel.setEqEnabled(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = CIPHERPrimary,
                        checkedTrackColor = CIPHERPrimary.copy(alpha = 0.4f),
                        uncheckedThumbColor = CIPHEROnSurfaceVariant,
                        uncheckedTrackColor = CIPHERSurfaceVariant
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Preset chips
            Text(
                text = "PRESETS",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                ),
                color = CIPHEROnSurfaceVariant
            )
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EqualizerPreset.ALL_PRESETS.forEach { preset ->
                    val isSelected = presetName == preset.name
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (isSelected) CIPHERPrimary else CIPHERSurfaceVariant
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) CIPHERPrimary else CIPHEROutlineVariant,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { viewModel.selectPreset(preset) }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = preset.name,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = if (isSelected) CIPHEROnPrimary else CIPHEROnSurface
                        )
                    }
                }
            }

            // 10-Band Sliders
            Text(
                text = "FREQUENCY BANDS",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                ),
                color = CIPHEROnSurfaceVariant
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(CIPHERSurface)
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                bandGains.forEachIndexed { index, gain ->
                    BandSlider(
                        label = EqualizerPreset.BAND_LABELS[index],
                        gainMb = gain,
                        onGainChange = { newGain ->
                            viewModel.setBandGain(index, newGain)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Bass Boost
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "BASS BOOST",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = CIPHEROnSurfaceVariant
                )
                Slider(
                    value = bassBoost / 1000f,
                    onValueChange = { viewModel.setBassBoost((it * 1000).toInt()) },
                    colors = SliderDefaults.colors(
                        thumbColor = CIPHERPrimary,
                        activeTrackColor = CIPHERPrimary,
                        inactiveTrackColor = CIPHERSurfaceVariant
                    )
                )
            }

            // Virtualizer
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "VIRTUALIZER",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = CIPHEROnSurfaceVariant
                )
                Slider(
                    value = virtualizer / 1000f,
                    onValueChange = { viewModel.setVirtualizer((it * 1000).toInt()) },
                    colors = SliderDefaults.colors(
                        thumbColor = CIPHERPrimary,
                        activeTrackColor = CIPHERPrimary,
                        inactiveTrackColor = CIPHERSurfaceVariant
                    )
                )
            }
        }
    }
}
