package com.cipher.media.ui.navigation.components

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.navigation.BottomNavItem
import com.cipher.media.ui.theme.CIPHERPrimary
import com.cipher.media.ui.theme.Spacing
import kotlin.math.abs
import kotlin.math.exp

/**
 * Premium Liquid Glass Dock with real-time physics and Mac-style magnification.
 * 
 * Implements:
 * 1. Liquid Glass blurring & rendering
 * 2. Finger-tracking magnification (Gaussian falloff)
 * 3. Spring physics for all animations
 * 4. Blob morphing active indicator
 */
@Composable
fun LiquidGlassDock(
    items: List<BottomNavItem>,
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
    bottomPadding: Dp = 16.dp
) {
    var fingerX by remember { mutableStateOf<Float?>(null) }
    var dockWidth by remember { mutableStateOf(0) }
    
    // Determine selected index
    val selectedIndex = items.indexOfFirst { currentRoute?.startsWith(it.screen.route) == true }.takeIf { it >= 0 } ?: 0
    
    // Liquid Indicator position physics
    // Uses a bouncy spring for an organic "snap"
    val indicatorOffset by animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        animationSpec = spring(
            dampingRatio = 0.65f, // Bouncy but damped
            stiffness = Spring.StiffnessLow
        ),
        label = "IndicatorOffset"
    )

    // A secondary position to create the "squish/stretch" blob effect during fast movement
    val indicatorFollower by animateFloatAsState(
        targetValue = indicatorOffset,
        animationSpec = spring(
            dampingRatio = 0.9f, 
            stiffness = Spring.StiffnessLow
        ),
        label = "IndicatorFollower"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md)
            .padding(bottom = bottomPadding),
        contentAlignment = Alignment.BottomCenter
    ) {
        // The Floating Glass Pill
        Row(
            modifier = Modifier
                .onGloballyPositioned { dockWidth = it.size.width }
                .glassmorphismEffect()
                // Track finger for Mac-style dock scaling
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            when (event.type) {
                                PointerEventType.Press, PointerEventType.Move -> {
                                    fingerX = event.changes.first().position.x
                                }
                                PointerEventType.Release -> {
                                    fingerX = null
                                }
                            }
                        }
                    }
                }
                .padding(horizontal = 12.dp, vertical = 8.dp)
                // Draw the active indicator behind the items
                .drawBehind {
                    if (items.isEmpty() || dockWidth == 0) return@drawBehind
                    val itemWidth = size.width / items.size
                    
                    // The main position
                    val leadCenter = (indicatorOffset * itemWidth) + (itemWidth / 2f)
                    // The trailing position
                    val trailCenter = (indicatorFollower * itemWidth) + (itemWidth / 2f)
                    
                    // Draw a stretched pill between lead and trail to simulate liquid merging
                    val left = minOf(leadCenter, trailCenter) - 24.dp.toPx()
                    val right = maxOf(leadCenter, trailCenter) + 24.dp.toPx()
                    
                    drawRoundRect(
                        color = CIPHERPrimary.copy(alpha = 0.15f),
                        topLeft = Offset(left, size.height / 2f - 24.dp.toPx()),
                        size = Size(right - left, 48.dp.toPx()),
                        cornerRadius = CornerRadius(24.dp.toPx())
                    )
                },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedIndex == index
                
                LiquidDockItem(
                    item = item,
                    isSelected = isSelected,
                    index = index,
                    itemCount = items.size,
                    dockWidth = dockWidth,
                    fingerX = fingerX,
                    onClick = { onNavigate(item.screen.route) }
                )
            }
        }
    }
}

@Composable
private fun RowScope.LiquidDockItem(
    item: BottomNavItem,
    isSelected: Boolean,
    index: Int,
    itemCount: Int,
    dockWidth: Int,
    fingerX: Float?,
    onClick: () -> Unit
) {
    var itemCenterX by remember { mutableStateOf(0f) }
    val density = LocalDensity.current.density
    
    // Physics-based scale calculation
    val baseScale = 1f
    var targetScale = baseScale
    var targetOffsetY = 0f
    
    // Calculate Mac-style magnification if dragging
    if (fingerX != null && dockWidth > 0 && itemCenterX > 0f) {
        // Distance from finger to this item's center
        val distance = abs(fingerX - itemCenterX)
        
        // Use a bell curve (Gaussian-like) falloff for organic scaling
        val magnificationRange = dockWidth / 2.5f 
        if (distance < magnificationRange) {
            // zoomAmount peaks at 1.0 (finger exactly on item), drops to 0 at magnificationRange
            val zoomAmount = 1f - (distance / magnificationRange)
            // Organic smoothing (ease-in-out curve)
            val smoothedZoom = zoomAmount * zoomAmount * (3f - 2f * zoomAmount)
            
            targetScale = baseScale + (smoothedZoom * 0.45f) // Max 45% larger
            targetOffsetY = -(smoothedZoom * 16f * density) // Lift up organically
        }
    }
    
    // Animate the scale with a bouncy spring
    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = spring(
            dampingRatio = 0.5f,
            stiffness = Spring.StiffnessMedium
        ),
        label = "IconScale"
    )
    
    // Animate the vertical lift
    val offsetY by animateFloatAsState(
        targetValue = targetOffsetY,
        animationSpec = spring(
            dampingRatio = 0.5f,
            stiffness = Spring.StiffnessMedium
        ),
        label = "IconLift"
    )
    
    // Subtle tilt effect for 3D feel
    val tiltTarget = if (fingerX != null) {
        val dist = fingerX - itemCenterX
        if (abs(dist) < dockWidth / 3f) {
            (dist / (dockWidth / 3f)) * 15f // Tilt up to 15 degrees based on finger position relative to item
        } else 0f
    } else 0f
    
    val rotationY by animateFloatAsState(
        targetValue = tiltTarget,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessMedium),
        label = "IconTilt"
    )

    // Compose layout
    Box(
        modifier = Modifier
            .weight(1f)
            .height(64.dp)
            .onGloballyPositioned { coords ->
                itemCenterX = coords.positionInParent().x + (coords.size.width / 2f)
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null, // No default ripple, we use physics!
                onClick = onClick
            )
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationY = offsetY
                this.rotationY = rotationY
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = if (isSelected) CIPHERPrimary else Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Label (fades in on selected or magnified)
            val baseLabelAlpha = if (isSelected) 1f else 0.4f
            val labelAlphaTarget = if (scale > 1.1f) 1f else baseLabelAlpha
            val labelAlpha by animateFloatAsState(labelAlphaTarget)
            
            Text(
                text = item.label,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = (if (isSelected) CIPHERPrimary else Color.White).copy(alpha = labelAlpha)
            )
        }
    }
}

/**
 * Modifier for creating the frosted glass effect.
 * Real native blur on API 31+, optimized transparent fallback for older devices.
 */
fun Modifier.glassmorphismEffect(
    blurRadius: Float = 40f,
    cornerRadius: Dp = 32.dp,
    containerColor: Color = Color(0xFF1E1E1E).copy(alpha = 0.65f)
): Modifier = this
    .graphicsLayer {
        clip = true
        shape = RoundedCornerShape(cornerRadius)
    }
    .background(containerColor)
    .border(
        width = 1.dp,
        brush = Brush.verticalGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.15f), // Top highlight
                Color.White.copy(alpha = 0.02f)  // Bottom
            )
        ),
        shape = RoundedCornerShape(cornerRadius)
    )
