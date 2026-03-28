package com.cipher.media.ui.audio.audiofx

import android.content.Context
import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer
import android.media.audiofx.LoudnessEnhancer
import android.media.audiofx.PresetReverb
import android.media.audiofx.Virtualizer
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AudioEffectsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var equalizer: Equalizer? = null
    private var bassBoost: BassBoost? = null
    private var virtualizer: Virtualizer? = null
    private var reverb: PresetReverb? = null
    private var loudnessEnhancer: LoudnessEnhancer? = null
    
    // Virtual 10-band centers in milliHertz
    private val virtualCenterFreqs = listOf(
        31000, 63000, 125000, 250000, 500000, 
        1000000, 2000000, 4000000, 8000000, 16000000
    )

    fun attachToAudioSession(audioSessionId: Int) {
        if (audioSessionId == 0) return
        release()
        try {
            equalizer = Equalizer(0, audioSessionId).apply { enabled = true }
            bassBoost = BassBoost(0, audioSessionId).apply { enabled = true }
            virtualizer = Virtualizer(0, audioSessionId).apply { enabled = true }
            reverb = PresetReverb(0, audioSessionId).apply { enabled = false }
            loudnessEnhancer = LoudnessEnhancer(audioSessionId).apply { enabled = true }
        } catch (e: Exception) {
            Log.e("AudioFxManager", "Failed to init effects. Device might lack hardware DSP.", e)
        }
    }

    fun release() {
        equalizer?.release()
        bassBoost?.release()
        virtualizer?.release()
        reverb?.release()
        loudnessEnhancer?.release()
        equalizer = null
        bassBoost = null
        virtualizer = null
        reverb = null
        loudnessEnhancer = null
    }

    /**
     * Map virtual 10-band slider to the closest native hardware band.
     * @param virtualBand 0-9
     * @param levelDb -15 to +15 dB
     */
    fun setBandLevel(virtualBand: Int, levelDb: Int) {
        val eq = equalizer ?: return
        if (virtualBand !in 0..9) return
        
        val numNativeBands = eq.numberOfBands.toInt()
        val requestedFreqMilliHz = virtualCenterFreqs[virtualBand]
        
        var bestNativeBand: Short = 0
        var minDiff = Int.MAX_VALUE
        for (i in 0 until numNativeBands) {
            val nativeFreq = eq.getCenterFreq(i.toShort())
            val diff = kotlin.math.abs(nativeFreq - requestedFreqMilliHz)
            if (diff < minDiff) {
                minDiff = diff
                bestNativeBand = i.toShort()
            }
        }
        
        val levelMillibels = (levelDb * 100).toShort().coerceIn(
            eq.bandLevelRange[0], eq.bandLevelRange[1]
        )
        
        try {
            eq.setBandLevel(bestNativeBand, levelMillibels)
        } catch (e: Exception) {
            Log.e("AudioFxManager", "Error setting EQ band", e)
        }
    }

    fun setBassBoost(strength: Int) { // 0-1000
        try {
            bassBoost?.takeIf { it.strengthSupported }?.apply {
                enabled = strength > 0
                setStrength(strength.toShort())
            }
        } catch(e: Exception) {}
    }

    fun setVirtualizer(strength: Int) { // 0-1000
        try {
            virtualizer?.takeIf { it.strengthSupported }?.apply {
                enabled = strength > 0
                setStrength(strength.toShort())
            }
        } catch(e: Exception) {}
    }

    fun setReverbPreset(preset: Short) {
        try {
            reverb?.apply {
                enabled = preset != PresetReverb.PRESET_NONE
                this.preset = preset
            }
        } catch(e: Exception) {}
    }
}
