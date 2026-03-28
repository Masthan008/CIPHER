package com.cipher.media.ui.settings.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cipher.media.data.model.audiofx.Tier
import com.cipher.media.ui.settings.theme.model.ThemeConfig
import com.cipher.media.ui.settings.theme.model.ThemeTier
import com.cipher.media.ui.theme.*

/**
 * Horizontal scrollable theme card selector.
 */
@Composable
fun ThemeSelector(
    themes: List<ThemeConfig>,
    selectedThemeId: String,
    userTier: Tier,
    onThemeSelected: (ThemeConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            "Theme",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = CIPHEROnSurface
        )
        Spacer(Modifier.height(Spacing.sm))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(themes) { theme ->
                val isSelected = theme.id == selectedThemeId
                val isLocked = when (theme.tier) {
                    ThemeTier.FREE -> false
                    ThemeTier.PRO -> userTier == Tier.FREE
                    ThemeTier.POWER -> userTier != Tier.POWER
                }

                Card(
                    modifier = Modifier
                        .width(100.dp)
                        .clickable { if (!isLocked) onThemeSelected(theme) }
                        .then(
                            if (isSelected) Modifier.border(2.dp, CIPHERPrimary, RoundedCornerShape(12.dp))
                            else Modifier
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = theme.surface)
                ) {
                    Box(Modifier.fillMaxWidth().height(60.dp).background(theme.background)) {
                        // Preview dots
                        Row(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(Modifier.size(12.dp).clip(CircleShape).background(theme.primary))
                            Box(Modifier.size(12.dp).clip(CircleShape).background(theme.onSurface.copy(alpha = 0.5f)))
                            Box(Modifier.size(12.dp).clip(CircleShape).background(theme.surfaceVariant))
                        }

                        if (isLocked) {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Locked",
                                tint = Color.White.copy(alpha = 0.6f),
                                modifier = Modifier.align(Alignment.TopEnd).padding(4.dp).size(14.dp)
                            )
                        }
                        if (isSelected) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = theme.primary,
                                modifier = Modifier.align(Alignment.TopStart).padding(4.dp).size(16.dp)
                            )
                        }
                    }
                    Text(
                        theme.name,
                        style = MaterialTheme.typography.labelSmall,
                        color = theme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
