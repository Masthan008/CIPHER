package com.cipher.media

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.VideoFrameDecoder
import com.cipher.media.ads.AdManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * CIPHER Application entry point.
 * Annotated with @HiltAndroidApp to trigger Hilt's code generation.
 * Implements ImageLoaderFactory to enable Coil video thumbnail extraction.
 */
@HiltAndroidApp
class CIPHERApplication : Application(), ImageLoaderFactory {

    @Inject lateinit var adManager: AdManager
    @Inject lateinit var languageManager: com.cipher.media.ui.settings.LanguageManager

    override fun onCreate() {
        super.onCreate()
        // Re-apply saved locale on cold start (critical for Android < 13)
        languageManager.applyOnStartup()
        // Initialize AdMob so interstitials are preloaded
        adManager.initialize()
    }

    /**
     * Global Coil ImageLoader with VideoFrameDecoder enabled.
     * This allows AsyncImage to extract video thumbnails from content:// URIs.
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(VideoFrameDecoder.Factory())
            }
            .crossfade(true)
            .build()
    }
}
