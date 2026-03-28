package com.cipher.media.ui.audio.audiofx.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.Spacing

/**
 * Vertical slider representing single frequency band (-15dB to +15dB).
 */
@Composable
fun BandSlider(
    label: String,
    value: Float, // -15f to +15f
    onValueChange: (Float) -> Unit,
    isEnabled: Boolean = true,
    isProFeature: Boolean = false // If showing generic badge overlay in future
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(48.dp)
    ) {
        Text(
            text = "${if (value > 0) "+" else ""}${value.toInt()}dB",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(Spacing.sm))
        
        Box(
            modifier = Modifier.height(180.dp).width(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Slider(
                value = value,
                onValueChange = { onValueChange(it) },
                valueRange = -15f..15f,
                steps = 29,
                enabled = isEnabled,
                modifier = Modifier
                    .graphicsLayer {
                        rotationZ = -90f
                        transformOrigin = androidx.compose.ui.graphics.TransformOrigin(0.5f, 0.5f)
                    }
                    .width(180.dp)
                    .height(30.dp)
            )
        }
        
        Spacer(Modifier.height(Spacing.sm))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
