package com.cipher.media.service

import android.content.Intent
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.cipher.media.audio.AudioEffectsManager
import com.cipher.media.data.repository.EqualizerRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Background audio playback service using Media3 MediaSessionService.
 * Handles ExoPlayer lifecycle, MediaSession, notification controls, audio focus,
 * and real-time audio effects (Equalizer, BassBoost, Virtualizer).
 */
@AndroidEntryPoint
class AudioPlaybackService : MediaSessionService() {

    @Inject lateinit var eqRepository: EqualizerRepository

    private var mediaSession: MediaSession? = null
    private val audioEffects = AudioEffectsManager()
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        // FREE FEATURE: Full audio playback service with gapless support
        val player = ExoPlayer.Builder(this)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA)
                    .build(),
                /* handleAudioFocus = */ true  // FREE: Auto-handle focus (pause on call, duck on notification)
            )
            .setHandleAudioBecomingNoisy(true) // FREE: Pause when headphones unplugged
            .setWakeMode(C.WAKE_MODE_LOCAL)     // FREE: Prevent CPU sleep during background playback
            .build()

        // FREE FEATURE: Gapless playback — skip silence gaps between tracks
        player.skipSilenceEnabled = true

        // Attach audio effects to ExoPlayer's audio session
        audioEffects.attach(player.audioSessionId)

        // Observe saved EQ settings and apply them in real-time
        observeEqSettings()

        mediaSession = MediaSession.Builder(this, player)
            .build()
    }

    private fun observeEqSettings() {
        // Apply persisted band gains
        serviceScope.launch {
            eqRepository.bandGains.collect { gains ->
                audioEffects.applyBandGains(gains)
            }
        }

        // Apply persisted bass boost
        serviceScope.launch {
            eqRepository.bassBoostStrength.collect { strength ->
                audioEffects.setBassBoostStrength(strength)
            }
        }

        // Apply persisted virtualizer
        serviceScope.launch {
            eqRepository.virtualizerStrength.collect { strength ->
                audioEffects.setVirtualizerStrength(strength)
            }
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? {
        return mediaSession
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession?.player
        if (player == null || !player.playWhenReady || player.mediaItemCount == 0) {
            stopSelf()
        }
    }

    override fun onDestroy() {
        serviceScope.cancel()
        audioEffects.release()
        mediaSession?.run {
            player.release()
            release()
        }
        mediaSession = null
        super.onDestroy()
    }
}
