package com.cipher.media.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cipher.media.ui.theme.*

/**
 * Premium Media card with:
 *  - Spring-physics press animation (bouncy scale)
 *  - Glassmorphic shimmer border
 *  - Subtle glow on touch
 */
@Composable
fun MediaCard(
    title: String,
    subtitle: String,
    thumbnailUri: Any?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    duration: String? = null,
    badge: String? = null
) {
    // Spring press animation
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "card_scale"
    )
    val glowAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.15f else 0f,
        animationSpec = tween(200),
        label = "card_glow"
    )

    // Glassmorphic shimmer
    val infiniteTransition = rememberInfiniteTransition(label = "card_glass")
    val shimmerX by infiniteTransition.animateFloat(
        initialValue = -100f,
        targetValue = 500f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "card_shimmer"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        val released = tryAwaitRelease()
                        isPressed = false
                        if (released) {
                            onClick()
                        }
                    }
                )
            },
        shape = RoundedCornerShape(Corners.medium),
        colors = CardDefaults.cardColors(containerColor = CIPHERSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = Elevation.level1)
    ) {
        Column {
            // Thumbnail with subtle glow overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(topStart = Corners.medium, topEnd = Corners.medium))
                    .background(CIPHERSurfaceVariant)
                    .drawBehind {
                        // Glassmorphic shimmer line across thumbnail
                        drawRect(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    CIPHERPrimary.copy(alpha = 0.08f),
                                    Color.Transparent
                                ),
                                start = Offset(shimmerX, 0f),
                                end = Offset(shimmerX + 120f, size.height)
                            )
                        )
                        // Press glow
                        if (glowAlpha > 0f) {
                            drawRect(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        CIPHERPrimary.copy(alpha = glowAlpha),
                                        Color.Transparent
                                    ),
                                    center = Offset(size.width / 2f, size.height / 2f),
                                    radius = size.width * 0.8f
                                )
                            )
                        }
                    }
            ) {
                AsyncImage(
                    model = thumbnailUri,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Duration badge
                if (duration != null) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(Spacing.sm),
                        shape = RoundedCornerShape(Corners.small),
                        color = Color.Black.copy(alpha = 0.7f)
                    ) {
                        Text(
                            text = duration,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                }

                // Top-left badge (e.g., "NEW", "LIVE")
                if (badge != null) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(Spacing.sm),
                        shape = RoundedCornerShape(Corners.small),
                        color = CIPHERPrimary
                    ) {
                        Text(
                            text = badge,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = CIPHEROnPrimary
                        )
                    }
                }
            }

            // Info
            Column(modifier = Modifier.padding(Spacing.cardPadding)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = CIPHEROnSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(Spacing.xs))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = CIPHEROnSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Premium horizontal list item card with spring press.
 */
@Composable
fun MediaListItem(
    title: String,
    subtitle: String,
    thumbnailUri: Any?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    duration: String? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "list_scale"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        val released = tryAwaitRelease()
                        isPressed = false
                        if (released) {
                            onClick()
                        }
                    }
                )
            },
        shape = RoundedCornerShape(Corners.medium),
        colors = CardDefaults.cardColors(containerColor = CIPHERSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(Corners.medium))
                    .background(CIPHERSurfaceVariant)
            ) {
                AsyncImage(
                    model = thumbnailUri,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                if (duration != null) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(2.dp),
                        shape = RoundedCornerShape(2.dp),
                        color = Color.Black.copy(alpha = 0.7f)
                    ) {
                        Text(
                            duration,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = Color.White
                        )
                    }
                }
            }

            // Info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.sm)
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleSmall,
                    color = CIPHEROnSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(Spacing.xxs))
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = CIPHEROnSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            trailing?.invoke()
        }
    }
}
