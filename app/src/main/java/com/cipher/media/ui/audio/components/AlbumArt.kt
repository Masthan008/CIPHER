package com.cipher.media.ui.audio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cipher.media.ui.theme.*

/**
 * Album art composable with blurred background gradient effect.
 */
@Composable
fun AlbumArt(
    artUri: android.net.Uri?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Blurred background layer
        AsyncImage(
            model = artUri,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(60.dp),
            contentScale = ContentScale.Crop,
            alpha = 0.4f
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            CIPHERBackground.copy(alpha = 0.6f),
                            Color.Transparent,
                            CIPHERBackground
                        )
                    )
                )
        )

        // Main album art
        Box(
            modifier = Modifier
                .size(280.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(CIPHERSurfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = artUri,
                contentDescription = "Album art",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Fallback
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = CIPHEROnSurfaceVariant.copy(alpha = 0.3f)
            )
        }
    }
}
