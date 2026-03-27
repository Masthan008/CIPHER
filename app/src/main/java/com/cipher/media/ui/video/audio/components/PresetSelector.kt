package com.cipher.media.ui.video.audio.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.video.audio.model.EQPreset
import com.cipher.media.ui.theme.*

@Composable
fun PresetSelector(
    presets: List<EQPreset>,
    selectedPreset: EQPreset,
    onPresetSelected: (EQPreset) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        presets.forEach { preset ->
            // Mark as selected either if it's the exact preset or if "Custom" matches an underlying target
            val isSelected = selectedPreset.name == preset.name

            FilterChip(
                selected = isSelected,
                onClick = { if (enabled) onPresetSelected(preset) },
                label = { Text(preset.name, style = MaterialTheme.typography.labelMedium) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = if (enabled) CIPHERPrimary else Color.Gray,
                    selectedLabelColor = CIPHEROnPrimary,
                    containerColor = CIPHERSurface,
                    labelColor = CIPHEROnSurfaceVariant,
                    disabledContainerColor = CIPHERSurface.copy(alpha = 0.5f),
                    disabledLabelColor = CIPHEROnSurfaceVariant.copy(alpha = 0.5f)
                ),
                enabled = enabled
            )
        }
    }
}
