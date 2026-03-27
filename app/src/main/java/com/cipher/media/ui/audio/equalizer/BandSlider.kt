package com.cipher.media.ui.audio.equalizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.*

/**
 * Custom vertical slider for a single EQ frequency band.
 * Displays frequency label at bottom and dB value at top.
 */
@Composable
fun BandSlider(
    label: String,
    gainMb: Int,  // millibels -1500 to 1500
    onGainChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val minDb = -15f
    val maxDb = 15f
    val currentDb = gainMb / 100f
    val normalizedValue = (currentDb - minDb) / (maxDb - minDb)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // dB value
        Text(
            text = "%+.0f".format(currentDb),
            style = MaterialTheme.typography.labelSmall,
            color = if (gainMb != 0) CIPHERPrimary else CIPHEROnSurfaceVariant
        )

        // Vertical slider
        Box(
            modifier = Modifier
                .width(40.dp)
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Slider(
                value = normalizedValue,
                onValueChange = { newNorm ->
                    val newDb = minDb + newNorm * (maxDb - minDb)
                    onGainChange((newDb * 100).toInt())
                },
                colors = SliderDefaults.colors(
                    thumbColor = CIPHERPrimary,
                    activeTrackColor = CIPHERPrimary,
                    inactiveTrackColor = CIPHERSurfaceVariant
                ),
                modifier = Modifier
                    .requiredHeight(200.dp)
                    .requiredWidth(40.dp)
            )
        }

        // Frequency label
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = CIPHEROnSurfaceVariant
        )
    }
}
