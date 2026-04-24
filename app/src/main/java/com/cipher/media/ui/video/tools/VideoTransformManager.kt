package com.cipher.media.ui.video.tools

import android.view.View
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.cipher.media.billing.ProFeatureGate
import com.cipher.media.billing.Tier
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Feature 15: Video Crop/Zoom (₹99/month)
 * Custom aspect ratios and gestures
 */
@Singleton
class VideoTransformManager @Inject constructor(
    private val proFeatureGate: ProFeatureGate
) {
    private var currentTier: Tier = Tier.FREE
    private var currentMode: CropMode = CropMode.FIT
    private var scale = 1f
    private var translateX = 0f
    private var translateY = 0f

    fun setUserTier(tier: Tier) {
        currentTier = tier
    }

    fun setCropMode(mode: CropMode, playerView: PlayerView) {
        if (!proFeatureGate.checkAccess(currentTier) && mode != CropMode.FIT) return

        currentMode = mode
        playerView.resizeMode = when (mode) {
            CropMode.FIT -> AspectRatioFrameLayout.RESIZE_MODE_FIT
            CropMode.CROP -> AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            CropMode.STRETCH -> AspectRatioFrameLayout.RESIZE_MODE_FILL
            CropMode.ASPECT_16_9, CropMode.ASPECT_4_3, CropMode.ASPECT_21_9 -> AspectRatioFrameLayout.RESIZE_MODE_FIT
        }
    }

    fun zoom(scaleFactor: Float, focusX: Float, focusY: Float) {
        if (!proFeatureGate.checkAccess(currentTier)) return

        val newScale = (scale * scaleFactor).coerceIn(1f, 3f)
        translateX += (focusX - translateX) * (1 - scaleFactor)
        translateY += (focusY - translateY) * (1 - scaleFactor)
        scale = newScale
        constrainTranslation()
    }

    fun pan(deltaX: Float, deltaY: Float) {
        if (!proFeatureGate.checkAccess(currentTier) || scale <= 1f) return

        translateX += deltaX
        translateY += deltaY
        constrainTranslation()
    }

    private fun constrainTranslation() {
        // Keep within bounds based on scale
        val maxTranslate = (scale - 1) * 500 // Simplified
        translateX = translateX.coerceIn(-maxTranslate, maxTranslate)
        translateY = translateY.coerceIn(-maxTranslate, maxTranslate)
    }

    fun reset() {
        scale = 1f
        translateX = 0f
        translateY = 0f
    }

    fun getCurrentTransform(): Triple<Float, Float, Float> = Triple(scale, translateX, translateY)
}

enum class CropMode {
    FIT,        // Free
    CROP,       // Pro
    STRETCH,    // Pro
    ASPECT_16_9, // Pro
    ASPECT_4_3,  // Pro
    ASPECT_21_9  // Pro
}
