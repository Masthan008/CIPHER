package com.cipher.media.ui.settings.theme.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cipher.media.data.model.audiofx.Tier
import com.cipher.media.ui.settings.theme.model.NowPlayingLayout
import com.cipher.media.ui.theme.*

/**
 * Now Playing layout picker showing layout preview cards.
 */
@Composable
fun NowPlayingLayoutPicker(
    selectedLayout: NowPlayingLayout,
    userTier: Tier,
    onLayoutSelected: (NowPlayingLayout) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            "Now Playing Layout",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = CIPHEROnSurface
        )
        Spacer(Modifier.height(Spacing.sm))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(NowPlayingLayout.entries) { layout ->
                val isSelected = layout == selectedLayout
                val isLocked = userTier == Tier.FREE && !NowPlayingLayout.FREE_LAYOUTS.contains(layout)

                Card(
                    modifier = Modifier
                        .width(90.dp)
                        .clickable { if (!isLocked) onLayoutSelected(layout) }
                        .then(
                            if (isSelected) Modifier.border(2.dp, CIPHERPrimary, RoundedCornerShape(12.dp))
                            else Modifier
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CIPHERSurface)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            when (layout) {
                                NowPlayingLayout.STANDARD -> Icons.Default.ViewAgenda
                                NowPlayingLayout.COMPACT -> Icons.Default.ViewCompact
                                NowPlayingLayout.MINIMAL -> Icons.Default.Fullscreen
                                NowPlayingLayout.SPLIT -> Icons.Default.ViewStream
                                NowPlayingLayout.BLUR -> Icons.Default.BlurCircular
                                NowPlayingLayout.CARD -> Icons.Default.CreditCard
                                NowPlayingLayout.VINYL -> Icons.Default.Album
                                NowPlayingLayout.GRADIENT -> Icons.Default.FilterDrama
                            },
                            contentDescription = layout.label,
                            tint = if (isSelected) CIPHERPrimary else CIPHEROnSurfaceVariant,
                            modifier = Modifier.size(28.dp)
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            layout.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) CIPHERPrimary else CIPHEROnSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        if (isLocked) {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = CIPHEROnSurfaceVariant,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
