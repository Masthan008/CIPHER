package com.cipher.media.ui.video.enhancement.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel

/**
 * A-B Repeat controls: floating buttons + seek bar markers.
 * PRO ONLY feature.
 */
@Composable
fun ABRepeatControls(
    hasPointA: Boolean,
    hasPointB: Boolean,
    isLooping: Boolean,
    isPro: Boolean,
    viewModel: VideoEnhancementViewModel,
    modifier: Modifier = Modifier
) {
    if (!isPro) return  // Only show for Pro users

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black.copy(alpha = 0.75f))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Set A button
        IconButton(
            onClick = { viewModel.setPointA() },
            modifier = Modifier.size(36.dp)
        ) {
            Text(
                "A",
                color = if (hasPointA) CIPHERPrimary else Color.White.copy(alpha = 0.6f),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )
        }

        // Set B button
        IconButton(
            onClick = { viewModel.setPointB() },
            enabled = hasPointA,
            modifier = Modifier.size(36.dp)
        ) {
            Text(
                "B",
                color = if (hasPointB) CIPHERPrimary else Color.White.copy(alpha = if (hasPointA) 0.6f else 0.3f),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )
        }

        // Loop indicator
        AnimatedVisibility(visible = isLooping, enter = fadeIn(), exit = fadeOut()) {
            Icon(
                Icons.Default.Repeat,
                "Looping",
                tint = CIPHERTertiary,
                modifier = Modifier.size(18.dp)
            )
        }

        // Clear button
        if (hasPointA || hasPointB) {
            IconButton(
                onClick = { viewModel.clearABRepeat() },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    "Clear A-B",
                    tint = CIPHERError,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

/**
 * Visual markers rendered on the seek bar for A and B points.
 */
@Composable
fun ABRepeatMarkers(
    pointA: Long,
    pointB: Long,
    duration: Long,
    modifier: Modifier = Modifier
) {
    if (duration <= 0) return

    Box(modifier = modifier.fillMaxWidth()) {
        // Point A marker
        if (pointA >= 0) {
            val aFraction = pointA.toFloat() / duration
            Box(
                modifier = Modifier
                    .fillMaxWidth(aFraction)
                    .align(Alignment.CenterStart)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(width = 3.dp, height = 16.dp)
                        .background(CIPHERTertiary, RoundedCornerShape(1.dp))
                )
            }
        }

        // Point B marker
        if (pointB >= 0) {
            val bFraction = pointB.toFloat() / duration
            Box(
                modifier = Modifier
                    .fillMaxWidth(bFraction)
                    .align(Alignment.CenterStart)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(width = 3.dp, height = 16.dp)
                        .background(CIPHERError, RoundedCornerShape(1.dp))
                )
            }
        }

        // Shaded region between A and B
        if (pointA >= 0 && pointB > pointA) {
            val startFraction = pointA.toFloat() / duration
            val widthFraction = (pointB - pointA).toFloat() / duration
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .align(Alignment.Center)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(widthFraction)
                        .offset(x = (startFraction * 1000).dp) // Approximate offset
                        .background(CIPHERPrimary.copy(alpha = 0.3f), RoundedCornerShape(2.dp))
                )
            }
        }
    }
}
