package com.cipher.media;

import android.app.Application;
import coil.ImageLoaderFactory;
import coil.decode.VideoFrameDecoder;
import com.cipher.media.ads.AdManager;
import dagger.hilt.android.HiltAndroidApp;
import javax.inject.Inject;

/**
 * CIPHER Application entry point.
 * Annotated with @HiltAndroidApp to trigger Hilt's code generation.
 * Implements ImageLoaderFactory to enable Coil video thumbnail extraction.
 */
@dagger.hilt.android.HiltAndroidApp()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t\u00a8\u0006\u000e"}, d2 = {"Lcom/cipher/media/CIPHERApplication;", "Landroid/app/Application;", "Lcoil/ImageLoaderFactory;", "()V", "adManager", "Lcom/cipher/media/ads/AdManager;", "getAdManager", "()Lcom/cipher/media/ads/AdManager;", "setAdManager", "(Lcom/cipher/media/ads/AdManager;)V", "newImageLoader", "Lcoil/ImageLoader;", "onCreate", "", "app_debug"})
public final class CIPHERApplication extends android.app.Application implements coil.ImageLoaderFactory {
    @javax.inject.Inject()
    public com.cipher.media.ads.AdManager adManager;
    
    public CIPHERApplication() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ads.AdManager getAdManager() {
        return null;
    }
    
    public final void setAdManager(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ads.AdManager p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    /**
     * Global Coil ImageLoader with VideoFrameDecoder enabled.
     * This allows AsyncImage to extract video thumbnails from content:// URIs.
     */
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public coil.ImageLoader newImageLoader() {
        return null;
    }
}