package com.cipher.media.security;

import android.content.Context;
import android.util.Log;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Manages hidden front-camera capture for intruder detection.
 * Takes a selfie silently when a wrong PIN is entered in the Vault.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0007\u001a\u00020\b2\u0014\u0010\t\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0006\u0012\u0004\u0012\u00020\b0\nJ\b\u0010\u000b\u001a\u00020\fH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/cipher/media/security/IntruderCameraManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "TAG", "", "captureIntruderPhoto", "", "onComplete", "Lkotlin/Function1;", "createPhotoFile", "Ljava/io/File;", "app_debug"})
public final class IntruderCameraManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "IntruderCamera";
    
    public IntruderCameraManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Capture a photo from the front camera and save it to the intruder photos directory.
     * @param onComplete callback with the saved file path, or null on failure.
     */
    public final void captureIntruderPhoto(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onComplete) {
    }
    
    private final java.io.File createPhotoFile() {
        return null;
    }
}