package com.cipher.media.ui.settings.theme.components

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cipher.media.data.model.audiofx.Tier
import com.cipher.media.ui.theme.*

/**
 * Listening stats dashboard (Pro feature).
 * Tracks total listening time, songs played, most played artists.
 */
@Composable
fun StatsDashboard(
    userTier: Tier,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("cipher_stats", Context.MODE_PRIVATE) }

    val totalMinutes = remember { prefs.getLong("total_listening_ms", 0L) / 60_000 }
    val totalSongs = remember { prefs.getInt("total_songs_played", 0) }
    val streakDays = remember { prefs.getInt("streak_days", 0) }
    val topArtist = remember { prefs.getString("top_artist", "—") ?: "—" }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Listening Stats",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = CIPHEROnSurface
            )
            if (userTier == Tier.FREE) {
                AssistChip(
                    onClick = {},
                    label = { Text("PRO", style = MaterialTheme.typography.labelSmall) },
                    leadingIcon = { Icon(Icons.Default.Lock, null, modifier = Modifier.size(14.dp)) }
                )
            }
        }

        Spacer(Modifier.height(Spacing.md))

        if (userTier == Tier.FREE) {
            Card(
                colors = CardDefaults.cardColors(containerColor = CIPHERPrimary.copy(alpha = 0.1f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Upgrade to Pro to unlock listening stats, play history, and trends",
                    modifier = Modifier.padding(Spacing.md),
                    style = MaterialTheme.typography.bodyMedium,
                    color = CIPHERPrimary
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                StatCard("Listening", formatMinutes(totalMinutes), Icons.Default.Headphones, Modifier.weight(1f))
                StatCard("Songs", "$totalSongs", Icons.Default.MusicNote, Modifier.weight(1f))
            }

            Spacer(Modifier.height(Spacing.sm))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                StatCard("Streak", "$streakDays days", Icons.Default.LocalFireDepartment, Modifier.weight(1f))
                StatCard("Top Artist", topArtist, Icons.Default.Person, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(Corners.medium),
        colors = CardDefaults.cardColors(containerColor = CIPHERSurface)
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = CIPHERPrimary, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(4.dp))
            Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
            Text(label, style = MaterialTheme.typography.labelSmall, color = CIPHEROnSurfaceVariant)
        }
    }
}

private fun formatMinutes(totalMin: Long): String = when {
    totalMin >= 1440 -> "${totalMin / 1440}d ${(totalMin % 1440) / 60}h"
    totalMin >= 60 -> "${totalMin / 60}h ${totalMin % 60}m"
    else -> "${totalMin}m"
}

/**
 * Call this from AudioPlayerViewModel every time a song plays.
 */
object StatsTracker {
    fun recordSongPlayed(context: Context, durationMs: Long, artist: String) {
        val prefs = context.getSharedPreferences("cipher_stats", Context.MODE_PRIVATE)
        prefs.edit().apply {
            putLong("total_listening_ms", prefs.getLong("total_listening_ms", 0L) + durationMs)
            putInt("total_songs_played", prefs.getInt("total_songs_played", 0) + 1)

            // Simple top artist tracking
            val artistKey = "artist_count_$artist"
            val count = prefs.getInt(artistKey, 0) + 1
            putInt(artistKey, count)

            val topCount = prefs.getInt("top_count", 0)
            if (count > topCount) {
                putInt("top_count", count)
                putString("top_artist", artist)
            }
            apply()
        }
    }
}
