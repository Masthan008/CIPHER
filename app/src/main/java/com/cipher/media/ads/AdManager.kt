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

        // Test ad unit IDs (replace with real IDs before release)
        const val BANNER_AD_UNIT = "ca-app-pub-3940256099942544/6300978111"
        const val INTERSTITIAL_AD_UNIT = "ca-app-pub-3940256099942544/1033173712"
        const val REWARDED_AD_UNIT = "ca-app-pub-3940256099942544/5224354917"

        const val INTERSTITIAL_INTERVAL = 5 // Show after every N video plays
    }

    private var interstitialAd: InterstitialAd? = null
    private var videoPlayCount = 0

    fun initialize() {
        MobileAds.initialize(context) {
            Log.d(TAG, "AdMob initialized")
            loadInterstitial()
        }
    }

    private fun loadInterstitial() {
        val request = AdRequest.Builder().build()
        InterstitialAd.load(context, INTERSTITIAL_AD_UNIT, request,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    Log.d(TAG, "Interstitial loaded")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                    Log.w(TAG, "Interstitial failed: ${error.message}")
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
        if (videoPlayCount % INTERSTITIAL_INTERVAL == 0 && interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    loadInterstitial() // Preload next
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    interstitialAd = null
                    loadInterstitial()
                }
            }
            interstitialAd?.show(activity)
            return true
        }
        return false
    }

    fun getAdRequest(): AdRequest = AdRequest.Builder().build()
}
