package com.cipher.media.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.google.android.gms.ads.*;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import dagger.hilt.android.qualifiers.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Manages AdMob initialization, interstitial ad loading, and play counter.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u000f\u001a\u00020\bJ\b\u0010\u0010\u001a\u00020\u000eH\u0002J\u000e\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/cipher/media/ads/AdManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "interstitialAd", "Lcom/google/android/gms/ads/interstitial/InterstitialAd;", "isInitialized", "", "videoPlayCount", "", "getAdRequest", "Lcom/google/android/gms/ads/AdRequest;", "initialize", "", "isAdLoaded", "loadInterstitial", "onVideoPlayed", "activity", "Landroid/app/Activity;", "Companion", "app_debug"})
public final class AdManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "CIPHERAdManager";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BANNER_AD_UNIT = "ca-app-pub-3940256099942544/6300978111";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String INTERSTITIAL_AD_UNIT = "ca-app-pub-3940256099942544/1033173712";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String REWARDED_AD_UNIT = "ca-app-pub-3940256099942544/5224354917";
    public static final int INTERSTITIAL_INTERVAL = 5;
    @org.jetbrains.annotations.Nullable()
    private com.google.android.gms.ads.interstitial.InterstitialAd interstitialAd;
    private int videoPlayCount = 0;
    private boolean isInitialized = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.ads.AdManager.Companion Companion = null;
    
    @javax.inject.Inject()
    public AdManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void initialize() {
    }
    
    private final void loadInterstitial() {
    }
    
    /**
     * Increment play counter and show interstitial if interval reached.
     * Returns true if interstitial was shown.
     */
    public final boolean onVideoPlayed(@org.jetbrains.annotations.NotNull()
    android.app.Activity activity) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.google.android.gms.ads.AdRequest getAdRequest() {
        return null;
    }
    
    public final boolean isAdLoaded() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/cipher/media/ads/AdManager$Companion;", "", "()V", "BANNER_AD_UNIT", "", "INTERSTITIAL_AD_UNIT", "INTERSTITIAL_INTERVAL", "", "REWARDED_AD_UNIT", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}