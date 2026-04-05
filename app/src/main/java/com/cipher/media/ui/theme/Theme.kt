package com.cipher.media.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.cipher.media.ui.settings.theme.ThemeManager
import com.cipher.media.ui.settings.theme.model.AccentColor

private val CIPHERColorScheme = darkColorScheme(
    primary = CIPHERPrimary,
    onPrimary = CIPHEROnPrimary,
    primaryContainer = CIPHERPrimaryContainer,
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

private val CIPHERLightColorScheme = lightColorScheme(
    primary = CIPHERPrimary,
    onPrimary = Color.White,
    primaryContainer = CIPHERPrimaryDim,
    secondary = CIPHERSecondary,
    secondaryContainer = CIPHERSecondaryLight,
    tertiary = CIPHERTertiary,
    tertiaryContainer = CIPHERTertiaryDim,
    background = Color(0xFFF5F5F5),
    surface = Color.White,
    surfaceVariant = Color(0xFFE0E0E0),
    onBackground = Color(0xFF1E1E1E),
    onSurface = Color(0xFF1E1E1E),
    onSurfaceVariant = Color(0xFF666666),
    error = CIPHERError,
    errorContainer = Color(0xFFFFDAD6),
    onError = Color.White,
    outline = Color(0xFFBDBDBD),
    outlineVariant = Color(0xFFE0E0E0)
)

private val CIPHERShapes = Shapes(
    extraSmall = RoundedCornerShape(Corners.small),
    small = RoundedCornerShape(Corners.small),
    medium = RoundedCornerShape(Corners.medium),
    large = RoundedCornerShape(Corners.large),
    extraLarge = RoundedCornerShape(Corners.extraLarge)
)

@Composable
fun CIPHERTheme(
    themeId: String = "dark",
    accentColorName: String = "SAFFRON",
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val themeManager = ThemeManager(context)
    
    val accentColor = try {
        AccentColor.valueOf(accentColorName)
    } catch (e: Exception) {
        AccentColor.SAFFRON
    }
    
    val colorScheme = themeManager.buildColorScheme(themeId, accentColor)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CIPHERTypography,
        shapes = CIPHERShapes,
        content = content
    )
}
