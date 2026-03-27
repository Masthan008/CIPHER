package com.cipher.media.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.*

/**
 * OLED-optimized shimmer using surface gradient.
 */
@Composable
fun ShimmerEffect(
    modifier: Modifier = Modifier,
    content: @Composable (Brush) -> Unit
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateX by transition.animateFloat(
        initialValue = -300f,
        targetValue = 900f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "shimmer_x"
    )

    // Guard: only create gradient when translate values produce valid coordinates
    val safeStart = translateX.coerceIn(-1000f, 2000f)
    val safeEnd = (translateX + 300f).coerceIn(-700f, 2300f)

    val shimmerBrush = if (safeStart < safeEnd) {
        Brush.linearGradient(
            colors = listOf(
                CIPHERSurface,
                CIPHERSurfaceVariant,
                CIPHERSurface
            ),
            start = Offset(safeStart, 0f),
            end = Offset(safeEnd, 0f)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(CIPHERSurface, CIPHERSurface)
        )
    }

    content(shimmerBrush)
}

@Composable
fun ShimmerBar(
    width: Dp = 200.dp,
    height: Dp = 16.dp,
    modifier: Modifier = Modifier
) {
    ShimmerEffect {
        Box(
            modifier = modifier
                .width(width)
                .height(height)
                .clip(RoundedCornerShape(Corners.small))
                .background(it)
        )
    }
}

@Composable
fun ShimmerCard(modifier: Modifier = Modifier) {
    ShimmerEffect { brush ->
        Column(modifier = modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(topStart = Corners.medium, topEnd = Corners.medium))
                    .background(brush)
            )
            Column(modifier = Modifier.padding(Spacing.cardPadding)) {
                Box(
                    Modifier
                        .fillMaxWidth(0.7f)
                        .height(14.dp)
                        .clip(RoundedCornerShape(Corners.small))
                        .background(brush)
                )
                Spacer(Modifier.height(Spacing.sm))
                Box(
                    Modifier
                        .fillMaxWidth(0.4f)
                        .height(12.dp)
                        .clip(RoundedCornerShape(Corners.small))
                        .background(brush)
                )
            }
        }
    }
}

@Composable
fun ShimmerGrid(
    columns: Int = 2,
    count: Int = 6,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(Spacing.screenPadding)) {
        repeat((count + columns - 1) / columns) { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.cardGap)
            ) {
                repeat(columns) { col ->
                    val idx = row * columns + col
                    if (idx < count) {
                        ShimmerCard(modifier = Modifier.weight(1f))
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            Spacer(Modifier.height(Spacing.cardGap))
        }
    }
}
