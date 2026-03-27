package com.cipher.media.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages AdMob initialization, interstitial ad loading, and play counter.
 */
@Singleton
class AdManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val TAG = "CIPHERAdManager"

        // Google's official test ad unit IDs — safe for development
        // Replace with your real ad unit IDs before production release
        const val BANNER_AD_UNIT = "ca-app-pub-3940256099942544/6300978111"
        const val INTERSTITIAL_AD_UNIT = "ca-app-pub-3940256099942544/1033173712"
        const val REWARDED_AD_UNIT = "ca-app-pub-3940256099942544/5224354917"

        const val INTERSTITIAL_INTERVAL = 5 // Show after every N video plays
    }

    private var interstitialAd: InterstitialAd? = null
    private var videoPlayCount = 0
    private var isInitialized = false

    fun initialize() {
        MobileAds.initialize(context) { initStatus ->
            Log.d(TAG, "AdMob initialized: $initStatus")
            isInitialized = true
            loadInterstitial()
        }
    }

    private fun loadInterstitial() {
        if (!isInitialized) return
        val request = AdRequest.Builder().build()
        InterstitialAd.load(context, INTERSTITIAL_AD_UNIT, request,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    Log.d(TAG, "Interstitial loaded successfully")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                    Log.w(TAG, "Interstitial load failed: code=${error.code}, msg=${error.message}")
                    // Retry after a short delay is handled by the next play event
                }
            }
        )
    }

    /**
     * Increment play counter and show interstitial if interval reached.
     * Returns true if interstitial was shown.
     */
    fun onVideoPlayed(activity: Activity): Boolean {
        videoPlayCount++
        Log.d(TAG, "Video play #$videoPlayCount, interstitial loaded: ${interstitialAd != null}")

        if (videoPlayCount % INTERSTITIAL_INTERVAL == 0) {
            if (interstitialAd != null) {
                interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Interstitial dismissed, preloading next")
                        interstitialAd = null
                        loadInterstitial()
                    }

                    override fun onAdFailedToShowFullScreenContent(error: AdError) {
                        Log.w(TAG, "Interstitial show failed: ${error.message}")
                        interstitialAd = null
                        loadInterstitial()
                    }
                }
                interstitialAd?.show(activity)
                return true
            } else {
                // Ad wasn't loaded yet, preload for next time
                loadInterstitial()
            }
        }
        return false
    }

    fun getAdRequest(): AdRequest = AdRequest.Builder().build()

    fun isAdLoaded(): Boolean = interstitialAd != null
}
