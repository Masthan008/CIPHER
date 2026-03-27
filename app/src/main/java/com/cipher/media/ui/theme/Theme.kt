package com.cipher.media.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CIPHERColorScheme = darkColorScheme(
    primary = CIPHERPrimary,
    onPrimary = CIPHEROnPrimary,
    secondary = CIPHERSecondary,
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
    outline = CIPHEROutline,
    outlineVariant = CIPHEROutlineVariant
)

@Composable
fun CIPHERTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CIPHERColorScheme,
        typography = CIPHERTypography,
        content = content
    )
}
