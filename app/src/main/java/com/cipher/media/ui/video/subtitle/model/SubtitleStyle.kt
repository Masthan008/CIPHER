package com.cipher.media.ui.video.subtitle.model

import androidx.compose.ui.graphics.Color
import com.cipher.media.ui.theme.CIPHERPrimary

/**
 * Subtitle appearance styling with Free/Pro tier gating.
 */
data class SubtitleStyle(
    val fontSize: Int = 100,                    // Percentage: 75-150 (Free), 50-200 (Pro)
    val fontColor: Color = Color.White,
    val backgroundColor: Color = Color.Black.copy(alpha = 0.5f),
    val backgroundOpacity: Float = 0.5f,        // 0-1 (Pro only for fine control)
    val position: SubtitlePosition = SubtitlePosition.BOTTOM,
    val fontFamily: SubtitleFont = SubtitleFont.DEFAULT,
    val outlineEnabled: Boolean = true
)

enum class SubtitlePosition(val label: String) {
    TOP("Top"),
    MIDDLE("Middle"),
    BOTTOM("Bottom")  // FREE default
}

enum class SubtitleFont(val label: String, val isPro: Boolean) {
    DEFAULT("Default", false),
    ROBOTO("Roboto", true),
    OPEN_SANS("Open Sans", true),
    MONOSPACE("Monospace", true),
    SERIF("Serif", true),
    CURSIVE("Cursive", true)
}

/**
 * Free tier color options.
 */
val FREE_COLORS = listOf(
    Color.White to "White",
    Color.Yellow to "Yellow"
)

/**
 * Pro tier unlocks all colors.
 */
val PRO_COLORS = listOf(
    Color.White to "White",
    Color.Yellow to "Yellow",
    Color.Cyan to "Cyan",
    Color.Green to "Green",
    Color.Magenta to "Magenta",
    CIPHERPrimary to "Saffron",
    Color.Red to "Red",
    Color(0xFF87CEEB) to "Sky Blue"
)

/**
 * Constants for subtitle tier gating.
 */
object SubtitleTierConfig {
    // FREE tier limits
    const val FREE_MIN_FONT_SIZE = 75
    const val FREE_MAX_FONT_SIZE = 150
    const val FREE_MAX_SYNC_MS = 5_000L    // ±5 seconds

    // PRO tier limits
    const val PRO_MIN_FONT_SIZE = 50
    const val PRO_MAX_FONT_SIZE = 200
    const val PRO_MAX_SYNC_MS = 60_000L    // ±60 seconds
    const val PRO_SYNC_STEP_MS = 50L       // 50ms precision
}
