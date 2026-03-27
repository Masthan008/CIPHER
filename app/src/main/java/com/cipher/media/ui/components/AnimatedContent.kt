package com.cipher.media.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

/**
 * Staggered fade-in animation for list/grid items.
 * Each item fades in with a delay based on its index.
 */
@Composable
fun StaggeredAnimatedItem(
    index: Int,
    delayPerItem: Int = 50,
    durationMs: Int = 300,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = durationMs,
                delayMillis = index * delayPerItem,
                easing = FastOutSlowInEasing
            )
        ) + slideInVertically(
            initialOffsetY = { it / 4 },
            animationSpec = tween(
                durationMillis = durationMs,
                delayMillis = index * delayPerItem,
                easing = FastOutSlowInEasing
            )
        )
    ) {
        content()
    }
}

/**
 * Scale-on-press button effect (0.95 bounce).
 */
@Composable
fun ScalePressBox(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "scale_press"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
    ) {
        content()
    }
}

/**
 * Breathing/pulsing animation for album art "now playing" indicator.
 */
@Composable
fun BreathingAnimation(
    modifier: Modifier = Modifier,
    content: @Composable (Float) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathing_scale"
    )

    Box(modifier = modifier.graphicsLayer { scaleX = scale; scaleY = scale }) {
        content(scale)
    }
}

/**
 * Navigation transition specs for AnimatedNavHost.
 */
object NavAnimations {
    fun enterTransition(): EnterTransition = fadeIn(
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    ) + slideInHorizontally(
        initialOffsetX = { it / 6 },
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    fun exitTransition(): ExitTransition = fadeOut(
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    ) + slideOutHorizontally(
        targetOffsetX = { -it / 6 },
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    fun popEnterTransition(): EnterTransition = fadeIn(
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    ) + slideInHorizontally(
        initialOffsetX = { -it / 6 },
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    fun popExitTransition(): ExitTransition = fadeOut(
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    ) + slideOutHorizontally(
        targetOffsetX = { it / 6 },
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )
}
