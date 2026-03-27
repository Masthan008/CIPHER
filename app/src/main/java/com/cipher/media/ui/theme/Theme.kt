package com.cipher.media.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

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

private val CIPHERShapes = Shapes(
    extraSmall = RoundedCornerShape(Corners.small),
    small = RoundedCornerShape(Corners.small),
    medium = RoundedCornerShape(Corners.medium),
    large = RoundedCornerShape(Corners.large),
    extraLarge = RoundedCornerShape(Corners.extraLarge)
)

@Composable
fun CIPHERTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CIPHERColorScheme,
        typography = CIPHERTypography,
        shapes = CIPHERShapes,
        content = content
    )
}
