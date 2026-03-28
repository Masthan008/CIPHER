package com.cipher.media.ui.audio.lyrics.model

import androidx.compose.ui.graphics.Color

/**
 * Available visualizer rendering modes.
 */
enum class VisualizerType(val label: String) {
    SPECTRUM_BARS("Spectrum Bars"),
    WAVEFORM("Waveform"),
    CIRCULAR("Circular"),
    PARTICLE("Particle FX");

    companion object {
        val FREE_TYPES = listOf(SPECTRUM_BARS)
        val PRO_TYPES = entries.toList()
    }
}

/**
 * User-configurable visualizer settings.
 */
data class VisualizerSettings(
    val type: VisualizerType = VisualizerType.SPECTRUM_BARS,
    val barCount: Int = 32,
    val primaryColor: Color = Color(0xFF00E5FF),
    val secondaryColor: Color = Color(0xFF7C4DFF),
    val sensitivity: Float = 1.0f,
    val mirrorMode: Boolean = false
)
