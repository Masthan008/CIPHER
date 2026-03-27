package com.cipher.media.ui.auth

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.sin

/**
 * Premium Splash screen with:
 *  - Diagonal gradient background
 *  - Floating particle "magic dust" effect
 *  - Spring-physics logo entrance (scale + rotation)
 *  - Idle floating animation while visible
 *  - Staggered tagline fade-in
 */
@Composable
fun SplashScreen(onSplashComplete: () -> Unit) {
    var animationStarted by remember { mutableStateOf(false) }
    var showTagline by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animationStarted = true
        delay(2000)
        showTagline = true
        delay(500)
        onSplashComplete()
    }

    // Spring-physics logo entrance
    val logoScale by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0.3f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "logo_scale"
    )
    val logoAlpha by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0f,
        animationSpec = tween(800, easing = FastOutSlowInEasing),
        label = "logo_alpha"
    )
    val logoRotation by animateFloatAsState(
        targetValue = if (animationStarted) 0f else -15f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "logo_rotation"
    )

    // Idle float for the logo
    val infiniteTransition = rememberInfiniteTransition(label = "splash_idle")
    val idleY by infiniteTransition.animateFloat(
        initialValue = -3f,
        targetValue = 3f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "idle_y"
    )

    // Particle animation time
    val particleTime by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.2832f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "particle_time"
    )

    // Neon glow pulse
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    val taglineAlpha by animateFloatAsState(
        targetValue = if (showTagline) 1f else 0f,
        animationSpec = tween(500, easing = FastOutSlowInEasing),
        label = "tagline_alpha"
    )

    // Particle data
    data class Particle(val xPhase: Float, val yPhase: Float, val size: Float, val speed: Float)
    val particles = remember {
        (0 until 20).map {
            Particle(
                xPhase = (it * 137.5f) % 360f,
                yPhase = (it * 97.3f) % 360f,
                size = 1.5f + (it % 4) * 0.8f,
                speed = 0.7f + (it % 3) * 0.3f
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(GradientSplashStart, GradientSplashEnd)
                )
            )
            .drawBehind {
                // Floating particles
                particles.forEach { p ->
                    val x = (sin(particleTime * p.speed + p.xPhase) * 0.5f + 0.5f) * size.width
                    val y = (sin(particleTime * p.speed * 0.7f + p.yPhase) * 0.5f + 0.5f) * size.height
                    val alpha = (sin(particleTime * 1.5f + p.xPhase) * 0.5f + 0.5f) * 0.35f

                    drawCircle(
                        color = CIPHERPrimary.copy(alpha = alpha),
                        radius = p.size * density,
                        center = Offset(x, y)
                    )
                }

                // Central neon glow behind logo
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            CIPHERPrimary.copy(alpha = glowAlpha),
                            Color.Transparent
                        ),
                        center = Offset(size.width / 2f, size.height / 2f - 60 * density),
                        radius = 200f * density
                    ),
                    center = Offset(size.width / 2f, size.height / 2f - 60 * density),
                    radius = 200f * density
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Shield logo with spring entrance + idle float
            Icon(
                Icons.Default.Shield, null,
                modifier = Modifier
                    .size(120.dp)
                    .offset(y = idleY.dp)
                    .graphicsLayer {
                        scaleX = logoScale; scaleY = logoScale
                        alpha = logoAlpha
                        rotationZ = logoRotation
                    },
                tint = CIPHERPrimary
            )

            Spacer(Modifier.height(Spacing.md))

            // Wordmark
            Text(
                text = "CIPHER",
                modifier = Modifier.graphicsLayer {
                    scaleX = logoScale; scaleY = logoScale
                    alpha = logoAlpha
                },
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 28.sp,
                    letterSpacing = (-0.5).sp
                ),
                fontWeight = FontWeight.Bold,
                color = CIPHEROnSurface
            )

            Spacer(Modifier.height(Spacing.lg))

            // Tagline
            Text(
                text = "Your Media, Your Secret",
                modifier = Modifier.alpha(taglineAlpha),
                style = MaterialTheme.typography.bodyMedium,
                color = CIPHEROnSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
