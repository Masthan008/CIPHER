package com.cipher.media.ui.video.subtitle

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.video.subtitle.model.SubtitleFont
import com.cipher.media.ui.video.subtitle.model.SubtitlePosition

/**
 * Renders the active subtitle cue over the video player.
 */
@OptIn(ExperimentalTextApi::class)
@Composable
fun SubtitleRenderer(
    uiState: SubtitleUiState,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = uiState.isVisible && uiState.currentCueText != null,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier.fillMaxSize()
    ) {
        val alignment = when (uiState.style.position) {
            SubtitlePosition.TOP -> Alignment.TopCenter
            SubtitlePosition.MIDDLE -> Alignment.Center
            SubtitlePosition.BOTTOM -> Alignment.BottomCenter
        }

        val paddingOffset = when (uiState.style.position) {
            SubtitlePosition.TOP -> Modifier.padding(top = 48.dp)
            SubtitlePosition.MIDDLE -> Modifier.padding(0.dp)
            SubtitlePosition.BOTTOM -> Modifier.padding(bottom = 64.dp)
        }

        val fontFamily = when (uiState.style.fontFamily) {
            SubtitleFont.DEFAULT -> FontFamily.Default
            SubtitleFont.ROBOTO -> FontFamily.SansSerif
            SubtitleFont.OPEN_SANS -> FontFamily.SansSerif
            SubtitleFont.MONOSPACE -> FontFamily.Monospace
            SubtitleFont.SERIF -> FontFamily.Serif
            SubtitleFont.CURSIVE -> FontFamily.Cursive
        }

        // Base font size is 20sp, scaled by user percentage (e.g., 150%)
        val scaledFontSize = (20f * (uiState.style.fontSize / 100f)).sp

        Box(
            modifier = Modifier.fillMaxSize().then(paddingOffset),
            contentAlignment = alignment
        ) {
            uiState.currentCueText?.let { text ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(uiState.style.backgroundColor)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    // Draw text twice if outline is enabled to simulate a stroke
                    if (uiState.style.outlineEnabled) {
                        Text(
                            text = text,
                            color = Color.Black,
                            fontSize = scaledFontSize,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            style = TextStyle.Default.copy(
                                drawStyle = Stroke(
                                    miter = 10f,
                                    width = 4f,
                                    join = StrokeJoin.Round
                                )
                            )
                        )
                    }

                    Text(
                        text = text,
                        color = uiState.style.fontColor,
                        fontSize = scaledFontSize,
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
