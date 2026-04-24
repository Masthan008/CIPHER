package com.cipher.media.ui.video.quality

import android.content.Context
import android.view.Display
import android.view.WindowManager
import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import com.cipher.media.billing.ProFeatureGate
import com.cipher.media.billing.Tier
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Feature 1: 4K Video Playback (₹99/month)
 * Manages video quality selection with 4K support for Pro users
 */
@Singleton
@OptIn(UnstableApi::class)
class VideoQualityManager @Inject constructor(
    private val trackSelector: DefaultTrackSelector,
    private val proFeatureGate: ProFeatureGate,
    @ApplicationContext private val context: Context
) {
    private var currentTier: Tier = Tier.FREE

    fun setUserTier(tier: Tier) {
        currentTier = tier
    }

    val supportedQualities: List<VideoQuality>
        get() = buildList {
            add(VideoQuality.AUTO)
            add(VideoQuality.SD_480P)
            add(VideoQuality.HD_720P)
            add(VideoQuality.FHD_1080P)
            if (proFeatureGate.checkAccess(currentTier)) {
                add(VideoQuality.UHD_4K)
                add(VideoQuality.UHD_4K_HDR)
            }
        }

    fun setQuality(quality: VideoQuality) {
        if (quality.is4K && !proFeatureGate.checkAccess(currentTier)) {
            throw ProRequiredException("4K requires Pro subscription")
        }

        val params = trackSelector.buildUponParameters()
            .apply {
                when (quality) {
                    VideoQuality.AUTO -> clearVideoSizeConstraints()
                    VideoQuality.SD_480P -> setMaxVideoSize(854, 480)
                    VideoQuality.HD_720P -> setMaxVideoSize(1280, 720)
                    VideoQuality.FHD_1080P -> setMaxVideoSize(1920, 1080)
                    VideoQuality.UHD_4K -> setMaxVideoSize(3840, 2160)
                    VideoQuality.UHD_4K_HDR -> {
                        setMaxVideoSize(3840, 2160)
                        // HDR flag not available in current ExoPlayer version
                    }
                }
            }
            .build()

        trackSelector.setParameters(params)
    }

    fun is4KDevice(): Boolean {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        return display?.let { display.width >= 3840 || display.height >= 2160 } ?: false
    }

    class ProRequiredException(message: String) : Exception(message)
}

enum class VideoQuality(
    val displayName: String,
    val resolution: String,
    val is4K: Boolean = false
) {
    AUTO("Auto", "Adaptive"),
    SD_480P("SD", "480p"),
    HD_720P("HD", "720p"),
    FHD_1080P("Full HD", "1080p"),
    UHD_4K("4K", "2160p", true),
    UHD_4K_HDR("4K HDR", "2160p HDR", true)
}
