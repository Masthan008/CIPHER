package com.cipher.media.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.cipher.media.MainActivity

/**
 * Glance-based home screen widget for audio player.
 * Shows currently playing track info with play/pause control.
 */
class AudioPlayerWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val prefs = context.getSharedPreferences("cipher_widget", Context.MODE_PRIVATE)
        val trackTitle = prefs.getString("current_track", "No track playing") ?: "No track playing"
        val artistName = prefs.getString("current_artist", "CIPHER") ?: "CIPHER"
        val isPlaying = prefs.getBoolean("is_playing", false)

        provideContent {
            AudioWidgetContent(
                trackTitle = trackTitle,
                artistName = artistName,
                isPlaying = isPlaying
            )
        }
    }
}

@Composable
private fun AudioWidgetContent(
    trackTitle: String,
    artistName: String,
    isPlaying: Boolean
) {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ImageProvider(R.drawable.widget_background))
            .clickable(actionStartActivity<MainActivity>())
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Track info
            Column(
                modifier = GlanceModifier.defaultWeight()
            ) {
                Text(
                    text = trackTitle,
                    style = TextStyle(
                        color = ColorProvider(android.graphics.Color.WHITE),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1
                )
                Text(
                    text = artistName,
                    style = TextStyle(
                        color = ColorProvider(android.graphics.Color.parseColor("#DBC2B0")),
                        fontSize = 12.sp
                    ),
                    maxLines = 1
                )
            }

            // Play/pause icon
            Text(
                text = if (isPlaying) "⏸" else "▶",
                style = TextStyle(
                    color = ColorProvider(android.graphics.Color.parseColor("#FF9933")),
                    fontSize = 24.sp
                )
            )
        }
    }
}

/**
 * Widget provider that registers the widget with the system.
 */
class AudioPlayerWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = AudioPlayerWidget()
}
