package com.cipher.media.ui.settings.theme.model

import androidx.compose.ui.graphics.Color

/**
 * Complete theme configuration for the CIPHER app.
 */
data class ThemeConfig(
    val id: String,
    val name: String,
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val primary: Color,
    val onPrimary: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val isDark: Boolean = true,
    val tier: ThemeTier = ThemeTier.FREE
)

enum class ThemeTier { FREE, PRO, POWER }

enum class AccentColor(val color: Color, val label: String) {
    SAFFRON(Color(0xFFFF9933), "Saffron"),
    OCEAN(Color(0xFF00BCD4), "Ocean"),
    EMERALD(Color(0xFF00C853), "Emerald"),
    CRIMSON(Color(0xFFE53935), "Crimson"),
    VIOLET(Color(0xFF7C4DFF), "Violet"),
    // Pro colors
    ROSE(Color(0xFFFF6090), "Rose"),
    AMBER(Color(0xFFFFAB00), "Amber"),
    TEAL(Color(0xFF1DE9B6), "Teal"),
    INDIGO(Color(0xFF536DFE), "Indigo"),
    CORAL(Color(0xFFFF7043), "Coral");

    companion object {
        val FREE_COLORS = listOf(SAFFRON, OCEAN, EMERALD, CRIMSON, VIOLET)
        val PRO_COLORS = entries.toList()
    }
}

enum class NowPlayingLayout(val label: String) {
    STANDARD("Standard"),
    COMPACT("Compact"),
    MINIMAL("Minimal"),
    SPLIT("Split"),
    BLUR("Full Blur"),
    CARD("Card"),
    VINYL("Vinyl"),
    GRADIENT("Gradient");

    companion object {
        val FREE_LAYOUTS = listOf(STANDARD)
        val PRO_LAYOUTS = entries.toList()
    }
}

enum class AppFont(val label: String, val fontFamily: String) {
    SYSTEM("System Default", ""),
    INTER("Inter", "Inter"),
    ROBOTO("Roboto", "Roboto"),
    POPPINS("Poppins", "Poppins"),
    OUTFIT("Outfit", "Outfit"),
    JETBRAINS_MONO("JetBrains Mono", "JetBrains Mono");

    companion object {
        val FREE_FONTS = listOf(SYSTEM)
        val PRO_FONTS = entries.toList()
    }
}

enum class AnimSpeed(val label: String, val multiplier: Float) {
    SLOW("Slow", 1.5f),
    NORMAL("Normal", 1.0f),
    FAST("Fast", 0.6f),
    INSTANT("Instant", 0.1f)
}
