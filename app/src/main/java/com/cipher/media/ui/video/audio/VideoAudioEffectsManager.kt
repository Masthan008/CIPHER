package com.cipher.media.ui.video.audio

import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer
import android.media.audiofx.LoudnessEnhancer
import android.media.audiofx.PresetReverb
import android.media.audiofx.Virtualizer
import android.util.Log

/**
 * Manages advanced Android audio effects attached to a given audio session ID.
 * Maps 10 logical UI bands to the device's physical equalizer bands.
 */
class VideoAudioEffectsManager {

    private var equalizer: Equalizer? = null
    private var bassBoost: BassBoost? = null
    private var virtualizer: Virtualizer? = null
    private var loudnessEnhancer: LoudnessEnhancer? = null
    private var presetReverb: PresetReverb? = null

    private val TAG = "VideoAudioFxManager"

    // Map of 10 logical bands (31Hz to 16kHz)
    private val logicalFrequencies = intArrayOf(
        31000, 63000, 125000, 250000, 500000, 1000000, 2000000, 4000000, 8000000, 16000000
    )

    fun attach(audioSessionId: Int) {
        release() // Clean up existing

        try {
            equalizer = Equalizer(0, audioSessionId).apply { enabled = true }
        } catch (e: Exception) { Log.e(TAG, "Equalizer error", e) }

        try {
            bassBoost = BassBoost(0, audioSessionId).apply { enabled = true; setStrength(0) }
        } catch (e: Exception) { Log.e(TAG, "BassBoost error", e) }

        try {
            virtualizer = Virtualizer(0, audioSessionId).apply { enabled = true; setStrength(0) }
        } catch (e: Exception) { Log.e(TAG, "Virtualizer error", e) }

        try {
            loudnessEnhancer = LoudnessEnhancer(audioSessionId).apply { enabled = true; setTargetGain(0) }
        } catch (e: Exception) { Log.e(TAG, "LoudnessEnhancer error", e) }

        try {
            presetReverb = PresetReverb(0, audioSessionId).apply { enabled = true; preset = PresetReverb.PRESET_NONE }
        } catch (e: Exception) { Log.e(TAG, "PresetReverb error", e) }
    }

    /**
     * Set gain for a logical 10-band index (0-9).
     * We map this logical 10-band index to the closest physical device band.
     */
    fun setLogicalBandLevel(logicalIndex: Int, gainMillibels: Int) {
        val eq = equalizer ?: return
        if (logicalIndex !in logicalFrequencies.indices) return

        try {
            val numBands = eq.numberOfBands
            if (numBands == 0.toShort()) return

            val targetFreq = logicalFrequencies[logicalIndex]
            
            // Find the closest physical band
            var closestPhysicalBand: Short = 0
            var minDiff = Int.MAX_VALUE
            for (i in 0 until numBands) {
                val centerFreq = eq.getCenterFreq(i.toShort())
                val diff = kotlin.math.abs(centerFreq - targetFreq)
                if (diff < minDiff) {
                    minDiff = diff
                    closestPhysicalBand = i.toShort()
                }
            }

            val minLevel = eq.bandLevelRange[0]
            val maxLevel = eq.bandLevelRange[1]
            val clampedGain = gainMillibels.toShort().coerceIn(minLevel, maxLevel)
            
            // Apply gain directly to the closest physical band. Multiple logical bands might map to the same physical band.
            eq.setBandLevel(closestPhysicalBand, clampedGain)
            
        } catch (e: Exception) {
            Log.e(TAG, "setLogicalBandLevel error", e)
        }
    }

    fun apply10BandGains(gainsMillibels: List<Int>) {
        if (gainsMillibels.size != 10) return
        
        // Reset physical bands to 0 first, as multiple logical bands might map to one physical
        val eq = equalizer ?: return
        try {
            for (i in 0 until eq.numberOfBands) {
                eq.setBandLevel(i.toShort(), 0)
            }
        } catch (e: Exception) { }

        // Apply new gains (closer bands override)
        gainsMillibels.forEachIndexed { index, gain ->
            setLogicalBandLevel(index, gain)
        }
    }

    fun setBassBoostStrength(strength: Int) {
        try { bassBoost?.setStrength(strength.toShort().coerceIn(0, 1000)) } 
        catch (e: Exception) { Log.e(TAG, "BassBoost", e) }
    }

    fun setVirtualizerStrength(strength: Int) {
        try { virtualizer?.setStrength(strength.toShort().coerceIn(0, 1000)) } 
        catch (e: Exception) { Log.e(TAG, "Virtualizer", e) }
    }

    fun setLoudnessEnhancerGain(gainMb: Int) {
        try { loudnessEnhancer?.setTargetGain(gainMb) } 
        catch (e: Exception) { Log.e(TAG, "Loudness", e) }
    }

    fun setReverbPreset(preset: Short) {
        try { presetReverb?.preset = preset } 
        catch (e: Exception) { Log.e(TAG, "Reverb", e) }
    }

    fun release() {
        try { equalizer?.release() } catch (e: Exception) { }
        try { bassBoost?.release() } catch (e: Exception) { }
        try { virtualizer?.release() } catch (e: Exception) { }
        try { loudnessEnhancer?.release() } catch (e: Exception) { }
        try { presetReverb?.release() } catch (e: Exception) { }
        
        equalizer = null
        bassBoost = null
        virtualizer = null
        loudnessEnhancer = null
        presetReverb = null
    }
}
