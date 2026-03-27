package com.cipher.media.ads

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.LoadAdError

/**
 * Helper for showing interstitial ads between content.
 */
object InterstitialHelper {

    private var interstitialAd: InterstitialAd? = null

    fun preload(activity: Activity, adUnitId: String = AdManager.INTERSTITIAL_AD_UNIT) {
        InterstitialAd.load(activity, adUnitId, AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                }
            }
        )
    }

    fun showIfReady(
        activity: Activity,
        onDismissed: () -> Unit = {},
        onFailed: () -> Unit = {}
    ): Boolean {
        val ad = interstitialAd ?: return false

        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                preload(activity) // Reload for next time
                onDismissed()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                interstitialAd = null
                preload(activity)
                onFailed()
            }
        }

        ad.show(activity)
        return true
    }

    val isReady: Boolean get() = interstitialAd != null
}
