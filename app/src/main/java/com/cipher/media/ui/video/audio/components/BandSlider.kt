package com.cipher.media.ui.video.audio.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.CIPHERPrimary
import com.cipher.media.ui.theme.CIPHEROnSurfaceVariant
import kotlin.math.roundToInt

@Composable
fun BandSlider(
    label: String,
    valueMillibels: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    // 15dB corresponds to 1500mB
    val maxMb = 1500f
    
    // Scale -1500 to 1500 into 0f to 1f for Slider
    val sliderValue = (valueMillibels + maxMb) / (maxMb * 2)

    Column(
        modifier = modifier.width(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Gain text (+5dB)
        val textVal = (valueMillibels / 100f).roundToInt()
        val textStr = if (textVal > 0) "+${textVal}dB" else "${textVal}dB"
        
        Text(
            text = textStr,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = if (enabled) CIPHEROnSurfaceVariant else CIPHEROnSurfaceVariant.copy(alpha = 0.5f),
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Vertical Slider mapping
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Slider(
                value = sliderValue,
                onValueChange = { 
                    val newMb = ((it * maxMb * 2) - maxMb).roundToInt()
                    onValueChange(newMb)
                },
                enabled = enabled,
                colors = SliderDefaults.colors(
                    thumbColor = if (enabled) CIPHERPrimary else Color.Gray,
                    activeTrackColor = if (enabled) CIPHERPrimary else Color.Gray,
                    inactiveTrackColor = if (enabled) CIPHERPrimary.copy(alpha = 0.3f) else Color.DarkGray
                ),
                modifier = Modifier
                    .graphicsLayer {
                        rotationZ = 270f
                        transformOrigin = TransformOrigin(0.5f, 0.5f)
                    }
                    .width(180.dp) // The "width" dictates the height when rotated
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Frequency label (e.g. 63Hz)
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
            color = if (enabled) CIPHEROnSurfaceVariant else CIPHEROnSurfaceVariant.copy(alpha = 0.5f),
            fontWeight = FontWeight.Bold
        )
    }
}
