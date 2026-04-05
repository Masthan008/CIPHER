package com.cipher.media.ui.audio.audiofx.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cipher.media.ui.theme.Spacing

@Composable
fun BassBoostControl(
    strength: Int, // 0-1000
    maxAllowed: Int,
    onValueChange: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(Spacing.sm)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Bass Boost", style = MaterialTheme.typography.titleMedium)
            Text("${(strength / 10)}%", style = MaterialTheme.typography.labelMedium)
        }
        Slider(
            value = strength.toFloat().coerceIn(0f, maxAllowed.toFloat()),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 0f..maxAllowed.toFloat(),
            enabled = maxAllowed > 0
        )
        if (maxAllowed < 1000) {
            Text(
                "Up to 100% available in Pro Tier",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun VirtualizerControl(
    isEnabled: Boolean,
    strength: Int, // 0-1000
    onValueChange: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(Spacing.sm)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Virtualizer (3D)", style = MaterialTheme.typography.titleMedium, color = if (isEnabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant)
            Text(if (isEnabled) "${(strength / 10)}%" else "PRO", style = MaterialTheme.typography.labelMedium)
        }
        Slider(
            value = strength.toFloat().coerceIn(0f, 1000f),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 0f..1000f,
            enabled = isEnabled
        )
        if (!isEnabled) {
            Text(
                "Unlock 3D Virtualization in Pro Tier",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
