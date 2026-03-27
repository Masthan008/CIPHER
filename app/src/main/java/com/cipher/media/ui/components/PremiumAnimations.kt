package com.cipher.media.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.*
import kotlin.math.sin

// ============================================================================
// PREMIUM ANIMATION TOOLKIT — CIPHER
// ============================================================================

// ========== 1. GLASSMORPHIC CARD ==========
/**
 * A frosted-glass card background with animated gradient shimmer.
 * Simulates a glassmorphic blur effect using a semi-transparent gradient overlay
 * that subtly shifts over time, creating a premium "frosted panel" look.
 */
@Composable
fun GlassmorphicCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "glass_shimmer")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -200f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "glass_offset"
    )

    val glassBrush = Brush.linearGradient(
        colors = listOf(
            CIPHERSurface.copy(alpha = 0.65f),
            CIPHERSurfaceVariant.copy(alpha = 0.35f),
            CIPHERSurface.copy(alpha = 0.55f),
            CIPHERPrimary.copy(alpha = 0.05f),
            CIPHERSurface.copy(alpha = 0.65f)
        ),
        start = Offset(shimmerOffset, 0f),
        end = Offset(shimmerOffset + 400f, 300f)
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(glassBrush)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        CIPHERDivider.copy(alpha = 0.4f),
                        CIPHERPrimary.copy(alpha = 0.15f),
                        CIPHERDivider.copy(alpha = 0.2f)
                    )
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
    ) {
        content()
    }
}

// ========== 2. SPRING-PHYSICS PRESS ANIMATION ==========
/**
 * A spring-based press effect that gives cards and buttons a natural, bouncy feel.
 * Uses physics-based spring animation rather than linear tween.
 */
@Composable
fun SpringPressEffect(
    onClick: () -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "spring_scale"
    )

    val elevation by animateFloatAsState(
        targetValue = if (isPressed) 2f else 8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "spring_elevation"
    )

    content(
        Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                shadowElevation = elevation
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    )
}

// ========== 3. PARALLAX SCROLL OFFSET ==========
/**
 * Gives items a subtle parallax offset based on their scroll position index.
 * Creates a layered depth effect in lists and grids.
 */
@Composable
fun ParallaxOffset(
    scrollProgress: Float, // 0f to 1f, from LazyList firstVisibleItemScrollOffset
    index: Int,
    depth: Float = 0.05f,  // parallax depth multiplier
    content: @Composable (Modifier) -> Unit
) {
    val offsetY = remember(scrollProgress, index) {
        -(scrollProgress * depth * (index % 3 + 1) * 20f)
    }

    val animatedOffset by animateFloatAsState(
        targetValue = offsetY,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "parallax_y"
    )

    content(Modifier.offset(y = animatedOffset.dp))
}

// ========== 4. NEON GLOW BORDER ==========
/**
 * Animated neon glow border that cycles through primary/accent colors.
 * Perfect for premium/featured content cards and active selections.
 */
@Composable
fun NeonGlowBorder(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    glowRadius: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "neon_glow")
    val hueShift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "hue_shift"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    // Cycle between saffron → gold → amber
    val color1 = CIPHERPrimary.copy(alpha = glowAlpha)
    val color2 = CIPHERSecondaryLight.copy(alpha = glowAlpha * 0.6f)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .border(
                width = 1.5.dp,
                brush = Brush.sweepGradient(
                    colors = listOf(color1, color2, color1, color2, color1)
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
    ) {
        content()
    }
}

// ========== 5. GLOW RIPPLE ON TAP ==========
/**
 * Radial glow ripple effect on tap. Creates a circle of light that expands
 * from the touch point and fades out, like a premium touch response.
 */
