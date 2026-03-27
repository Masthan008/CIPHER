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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*
import kotlinx.coroutines.delay

/**
 * Splash screen: diagonal gradient #0D0D0D → #1A237E,
 * logo scale 0.8→1.0, opacity 0→1, tagline fade last 0.5s.
 * Duration: 2.5s then calls onSplashComplete.
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

    val logoScale by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0.8f,
        animationSpec = tween(1500, easing = FastOutSlowInEasing), label = "logo_scale"
    )
    val logoAlpha by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0f,
        animationSpec = tween(1200, easing = FastOutSlowInEasing), label = "logo_alpha"
    )
    val taglineAlpha by animateFloatAsState(
        targetValue = if (showTagline) 1f else 0f,
        animationSpec = tween(500, easing = FastOutSlowInEasing), label = "tagline_alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(GradientSplashStart, GradientSplashEnd)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Shield logo
            Icon(
                Icons.Default.Shield, null,
                modifier = Modifier
                    .size(120.dp)
                    .scale(logoScale)
                    .alpha(logoAlpha),
                tint = CIPHERPrimary
            )

            Spacer(Modifier.height(Spacing.md))

            // Wordmark
            Text(
                text = "CIPHER",
                modifier = Modifier
                    .scale(logoScale)
                    .alpha(logoAlpha),
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
