package com.cipher.media.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.CIPHERSurface
import com.cipher.media.ui.theme.CIPHERSurfaceBright
import com.cipher.media.ui.theme.CIPHERSurfaceVariant

/**
 * Shimmer loading skeleton for content placeholders.
 */
@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier,
    widthFraction: Float = 1f,
    height: Dp = 16.dp,
    cornerRadius: Dp = 8.dp
) {
    val shimmerColors = listOf(
        CIPHERSurface,
        CIPHERSurfaceBright,
        CIPHERSurface
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim.value - 200f, 0f),
        end = Offset(translateAnim.value, 0f)
    )

    Box(
        modifier = modifier
            .fillMaxWidth(widthFraction)
            .height(height)
            .clip(RoundedCornerShape(cornerRadius))
            .background(brush)
    )
}

/**
 * Shimmer loading card for grid/list items.
 */
@Composable
fun ShimmerCard(
    modifier: Modifier = Modifier,
    aspectRatio: Float = 1f
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(CIPHERSurfaceVariant)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ShimmerEffect(
            modifier = Modifier.aspectRatio(aspectRatio),
            height = 120.dp,
            cornerRadius = 8.dp
        )
        ShimmerEffect(widthFraction = 0.7f, height = 14.dp)
        ShimmerEffect(widthFraction = 0.4f, height = 10.dp)
    }
}

/**
 * Grid of shimmer cards for loading state.
 */
@Composable
fun ShimmerGrid(
    columns: Int = 2,
    itemCount: Int = 6,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        for (row in 0 until (itemCount + columns - 1) / columns) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (col in 0 until columns) {
                    val index = row * columns + col
                    if (index < itemCount) {
                        ShimmerCard(modifier = Modifier.weight(1f))
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