@Composable
fun GlowRippleContainer(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    var rippleCenter by remember { mutableStateOf(Offset.Zero) }
    var rippleActive by remember { mutableStateOf(false) }

    val rippleRadius by animateFloatAsState(
        targetValue = if (rippleActive) 300f else 0f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "ripple_radius",
        finishedListener = { rippleActive = false }
    )

    val rippleAlpha by animateFloatAsState(
        targetValue = if (rippleActive) 0f else 0.3f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "ripple_alpha"
    )

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        rippleCenter = offset
                        rippleActive = true
                        onClick()
                    }
                )
            }
            .drawWithContent {
                drawContent()
                if (rippleRadius > 0f) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                CIPHERPrimary.copy(alpha = rippleAlpha),
                                Color.Transparent
                            ),
                            center = rippleCenter,
                            radius = rippleRadius
                        ),
                        radius = rippleRadius,
                        center = rippleCenter
                    )
                }
            }
    ) {
        content()
    }
}

// ========== 6. FLOATING ENTRANCE ==========
/**
 * Items float up from below with a gentle rotation and spring physics.
 * More dramatic than StaggeredEntrance — ideal for hero cards and feature screens.
 */
@Composable
fun FloatingEntrance(
    index: Int,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(index * 80L)
        visible = true
    }

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "float_scale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "float_alpha"
    )
    val offsetY by animateFloatAsState(
        targetValue = if (visible) 0f else 40f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "float_y"
    )
    val rotationZ by animateFloatAsState(
        targetValue = if (visible) 0f else -2f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "float_rotation"
    )

    Box(
        modifier = Modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
                translationY = offsetY * density
                this.rotationZ = rotationZ
            }
    ) {
        content()
    }
}

// ========== 7. IDLE HOVER FLOAT ==========
/**
 * A gentle floating idle animation that makes elements feel alive.
 * Combines slow Y oscillation with subtle scale breathing.
 */
@Composable
fun IdleFloat(
    speed: Int = 3000,
    amplitude: Float = 4f,
    content: @Composable (Modifier) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "idle_float")

    val yOffset by infiniteTransition.animateFloat(
        initialValue = -amplitude,
        targetValue = amplitude,
        animationSpec = infiniteRepeatable(
            animation = tween(speed, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "idle_y"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.015f,
        animationSpec = infiniteRepeatable(
            animation = tween(speed + 500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "idle_scale"
    )

    content(
        Modifier
            .offset(y = yOffset.dp)
            .graphicsLayer { scaleX = scale; scaleY = scale }
    )
}

// ========== 8. ANIMATED COUNTER TEXT ==========
/**
 * Smoothly animates number changes by sliding old value up/out and new value up/in.
 * Great for dynamic counters (play counts, file counts, timer, etc.).
 */
@Composable
fun AnimatedCounterSlide(
    targetValue: Int,
    content: @Composable (Int) -> Unit
) {
    AnimatedContent(
        targetState = targetValue,
        transitionSpec = {
            (slideInVertically { -it } + fadeIn()) togetherWith
                    (slideOutVertically { it } + fadeOut())
        },
        label = "counter_slide"
    ) { value ->
        content(value)
    }
}

// ========== 9. PARTICLE SHIMMER OVERLAY ==========
/**
 * Draws tiny animated particles that slowly drift across a surface,
 * giving a "magic dust" effect to premium cards and hero sections.
 */
@Composable
fun ParticleShimmerOverlay(
    modifier: Modifier = Modifier,
    particleCount: Int = 12,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "particles")

    data class ParticleState(val xPhase: Float, val yPhase: Float, val speed: Int, val size: Float)

    val particles = remember {
        (0 until particleCount).map {
            ParticleState(
                xPhase = (it * 137.5f) % 360f,
                yPhase = (it * 97.3f) % 360f,
                speed = 4000 + (it * 500) % 3000,
                size = 1.5f + (it % 3) * 0.5f
            )
        }
    }

    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.2832f, // 2 * PI
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particle_time"
    )

    Box(
        modifier = modifier
            .drawWithContent {
                drawContent()
                particles.forEach { p ->
                    val x = (sin(time + p.xPhase) * 0.5f + 0.5f) * size.width
                    val y = (sin(time * 0.7f + p.yPhase) * 0.5f + 0.5f) * size.height
                    val alpha = (sin(time * 1.5f + p.xPhase) * 0.5f + 0.5f) * 0.4f

                    drawCircle(
                        color = CIPHERPrimary.copy(alpha = alpha),
                        radius = p.size * density,
                        center = Offset(x, y)
                    )
                }
            }
    ) {
        content()
    }
}
