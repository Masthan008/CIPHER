package com.cipher.media.ui.audio.lyrics

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Extracts dominant colors from album art for dynamic theming.
 * Uses Android Palette API + Coil for image loading.
 */
@Singleton
class AlbumColorExtractor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    data class AlbumColors(
        val dominant: Color = Color(0xFF1E1E1E),
        val vibrant: Color = Color(0xFF00E5FF),
        val muted: Color = Color(0xFF424242),
        val darkVibrant: Color = Color(0xFF006064),
        val lightVibrant: Color = Color(0xFF80DEEA),
        val darkMuted: Color = Color(0xFF212121)
    )

    /**
     * Extract colors from album art URI.
     */
    suspend fun extractColors(albumArtUri: Uri?): AlbumColors = withContext(Dispatchers.IO) {
        if (albumArtUri == null) return@withContext AlbumColors()

        try {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(albumArtUri)
                .allowHardware(false)
                .size(128) // Small for faster palette extraction
                .build()

            val result = loader.execute(request)
            if (result !is SuccessResult) return@withContext AlbumColors()

            val bitmap = (result.drawable as? BitmapDrawable)?.bitmap
                ?: return@withContext AlbumColors()

            val palette = Palette.from(bitmap).generate()

            AlbumColors(
                dominant = palette.getDominantColor(0xFF1E1E1E.toInt()).toComposeColor(),
                vibrant = palette.getVibrantColor(0xFF00E5FF.toInt()).toComposeColor(),
                muted = palette.getMutedColor(0xFF424242.toInt()).toComposeColor(),
                darkVibrant = palette.getDarkVibrantColor(0xFF006064.toInt()).toComposeColor(),
                lightVibrant = palette.getLightVibrantColor(0xFF80DEEA.toInt()).toComposeColor(),
                darkMuted = palette.getDarkMutedColor(0xFF212121.toInt()).toComposeColor()
            )
        } catch (_: Exception) {
            AlbumColors()
        }
    }

    private fun Int.toComposeColor(): Color = Color(this)
}
