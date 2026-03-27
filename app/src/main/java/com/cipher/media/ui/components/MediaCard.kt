package com.cipher.media.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cipher.media.ui.theme.*

/**
 * Media card with thumbnail, title, subtitle.
 */
@Composable
fun MediaCard(
    title: String,
    subtitle: String,
    thumbnailUri: Any?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    duration: String? = null,
    badge: String? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(Corners.medium),
        colors = CardDefaults.cardColors(containerColor = CIPHERSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = Elevation.level1)
    ) {
        Column {
            // Thumbnail
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(topStart = Corners.medium, topEnd = Corners.medium))
                    .background(CIPHERSurfaceVariant)
            ) {
                AsyncImage(
                    model = thumbnailUri,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Duration badge
                if (duration != null) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(Spacing.sm),
                        shape = RoundedCornerShape(Corners.small),
                        color = Color.Black.copy(alpha = 0.7f)
                    ) {
                        Text(
                            text = duration,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White
                        )
                    }
                }

                // Top-left badge (e.g., "NEW", "LIVE")
                if (badge != null) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(Spacing.sm),
                        shape = RoundedCornerShape(Corners.small),
                        color = CIPHERPrimary
                    ) {
                        Text(
                            text = badge,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = CIPHEROnPrimary
                        )
                    }
                }
            }

            // Info
            Column(modifier = Modifier.padding(Spacing.cardPadding)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = CIPHEROnSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(Spacing.xs))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = CIPHEROnSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Horizontal list item card (80dp height).
 */
@Composable
fun MediaListItem(
    title: String,
    subtitle: String,
    thumbnailUri: Any?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    duration: String? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(Corners.medium),
        colors = CardDefaults.cardColors(containerColor = CIPHERSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(Corners.medium))
                    .background(CIPHERSurfaceVariant)
            ) {
                AsyncImage(
                    model = thumbnailUri,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                if (duration != null) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(2.dp),
                        shape = RoundedCornerShape(2.dp),
                        color = Color.Black.copy(alpha = 0.7f)
                    ) {
                        Text(
                            duration,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                            color = Color.White
                        )
                    }
                }
            }

            // Info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.sm)
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleSmall,
                    color = CIPHEROnSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(Spacing.xxs))
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = CIPHEROnSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            trailing?.invoke()
        }
    }
}
