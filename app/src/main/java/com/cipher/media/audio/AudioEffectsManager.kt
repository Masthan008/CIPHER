package com.cipher.media.audio

import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer
import android.media.audiofx.Virtualizer
import android.util.Log

/**
 * Manages Android audio effects (Equalizer, BassBoost, Virtualizer)
 * attached to a given audio session ID from ExoPlayer.
 */
class AudioEffectsManager {

    private var equalizer: Equalizer? = null
    private var bassBoost: BassBoost? = null
    private var virtualizer: Virtualizer? = null

    private val TAG = "AudioEffectsManager"

    /**
     * Attach effects to the given audio session ID.
     * Must be called after ExoPlayer is created.
     */
    fun attach(audioSessionId: Int) {
        release() // Clean up any existing effects

        try {
            equalizer = Equalizer(0, audioSessionId).apply {
                enabled = true
            }
            Log.d(TAG, "Equalizer attached, bands: ${equalizer?.numberOfBands}")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create Equalizer: ${e.message}")
        }

        try {
            bassBoost = BassBoost(0, audioSessionId).apply {
                enabled = true
                setStrength(0)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create BassBoost: ${e.message}")
        }

        try {
            virtualizer = Virtualizer(0, audioSessionId).apply {
                enabled = true
                setStrength(0)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create Virtualizer: ${e.message}")
        }
    }

    /**
     * Set the gain (in millibels, -1500 to +1500) for a band index.
     * The actual number of bands depends on the device; we map our 10-band
     * model to the device's available bands.
     */
    fun setBandLevel(bandIndex: Int, gainMillibels: Int) {
        val eq = equalizer ?: return
        val numBands = eq.numberOfBands.toInt()
        if (bandIndex < numBands) {
            try {
                val minLevel = eq.bandLevelRange[0]
                val maxLevel = eq.bandLevelRange[1]
                val clampedGain = gainMillibels.toShort().coerceIn(minLevel, maxLevel)
                eq.setBandLevel(bandIndex.toShort(), clampedGain)
            } catch (e: Exception) {
                Log.e(TAG, "setBandLevel error: ${e.message}")
            }
        }
    }

    /**
     * Apply all 10 band gains at once.
     */
    fun applyBandGains(gains: List<Int>) {
        val eq = equalizer ?: return
        val numBands = eq.numberOfBands.toInt()
        for (i in 0 until minOf(gains.size, numBands)) {
            setBandLevel(i, gains[i])
        }
    }

    /**
     * Set bass boost strength (0-1000).
     */
    fun setBassBoostStrength(strength: Int) {
        try {
            bassBoost?.setStrength(strength.toShort().coerceIn(0, 1000))
        } catch (e: Exception) {
            Log.e(TAG, "BassBoost error: ${e.message}")
        }
    }

    /**
     * Set virtualizer strength (0-1000).
     */
    fun setVirtualizerStrength(strength: Int) {
        try {
            virtualizer?.setStrength(strength.toShort().coerceIn(0, 1000))
        } catch (e: Exception) {
            Log.e(TAG, "Virtualizer error: ${e.message}")
        }
    }

    fun release() {
        try {
            equalizer?.release()
            bassBoost?.release()
            virtualizer?.release()
        } catch (_: Exception) { }
        equalizer = null
        bassBoost = null
        virtualizer = null
    }
}
