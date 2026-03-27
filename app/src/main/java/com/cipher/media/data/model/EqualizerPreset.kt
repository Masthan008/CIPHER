package com.cipher.media.data.model

/**
 * An equalizer preset with gain values for 10 bands.
 * Gain range: -15dB to +15dB (stored as millibels internally: -1500 to +1500).
 * Bands: 31Hz, 63Hz, 125Hz, 250Hz, 500Hz, 1kHz, 2kHz, 4kHz, 8kHz, 16kHz
 */
data class EqualizerPreset(
    val name: String,
    val bandGains: List<Int> // 10 values in millibels (-1500 to 1500)
) {
    companion object {
        val BAND_LABELS = listOf("31", "63", "125", "250", "500", "1K", "2K", "4K", "8K", "16K")
        const val MIN_GAIN_MB = -1500
        const val MAX_GAIN_MB = 1500
        const val NUM_BANDS = 10

        val FLAT = EqualizerPreset("Flat", List(10) { 0 })
        val BASS_BOOST = EqualizerPreset("Bass Boost", listOf(1000, 800, 600, 300, 0, 0, 0, 0, 0, 0))
        val VOCAL = EqualizerPreset("Vocal", listOf(-300, -200, 0, 400, 800, 800, 400, 0, -200, -300))
        val TREBLE = EqualizerPreset("Treble", listOf(0, 0, 0, 0, 0, 300, 600, 800, 1000, 1200))
        val ROCK = EqualizerPreset("Rock", listOf(700, 500, 0, -200, -400, 0, 300, 600, 800, 900))
        val POP = EqualizerPreset("Pop", listOf(-200, 200, 500, 700, 500, 0, -200, -300, 200, 400))
        val JAZZ = EqualizerPreset("Jazz", listOf(400, 300, 0, 200, -200, -200, 0, 200, 500, 600))
        val CLASSICAL = EqualizerPreset("Classical", listOf(500, 400, 200, 0, -200, -200, 0, 300, 500, 600))

        val ALL_PRESETS = listOf(FLAT, BASS_BOOST, VOCAL, TREBLE, ROCK, POP, JAZZ, CLASSICAL)
    }
}
