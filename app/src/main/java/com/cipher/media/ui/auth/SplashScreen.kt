package com.cipher.media.ui.auth

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*
import kotlinx.coroutines.delay

/**
 * Native reconstruction of the CIPHER Splash Screen.
 * Routes to the next screen automatically after a short delay.
 */
@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    // Auto-navigate after 2.5 seconds
    LaunchedEffect(Unit) {
        delay(2500)
        onSplashComplete()
    }

    // Shield glow animation
    val infiniteTransition = rememberInfiniteTransition(label = "shield_glow")
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_scale"
    )
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    // Animated loading bar
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    val progressAnim by animateFloatAsState(
        targetValue = if (containerSize.width > 0) 1f else 0f,
        animationSpec = tween(durationMillis = 2500, easing = LinearEasing),
        label = "loading_bar"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        CIPHERBackground,
                        Color(0xFF1A237E) // Deep blue tint from original design
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Ambient flares (background blurs)
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = (-50).dp, y = (-50).dp)
                .size(300.dp)
                .blur(120.dp)
                .background(CIPHERPrimary.copy(alpha = 0.1f), RoundedCornerShape(percent = 50))
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 50.dp, y = 50.dp)
                .size(250.dp)
                .blur(100.dp)
                .background(CIPHERSecondaryContainer.copy(alpha = 0.2f), RoundedCornerShape(percent = 50))
        )

        // Central Branding Cluster
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Logo Icon: Shield with Play Icon
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                // Outer Glow
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(24.dp)
                        .background(
                            color = CIPHERPrimary.copy(alpha = glowAlpha),
                            shape = RoundedCornerShape(percent = 50)
                        )
                        .padding(16.dp)
                )

                Icon(
                    imageVector = Icons.Filled.Shield,
                    contentDescription = "Shield",
                    modifier = Modifier.size(120.dp),
                    tint = CIPHERPrimary
                )

                // Inner Play Icon Overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play",
                        modifier = Modifier.size(56.dp),
                        tint = CIPHERBackground
                    )
                }
            }

            // Wordmark and Tagline
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "CIPHER",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = (-1).sp
                    ),
                    color = CIPHERPrimary
                )
                Text(
                    text = "Your Media, Your Secret",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp
                    ),
                    color = CIPHEROnSurfaceVariant
                )
            }
        }

        // Security Status & Loading Footer
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Military Grade Badge
            Row(
                modifier = Modifier
                    .background(
                        color = CIPHERSurfaceVariant.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(percent = 50)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.VerifiedUser,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = CIPHERTertiary
                )
                Text(
                    text = "MILITARY GRADE ENCRYPTION ACTIVE",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        fontSize = 10.sp
                    ),
                    color = CIPHEROnSurfaceVariant
                )
            }

            // Loading Bar
            Box(
                modifier = Modifier
                    .width(192.dp)
                    .height(2.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(CIPHERSurfaceBright)
                    .onSizeChanged { containerSize = it }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progressAnim)
                        .background(CIPHERPrimary)
                )
            }
        }
    }
}
