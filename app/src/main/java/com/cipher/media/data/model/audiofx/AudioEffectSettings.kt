package com.cipher.media.data.model.audiofx

data class AudioEffectSettings(
    val enabled: Boolean = false,
    val selectedPreset: EQPreset = EQPreset.Flat,
    val customBands: List<Int> = List(10) { 0 }, // -15 to +15 dB
    val bassBoostStrength: Int = 0, // 0 to 1000
    val virtualizerStrength: Int = 0, // 0 to 1000
    val reverbPreset: Short = 0, // EnvironmentalReverb.PRESET_NONE
    val crossfadeDurationMs: Long = 0L,
    val replayGainEnabled: Boolean = false,
    val hiResEnabled: Boolean = false
)
