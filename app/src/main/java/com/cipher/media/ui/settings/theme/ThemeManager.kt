package com.cipher.media.ui.settings.theme

import android.content.Context
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.cipher.media.ui.settings.theme.model.AccentColor
import com.cipher.media.ui.settings.theme.model.ThemeConfig
import com.cipher.media.ui.settings.theme.model.ThemeTier
import com.cipher.media.ui.theme.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Central theme manager that builds ColorScheme from ThemeConfig + accent color.
 */
@Singleton
class ThemeManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("cipher_theme", Context.MODE_PRIVATE)

    /** All available themes */
    val themes: List<ThemeConfig> = listOf(
        ThemeConfig("dark", "Dark", Color(0xFF121212), Color(0xFF1E1E1E), Color(0xFF2A2A2A),
            CIPHERPrimary, CIPHEROnPrimary, CIPHEROnSurface, CIPHEROnSurfaceVariant, isDark = true, tier = ThemeTier.FREE),
        ThemeConfig("black", "Black (OLED)", Color(0xFF000000), Color(0xFF0D0D0D), Color(0xFF1A1A1A),
            CIPHERPrimary, CIPHEROnPrimary, CIPHEROnSurface, CIPHEROnSurfaceVariant, isDark = true, tier = ThemeTier.FREE),
        ThemeConfig("light", "Light", Color(0xFFF5F5F5), Color(0xFFFFFFFF), Color(0xFFEEEEEE),
            CIPHERPrimary, Color(0xFFFFFFFF), Color(0xFF212121), Color(0xFF757575), isDark = false, tier = ThemeTier.FREE),
        ThemeConfig("midnight", "Midnight Blue", Color(0xFF0D1117), Color(0xFF161B22), Color(0xFF21262D),
            Color(0xFF58A6FF), Color(0xFF0D1117), CIPHEROnSurface, CIPHEROnSurfaceVariant, isDark = true, tier = ThemeTier.PRO),
        ThemeConfig("nord", "Nord", Color(0xFF2E3440), Color(0xFF3B4252), Color(0xFF434C5E),
            Color(0xFF88C0D0), Color(0xFF2E3440), Color(0xFFECEFF4), Color(0xFFD8DEE9), isDark = true, tier = ThemeTier.PRO),
        ThemeConfig("dracula", "Dracula", Color(0xFF282A36), Color(0xFF44475A), Color(0xFF6272A4),
            Color(0xFFBD93F9), Color(0xFF282A36), Color(0xFFF8F8F2), Color(0xFFBFBFBF), isDark = true, tier = ThemeTier.PRO),
        ThemeConfig("monokai", "Monokai", Color(0xFF272822), Color(0xFF3E3D32), Color(0xFF49483E),
            Color(0xFFA6E22E), Color(0xFF272822), Color(0xFFF8F8F2), Color(0xFF75715E), isDark = true, tier = ThemeTier.PRO),
        ThemeConfig("solarized", "Solarized", Color(0xFF002B36), Color(0xFF073642), Color(0xFF586E75),
            Color(0xFFB58900), Color(0xFF002B36), Color(0xFFFDF6E3), Color(0xFF93A1A1), isDark = true, tier = ThemeTier.PRO),
        ThemeConfig("catppuccin", "Catppuccin", Color(0xFF1E1E2E), Color(0xFF313244), Color(0xFF45475A),
            Color(0xFFCBA6F7), Color(0xFF1E1E2E), Color(0xFFCDD6F4), Color(0xFFA6ADC8), isDark = true, tier = ThemeTier.PRO),
        ThemeConfig("gruvbox", "Gruvbox", Color(0xFF282828), Color(0xFF3C3836), Color(0xFF504945),
            Color(0xFFFE8019), Color(0xFF282828), Color(0xFFEBDBB2), Color(0xFFA89984), isDark = true, tier = ThemeTier.PRO),
    )

    /** Build a Material3 ColorScheme from theme + accent */
    fun buildColorScheme(themeId: String, accent: AccentColor): ColorScheme {
        val theme = themes.find { it.id == themeId } ?: themes[0]
        val primary = accent.color

        return if (theme.isDark) {
            darkColorScheme(
                primary = primary,
                onPrimary = theme.onPrimary,
                primaryContainer = primary.copy(alpha = 0.2f),
                secondary = CIPHERSecondaryLight,
                secondaryContainer = CIPHERSecondaryContainer,
                tertiary = CIPHERTertiary,
                background = theme.background,
                surface = theme.surface,
                surfaceVariant = theme.surfaceVariant,
                onBackground = theme.onSurface,
                onSurface = theme.onSurface,
                onSurfaceVariant = theme.onSurfaceVariant,
                error = CIPHERError,
                outline = CIPHEROutline
            )
        } else {
            lightColorScheme(
                primary = primary,
                onPrimary = Color.White,
                primaryContainer = primary.copy(alpha = 0.12f),
                secondary = CIPHERSecondary,
                background = theme.background,
                surface = theme.surface,
                surfaceVariant = theme.surfaceVariant,
                onBackground = theme.onSurface,
                onSurface = theme.onSurface,
                onSurfaceVariant = theme.onSurfaceVariant,
                error = CIPHERError,
                outline = Color(0xFFBDBDBD)
            )
        }
    }

    fun saveTheme(themeId: String) = prefs.edit().putString("selected_theme", themeId).apply()
    fun saveAccent(accent: AccentColor) = prefs.edit().putString("accent_color", accent.name).apply()
    fun getSavedThemeId(): String = prefs.getString("selected_theme", "dark") ?: "dark"
    fun getSavedAccent(): AccentColor {
        val name = prefs.getString("accent_color", "SAFFRON") ?: "SAFFRON"
        return try { AccentColor.valueOf(name) } catch (_: Exception) { AccentColor.SAFFRON }
    }
}
