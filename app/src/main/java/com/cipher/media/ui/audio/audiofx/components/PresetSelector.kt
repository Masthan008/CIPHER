package com.cipher.media.ui.audio.audiofx.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cipher.media.data.model.audiofx.EQPreset
import com.cipher.media.data.model.audiofx.Tier

/**
 * Horizontal scrollable row of preset chips.
 */
@Composable
fun PresetSelector(
    presets: List<EQPreset>,
    selectedPreset: EQPreset,
    userTier: Tier,
    onPresetSelected: (EQPreset) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(com.cipher.media.ui.theme.Spacing.sm)
    ) {
        presets.forEach { preset ->
            val isProLocked = userTier == Tier.FREE && !EQPreset.FREE_TIER.contains(preset)
            
            FilterChip(
                selected = selectedPreset == preset,
                onClick = { if (!isProLocked) onPresetSelected(preset) },
                label = { Text(preset.name, style = MaterialTheme.typography.labelMedium) },
                leadingIcon = if (isProLocked) {
                    { Icon(Icons.Default.Lock, contentDescription = "Locked Pro feature", tint = MaterialTheme.colorScheme.error) }
                } else null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}
