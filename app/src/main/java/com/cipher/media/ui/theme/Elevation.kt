package com.cipher.media.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * CIPHER elevation system — neumorphic dark.
 * Shadow color: #000000 @ 30%. Highlight: 1dp white @ 5% top edge.
 */
object Elevation {
    val level0: Dp = 0.dp   // Base surface
    val level1: Dp = 4.dp   // Cards
    val level2: Dp = 8.dp   // FAB, dialogs
    val level3: Dp = 16.dp  // Modals, player controls
}

/**
 * CIPHER shape corner radii.
 */
object Corners {
    val none: Dp = 0.dp
    val small: Dp = 4.dp    // Buttons, chips
    val medium: Dp = 8.dp   // Cards, inputs
    val large: Dp = 12.dp   // Dialogs, sheets
    val extraLarge: Dp = 16.dp // Containers
    val pill: Dp = 50.dp    // Full round
}
