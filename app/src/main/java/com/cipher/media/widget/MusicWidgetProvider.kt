package com.cipher.media.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.cipher.media.R
import com.cipher.media.MainActivity

/**
 * FREE FEATURE: Home screen music widget (4x1 and 4x2 sizes).
 * Displays album art, track title/artist, and play/pause/skip controls.
 * Communicates with AudioPlaybackService via broadcast intents.
 */
class MusicWidgetProvider : AppWidgetProvider() {

    companion object {
        const val ACTION_PLAY_PAUSE = "com.cipher.media.WIDGET_PLAY_PAUSE"
        const val ACTION_NEXT = "com.cipher.media.WIDGET_NEXT"
        const val ACTION_PREV = "com.cipher.media.WIDGET_PREV"

        /**
         * Call this from the service whenever track info changes
         * to keep the widget in sync.
         */
        fun updateWidget(context: Context, title: String, artist: String, isPlaying: Boolean) {
            val manager = AppWidgetManager.getInstance(context)
            val component = ComponentName(context, MusicWidgetProvider::class.java)
            val ids = manager.getAppWidgetIds(component)

            for (id in ids) {
                val views = buildRemoteViews(context, title, artist, isPlaying)
                manager.updateAppWidget(id, views)
            }
        }

        private fun buildRemoteViews(
            context: Context,
            title: String,
            artist: String,
            isPlaying: Boolean
        ): RemoteViews {
            val views = RemoteViews(context.packageName, R.layout.widget_music_player)

            views.setTextViewText(R.id.widget_title, title)
            views.setTextViewText(R.id.widget_artist, artist)
            views.setImageViewResource(
                R.id.widget_play_pause,
                if (isPlaying) R.drawable.ic_pause_widget else R.drawable.ic_play_widget
            )

            // Tap on widget body → open app
            val openIntent = Intent(context, MainActivity::class.java)
            val openPending = PendingIntent.getActivity(
                context, 0, openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, openPending)

            // Play/Pause button
            views.setOnClickPendingIntent(
                R.id.widget_play_pause,
                createActionIntent(context, ACTION_PLAY_PAUSE, 1)
            )

            // Next button
            views.setOnClickPendingIntent(
                R.id.widget_next,
                createActionIntent(context, ACTION_NEXT, 2)
            )

            // Previous button
            views.setOnClickPendingIntent(
                R.id.widget_prev,
                createActionIntent(context, ACTION_PREV, 3)
            )

            return views
        }

        private fun createActionIntent(context: Context, action: String, requestCode: Int): PendingIntent {
            val intent = Intent(context, MusicWidgetProvider::class.java).apply {
                this.action = action
            }
            return PendingIntent.getBroadcast(
                context, requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (id in appWidgetIds) {
            val views = buildRemoteViews(context, "CIPHER Music", "No track playing", false)
            appWidgetManager.updateAppWidget(id, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        when (intent.action) {
            ACTION_PLAY_PAUSE -> {
                // Send play/pause command to service via MediaController
                sendMediaCommand(context, "PLAY_PAUSE")
            }
            ACTION_NEXT -> {
                sendMediaCommand(context, "NEXT")
            }
            ACTION_PREV -> {
                sendMediaCommand(context, "PREV")
            }
        }
    }

    private fun sendMediaCommand(context: Context, command: String) {
        val serviceIntent = Intent(context, com.cipher.media.service.AudioPlaybackService::class.java).apply {
            action = "com.cipher.media.WIDGET_COMMAND"
            putExtra("command", command)
        }
        context.startService(serviceIntent)
    }
}
