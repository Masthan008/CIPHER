package com.cipher.media.ui.audio.equalizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.*

/**
 * Vertical band slider: bar chart style, primary color fill.
 * Range: -15dB to +15dB.
 */
@Composable
fun BandSlider(
    label: String,
    value: Float, // -15f to 15f
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Vertical slider track
        Box(
            modifier = Modifier
                .width(8.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(Corners.small))
                .background(CIPHERSurfaceVariant)
        ) {
            // Fill from bottom based on value
            val normalizedValue: Float = ((value + 15f) / 30f).coerceIn(0f, 1f)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(normalizedValue)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(Corners.small))
                    .background(CIPHERPrimary.copy(alpha = 0.8f))
            )
        }

        // Invisible slider for interaction
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = -15f..15f,
            modifier = Modifier
                .width(200.dp)
                .height(28.dp)
                .graphicsLayer { rotationZ = -90f },
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent,
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(Spacing.xs))

        // Label
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = CIPHEROnSurfaceVariant
        )
    }
}
