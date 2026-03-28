package com.cipher.media.ui.settings.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import com.cipher.media.data.model.audiofx.Tier
import com.cipher.media.ui.settings.theme.model.AccentColor
import com.cipher.media.ui.theme.*

/**
 * Accent color picker — row of colored circles with selection indicator.
 */
@Composable
fun AccentColorPicker(
    colors: List<AccentColor>,
    selectedAccent: AccentColor,
    userTier: Tier,
    onAccentSelected: (AccentColor) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            "Accent Color",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = CIPHEROnSurface
        )
        Spacer(Modifier.height(Spacing.sm))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(colors) { accent ->
                val isSelected = accent == selectedAccent
                val isLocked = userTier == Tier.FREE && !AccentColor.FREE_COLORS.contains(accent)

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(accent.color)
                        .then(
                            if (isSelected) Modifier.border(3.dp, CIPHEROnSurface, CircleShape)
                            else Modifier.border(1.dp, CIPHERDivider, CircleShape)
                        )
                        .clickable { if (!isLocked) onAccentSelected(accent) },
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        isLocked -> Icon(
                            Icons.Default.Lock,
                            contentDescription = "Locked",
                            tint = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.size(18.dp)
                        )
                        isSelected -> Icon(
                            Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}
