package com.cipher.media.ui.video.audio.model

/**
 * Pre-defined 10-band equalizer profiles.
 * Values are stored in millibels (mB), so 1000 = 10dB.
 */
data class EQPreset(
    val name: String,
    val gainsMillibels: List<Int>
) {
    init {
        require(gainsMillibels.size == 10) { "EQ preset must define exactly 10 band gains." }
    }

    companion object {
        val FLAT = EQPreset(
            name = "Flat",
            gainsMillibels = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        )
        
        val BASS_BOOST = EQPreset(
            name = "Bass Boost",
            gainsMillibels = listOf(600, 500, 300, 0, 0, -100, -200, 0, 100, 200)
        )

        val VOCAL_BOOST = EQPreset(
            name = "Vocal Boost",
            gainsMillibels = listOf(-200, -100, 0, 200, 400, 400, 300, 100, 0, -100)
        )

        val TREBLE_BOOST = EQPreset(
            name = "Treble Boost",
            gainsMillibels = listOf(0, 0, -100, -200, 0, 100, 300, 500, 600, 600)
        )

        val ROCK = EQPreset(
            name = "Rock",
            gainsMillibels = listOf(500, 400, 300, 100, -100, -100, 200, 300, 400, 500)
        )

        val POP = EQPreset(
            name = "Pop",
            gainsMillibels = listOf(-200, -100, 0, 200, 400, 400, 200, 0, -100, -200)
        )

        val JAZZ = EQPreset(
            name = "Jazz",
            gainsMillibels = listOf(300, 200, 100, 200, -100, -200, 0, 100, 200, 300)
        )

        val CLASSICAL = EQPreset(
            name = "Classical",
            gainsMillibels = listOf(400, 300, 200, 100, -100, -100, 0, 200, 300, 400)
        )

        val ALL_PRESETS = listOf(
            FLAT, BASS_BOOST, VOCAL_BOOST, TREBLE_BOOST, 
            ROCK, POP, JAZZ, CLASSICAL
        )
    }
}
