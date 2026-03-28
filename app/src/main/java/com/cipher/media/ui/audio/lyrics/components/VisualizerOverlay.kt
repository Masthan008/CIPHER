package com.cipher.media.ui.audio.lyrics.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.audio.lyrics.model.VisualizerSettings
import com.cipher.media.ui.audio.lyrics.model.VisualizerType
import kotlin.math.*

/**
 * Compose Canvas-based audio visualizer.
 * Renders FFT data in multiple styles: bars, waveform, circular, particles.
 *
 * @param fftData raw FFT magnitude array (0-255 per band), updated in real-time
 * @param waveformData raw waveform bytes (-128..127), updated in real-time
 * @param settings user-configurable visualizer options
 * @param height composable height
 */
@Composable
fun VisualizerOverlay(
    fftData: FloatArray,
    waveformData: ByteArray,
    settings: VisualizerSettings,
    modifier: Modifier = Modifier,
    height: Dp = 120.dp
) {
    // Smooth color animation for dynamic theming
    val primary by animateColorAsState(settings.primaryColor, tween(500))
    val secondary by animateColorAsState(settings.secondaryColor, tween(500))

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        when (settings.type) {
            VisualizerType.SPECTRUM_BARS -> drawSpectrumBars(fftData, primary, secondary, settings)
            VisualizerType.WAVEFORM -> drawWaveform(waveformData, primary, secondary)
            VisualizerType.CIRCULAR -> drawCircular(fftData, primary, secondary, settings)
            VisualizerType.PARTICLE -> drawParticles(fftData, primary, secondary, settings)
        }
    }
}

// ── Spectrum Bars ──
private fun DrawScope.drawSpectrumBars(
    fftData: FloatArray,
    primary: Color,
    secondary: Color,
    settings: VisualizerSettings
) {
    val barCount = minOf(settings.barCount, fftData.size)
    if (barCount == 0) return

    val barWidth = size.width / (barCount * 1.5f)
    val gap = barWidth * 0.5f
    val maxHeight = size.height * 0.9f

    for (i in 0 until barCount) {
        val magnitude = (fftData.getOrElse(i) { 0f } / 255f) * settings.sensitivity
        val barHeight = (magnitude * maxHeight).coerceIn(2f, maxHeight)
        val x = i * (barWidth + gap) + gap / 2

        val fraction = i.toFloat() / barCount
        val color = lerp(primary, secondary, fraction)

        // Main bar
        drawRoundRect(
            color = color,
            topLeft = Offset(x, size.height - barHeight),
            size = Size(barWidth, barHeight),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(barWidth / 3)
        )

        // Mirror mode
        if (settings.mirrorMode) {
            drawRoundRect(
                color = color.copy(alpha = 0.3f),
                topLeft = Offset(x, 0f),
                size = Size(barWidth, barHeight * 0.4f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(barWidth / 3)
            )
        }
    }
}

// ── Waveform ──
private fun DrawScope.drawWaveform(
    waveformData: ByteArray,
    primary: Color,
    secondary: Color
) {
    if (waveformData.isEmpty()) return

    val path = Path()
    val step = size.width / waveformData.size
    val centerY = size.height / 2

    waveformData.forEachIndexed { i, byte ->
        val x = i * step
        val y = centerY + (byte.toFloat() / 128f) * (size.height * 0.4f)
        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }

    drawPath(
        path = path,
        brush = Brush.horizontalGradient(listOf(primary, secondary)),
        style = Stroke(width = 3f, cap = StrokeCap.Round)
    )

    // Glow effect
    drawPath(
        path = path,
        brush = Brush.horizontalGradient(
            listOf(primary.copy(alpha = 0.15f), secondary.copy(alpha = 0.15f))
        ),
        style = Stroke(width = 8f, cap = StrokeCap.Round)
    )
}

// ── Circular ──
private fun DrawScope.drawCircular(
    fftData: FloatArray,
    primary: Color,
    secondary: Color,
    settings: VisualizerSettings
) {
    val cx = size.width / 2
    val cy = size.height / 2
    val baseRadius = minOf(cx, cy) * 0.4f
    val barCount = minOf(settings.barCount, fftData.size)
    if (barCount == 0) return

    val angleStep = 360f / barCount

    for (i in 0 until barCount) {
        val magnitude = (fftData.getOrElse(i) { 0f } / 255f) * settings.sensitivity
        val barLength = magnitude * baseRadius * 1.2f
        val angle = Math.toRadians((i * angleStep).toDouble())

        val startX = cx + (baseRadius * cos(angle)).toFloat()
        val startY = cy + (baseRadius * sin(angle)).toFloat()
        val endX = cx + ((baseRadius + barLength) * cos(angle)).toFloat()
        val endY = cy + ((baseRadius + barLength) * sin(angle)).toFloat()

        val fraction = i.toFloat() / barCount
        drawLine(
            color = lerp(primary, secondary, fraction),
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = 3f,
            cap = StrokeCap.Round
        )
    }

    // Center circle
    drawCircle(
        brush = Brush.radialGradient(
            listOf(primary.copy(alpha = 0.3f), Color.Transparent),
            center = Offset(cx, cy),
            radius = baseRadius
        )
    )
}

// ── Particles ──
private fun DrawScope.drawParticles(
    fftData: FloatArray,
    primary: Color,
    secondary: Color,
    settings: VisualizerSettings
) {
    val barCount = minOf(settings.barCount, fftData.size)
    if (barCount == 0) return

    for (i in 0 until barCount) {
        val magnitude = (fftData.getOrElse(i) { 0f } / 255f) * settings.sensitivity
        if (magnitude < 0.05f) continue

        val x = (i.toFloat() / barCount) * size.width
        val baseY = size.height

        // Multiple particles per bar
        val particleCount = (magnitude * 5).toInt().coerceIn(1, 8)
        for (p in 0 until particleCount) {
            val py = baseY - (magnitude * size.height * (0.3f + p * 0.15f))
            val radius = 2f + magnitude * 4f
            val alpha = (1f - p * 0.15f).coerceIn(0.1f, 0.9f)
            val fraction = i.toFloat() / barCount

            drawCircle(
                color = lerp(primary, secondary, fraction).copy(alpha = alpha),
                radius = radius,
                center = Offset(x, py)
            )
        }
    }
}

/** Linear color interpolation */
private fun lerp(a: Color, b: Color, fraction: Float): Color {
    val f = fraction.coerceIn(0f, 1f)
    return Color(
        red = a.red + (b.red - a.red) * f,
        green = a.green + (b.green - a.green) * f,
        blue = a.blue + (b.blue - a.blue) * f,
        alpha = a.alpha + (b.alpha - a.alpha) * f
    )
}
