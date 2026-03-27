package com.cipher.media.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * CIPHER spacing system based on 4dp grid.
 * Use these consistently for all padding, margins, and gaps.
 */
object Spacing {
    val xxs: Dp = 2.dp
    val xs: Dp = 4.dp
    val sm: Dp = 8.dp
    val md: Dp = 16.dp
    val lg: Dp = 24.dp
    val xl: Dp = 32.dp
    val xxl: Dp = 48.dp
    val xxxl: Dp = 64.dp

    // Specific use-cases
    val screenPadding: Dp = 16.dp
    val cardPadding: Dp = 16.dp
    val cardGap: Dp = 8.dp
    val sectionGap: Dp = 24.dp
    val itemSpacing: Dp = 12.dp
    val iconTextGap: Dp = 8.dp

    // Touch targets
    val minTouchTarget: Dp = 48.dp
    val navBarHeight: Dp = 80.dp
    val topBarHeight: Dp = 64.dp
    val fabSize: Dp = 56.dp
    val miniPlayerHeight: Dp = 64.dp
}
