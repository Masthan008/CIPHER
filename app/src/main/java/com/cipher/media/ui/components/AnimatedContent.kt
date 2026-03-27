package com.cipher.media.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

// ========== STAGGERED ENTRANCE ==========
/**
 * Items fade in + slide up with 50ms stagger per index.
 */
@Composable
fun StaggeredEntrance(
    index: Int,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(index * 50L)
        visible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(300, easing = FastOutSlowInEasing), label = "stagger_alpha"
    )
    val offsetY by animateFloatAsState(
        targetValue = if (visible) 0f else 20f,
        animationSpec = tween(300, easing = FastOutSlowInEasing), label = "stagger_y"
    )

    Box(modifier = Modifier.alpha(alpha).offset(y = offsetY.dp)) {
        content()
    }
}

// ========== SCALE ON PRESS ==========
/**
 * Scale bounce: 1.0 → 0.95 → 1.0 on press.
 */
@Composable
fun ScaleOnPress(
    isPressed: Boolean,
    content: @Composable (Modifier) -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100, easing = FastOutSlowInEasing), label = "press_scale"
    )
    content(Modifier.scale(scale))
}

// ========== BREATHING EFFECT ==========
/**
 * Subtle breathing pulse: scale 1.0 → 1.02, 4s infinite loop.
 */
@Composable
fun BreathingEffect(
    content: @Composable (Modifier) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "breath_scale"
    )
    content(Modifier.scale(scale))
}

// ========== PULSE GLOW ==========
/**
 * Pulsing alpha glow: 0.5 → 1.0, 2s loop.
 */
@Composable
fun PulseGlow(
    content: @Composable (Modifier) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "pulse_alpha"
    )
    content(Modifier.alpha(alpha))
}

// ========== NAV TRANSITIONS ==========
val NavEnterTransition: EnterTransition = fadeIn(tween(300, easing = FastOutSlowInEasing)) +
    slideInHorizontally(initialOffsetX = { it / 6 }, animationSpec = tween(300, easing = FastOutSlowInEasing))

val NavExitTransition: ExitTransition = fadeOut(tween(200, easing = FastOutSlowInEasing))

val NavPopEnterTransition: EnterTransition = fadeIn(tween(300, easing = FastOutSlowInEasing)) +
    slideInHorizontally(initialOffsetX = { -it / 6 }, animationSpec = tween(300, easing = FastOutSlowInEasing))

val NavPopExitTransition: ExitTransition = fadeOut(tween(200, easing = FastOutSlowInEasing)) +
    slideOutHorizontally(targetOffsetX = { it / 6 }, animationSpec = tween(200, easing = FastOutSlowInEasing))

// ========== TAB CROSSFADE ==========
val TabCrossfade: FiniteAnimationSpec<Float> = tween(200, easing = FastOutSlowInEasing)
