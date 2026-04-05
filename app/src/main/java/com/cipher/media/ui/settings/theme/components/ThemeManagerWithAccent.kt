package com.cipher.media.ui.settings.theme.components

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.cipher.media.ui.settings.theme.model.AccentColor
import com.cipher.media.ui.theme.*

/**
 * Helper to build dynamic ColorScheme with accent colors
 */
class ThemeManagerWithAccent {

  fun buildDynamicColorScheme(accentName: String): ColorScheme {
    val accent = try {
      AccentColor.valueOf(accentName)
    } catch (e: Exception) {
      AccentColor.SAFFRON
    }
    val primary = accent.color

    return darkColorScheme(
      primary = primary,
      onPrimary = CIPHEROnPrimary,
      primaryContainer = primary.copy(alpha = 0.2f),
      secondary = CIPHERSecondaryLight,
      secondaryContainer = CIPHERSecondaryContainer,
      tertiary = CIPHERTertiary,
      tertiaryContainer = CIPHERTertiaryContainer,
      background = CIPHERBackground,
      surface = CIPHERSurface,
      surfaceVariant = CIPHERSurfaceVariant,
      onBackground = CIPHEROnBackground,
      onSurface = CIPHEROnSurface,
      onSurfaceVariant = CIPHEROnSurfaceVariant,
      error = CIPHERError,
      errorContainer = CIPHERErrorContainer,
      onError = CIPHEROnError,
      outline = CIPHEROutline,
      outlineVariant = CIPHEROutlineVariant
    )
  }

  fun getAccentFromName(accentName: String): AccentColor {
    return try {
      AccentColor.valueOf(accentName)
    } catch (e: Exception) {
      AccentColor.SAFFRON
    }
  }
}