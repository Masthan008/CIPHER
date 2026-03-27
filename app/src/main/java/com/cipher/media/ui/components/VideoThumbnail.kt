package com.cipher.media.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import com.cipher.media.data.model.MediaItem
import com.cipher.media.ui.theme.CIPHEROnSurface
import com.cipher.media.ui.theme.CIPHEROnSurfaceVariant
import com.cipher.media.ui.theme.CIPHERSurfaceVariant
import com.cipher.media.util.TimeUtil

/**
 * Video thumbnail card with preview image, duration badge, and metadata.
 */
@Composable
fun VideoThumbnail(
    mediaItem: MediaItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Thumbnail image area (16:9 aspect ratio)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(12.dp))
                .background(CIPHERSurfaceVariant)
        ) {
            // Load video frame thumbnail via Coil
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(mediaItem.uri)
                    .videoFrameMillis(1000) // Grab frame at 1 second
                    .crossfade(true)
                    .build(),
                contentDescription = mediaItem.displayName,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Duration badge
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(6.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.75f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = TimeUtil.formatDuration(mediaItem.duration),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
        }

        // Video name
        Text(
            text = mediaItem.displayName,
            style = MaterialTheme.typography.bodySmall,
            color = CIPHEROnSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        // File size
        Text(
            text = mediaItem.formattedSize,
            style = MaterialTheme.typography.labelSmall,
            color = CIPHEROnSurfaceVariant
        )
    }
}
