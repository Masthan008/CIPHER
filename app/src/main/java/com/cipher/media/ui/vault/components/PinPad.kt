package com.cipher.media.ui.vault.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.*

/**
 * Premium 3x4 PIN pad with:
 *  - Spring-physics scale on press (bouncy)
 *  - Radial glow ripple emanating from touch point
 *  - Staggered entrance animation for each key
 */
@Composable
fun PinPad(
    onDigit: (Char) -> Unit,
    onBackspace: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keys = listOf(
        listOf('1', '2', '3'),
        listOf('4', '5', '6'),
        listOf('7', '8', '9'),
        listOf('⌫', '0', '✓')
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        keys.forEachIndexed { rowIdx, row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEachIndexed { colIdx, key ->
                    val itemIndex = rowIdx * 3 + colIdx

                    // Staggered entrance
                    var visible by remember { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(itemIndex * 60L)
                        visible = true
                    }
                    val entranceScale by animateFloatAsState(
                        targetValue = if (visible) 1f else 0f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        ),
                        label = "entrance_$itemIndex"
                    )

                    Box(modifier = Modifier.graphicsLayer {
                        scaleX = entranceScale; scaleY = entranceScale
                        alpha = entranceScale
                    }) {
                        AnimatedPinKey(
                            key = key,
                            onClick = {
                                when (key) {
                                    '⌫' -> onBackspace()
                                    '✓' -> onSubmit()
                                    else -> onDigit(key)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimatedPinKey(
    key: Char,
    onClick: () -> Unit
) {
    // Spring press animation
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        label = "key_scale"
    )

    // Glow ripple
    var rippleProgress by remember { mutableFloatStateOf(0f) }
    val animatedRipple by animateFloatAsState(
        targetValue = rippleProgress,
        animationSpec = tween(400, easing = FastOutSlowInEasing),
        label = "key_ripple"
    )

    // Background glow alpha
    val glowAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.4f else 0f,
        animationSpec = tween(150),
        label = "key_glow"
    )

    Box(
        modifier = Modifier
            .size(72.dp)
            .graphicsLayer {
                scaleX = scale; scaleY = scale
            }
            .clip(CircleShape)
            .drawBehind {
                // Base background
                drawCircle(
                    color = CIPHERSurfaceVariant.copy(alpha = 0.6f),
                    radius = size.minDimension / 2f
                )
                // Glow on press
                if (glowAlpha > 0f) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                CIPHERPrimary.copy(alpha = glowAlpha),
                                Color.Transparent
                            )
                        ),
                        radius = size.minDimension / 2f
                    )
                }
                // Ripple ring
                if (animatedRipple > 0f && animatedRipple < 1f) {
                    drawCircle(
                        color = CIPHERPrimary.copy(alpha = (1f - animatedRipple) * 0.3f),
                        radius = size.minDimension / 2f * animatedRipple * 1.2f
                    )
                }
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        rippleProgress = 0f
                        tryAwaitRelease()
                        isPressed = false
                        rippleProgress = 1f
                        onClick()
                        // Reset ripple for next tap
                        kotlinx.coroutines.delay(400)
                        rippleProgress = 0f
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        when (key) {
            '⌫' -> Icon(
                Icons.Default.Backspace, "Backspace",
                tint = CIPHEROnSurface, modifier = Modifier.size(24.dp)
            )
            '✓' -> Icon(
                Icons.Default.Check, "Submit",
                tint = CIPHERPrimary, modifier = Modifier.size(24.dp)
            )
            else -> Text(
                key.toString(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = CIPHEROnSurface
            )
        }
    }
}
