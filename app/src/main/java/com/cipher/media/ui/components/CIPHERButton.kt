package com.cipher.media.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.theme.*

/**
 * Primary gradient button with scale press animation.
 */
@Composable
fun CIPHERButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100, easing = FastOutSlowInEasing), label = "btn_scale"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .height(Spacing.minTouchTarget)
            .scale(scale),
        enabled = enabled,
        shape = RoundedCornerShape(Corners.large),
        colors = ButtonDefaults.buttonColors(
            containerColor = CIPHERPrimary,
            contentColor = CIPHEROnPrimary,
            disabledContainerColor = CIPHERSurfaceVariant,
            disabledContentColor = CIPHERTextDisabled
        ),
        interactionSource = interactionSource
    ) {
        if (icon != null) {
            Icon(icon, null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(Spacing.sm))
        }
        Text(text, fontWeight = FontWeight.Bold)
    }
}

/**
 * Secondary outlined button.
 */
@Composable
fun CIPHEROutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100), label = "obtn_scale"
    )

    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(Spacing.minTouchTarget).scale(scale),
        shape = RoundedCornerShape(Corners.large),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = CIPHERPrimary),
        interactionSource = interactionSource
    ) {
        if (icon != null) {
            Icon(icon, null, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(Spacing.sm))
        }
        Text(text)
    }
}

/**
 * Circular icon button with surface tint.
 */
@Composable
fun CIPHERIconButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = CIPHEROnSurface,
    size: Dp = Spacing.minTouchTarget
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(80), label = "icon_scale"
    )

    IconButton(
        onClick = onClick,
        modifier = modifier.size(size).scale(scale),
        interactionSource = interactionSource
    ) {
        Icon(icon, null, tint = tint, modifier = Modifier.size(24.dp))
    }
}

/**
 * FAB with primary gradient.
 */
@Composable
fun CIPHERFAB(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.size(Spacing.fabSize),
        shape = CircleShape,
        containerColor = CIPHERPrimary,
        contentColor = CIPHEROnPrimary
    ) {
        Icon(icon, null, modifier = Modifier.size(24.dp))
    }
}
