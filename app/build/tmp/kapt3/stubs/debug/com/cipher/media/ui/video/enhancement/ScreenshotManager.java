package com.cipher.media.ui.video.enhancement;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.PixelCopy;
import android.view.View;
import android.widget.Toast;
import androidx.media3.ui.PlayerView;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Captures high-quality screenshots from the ExoPlayer PlayerView
 * using PixelCopy API (Android O+).
 *
 * - Free: Scaled to 720p with CIPHER watermark
 * - Pro:  Full resolution, no watermark
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J4\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0014\u0010\u000b\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0004\u0012\u00020\u00040\fJ6\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0014\u0010\u000b\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\r\u0012\u0004\u0012\u00020\u00040\fH\u0002J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\nH\u0002J\u001a\u0010\u0014\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0016\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\r\u00a8\u0006\u0017"}, d2 = {"Lcom/cipher/media/ui/video/enhancement/ScreenshotManager;", "", "()V", "captureScreenshot", "", "playerView", "Landroidx/media3/ui/PlayerView;", "context", "Landroid/content/Context;", "isPro", "", "onComplete", "Lkotlin/Function1;", "", "fallbackCapture", "view", "Landroid/view/View;", "processScreenshot", "Landroid/graphics/Bitmap;", "bitmap", "saveToGallery", "shareScreenshot", "path", "app_debug"})
public final class ScreenshotManager {
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.ui.video.enhancement.ScreenshotManager INSTANCE = null;
    
    private ScreenshotManager() {
        super();
    }
    
    public final void captureScreenshot(@org.jetbrains.annotations.NotNull()
    androidx.media3.ui.PlayerView playerView, @org.jetbrains.annotations.NotNull()
    android.content.Context context, boolean isPro, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onComplete) {
    }
    
    private final void fallbackCapture(android.view.View view, android.content.Context context, boolean isPro, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onComplete) {
    }
    
    private final android.graphics.Bitmap processScreenshot(android.graphics.Bitmap bitmap, boolean isPro) {
        return null;
    }
    
    private final java.lang.String saveToGallery(android.graphics.Bitmap bitmap, android.content.Context context) {
        return null;
    }
    
    public final void shareScreenshot(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String path) {
    }
}