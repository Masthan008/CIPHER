package com.cipher.media.data.model.audiofx

sealed class EQPreset(val name: String, val bands: List<Int>) {
    // Standard 10 bands for Pro
    object Flat : EQPreset("Flat", listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
    object BassBoost : EQPreset("Bass Boost", listOf(6, 6, 6, 0, 0, 0, 0, 0, 0, 0))
    object Vocal : EQPreset("Vocal", listOf(0, 0, 0, 1, 2, 3, 3, 2, 0, 0))
    object Treble : EQPreset("Treble", listOf(0, 0, 0, 0, 0, 0, 2, 4, 6, 6))
    object Rock : EQPreset("Rock", listOf(4, 3, 2, 0, -1, 0, 1, 2, 3, 4))
    object Pop : EQPreset("Pop", listOf(-1, 0, 1, 2, 3, 3, 2, 1, 0, -1))
    object Jazz : EQPreset("Jazz", listOf(2, 2, 1, 3, 3, 1, 0, 1, 2, 3))
    object Classical : EQPreset("Classical", listOf(0, 0, 0, 0, 0, 0, 1, 2, 2, 1))
    class Custom(bands: List<Int>) : EQPreset("Custom", bands)

    companion object {
        val ALL = listOf(Flat, BassBoost, Vocal, Treble, Rock, Pop, Jazz, Classical)
        val FREE_TIER = listOf(Flat, BassBoost, Vocal, Treble, Rock)
    }
}
