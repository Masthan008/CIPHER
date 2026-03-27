package com.cipher.media.util;

import android.Manifest;
import android.os.Build;

/**
 * Utility for determining correct runtime permissions based on API level.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004J\u0006\u0010\u0006\u001a\u00020\u0005J\u0006\u0010\u0007\u001a\u00020\u0005\u00a8\u0006\b"}, d2 = {"Lcom/cipher/media/util/PermissionsUtil;", "", "()V", "getAllMediaPermissions", "", "", "getAudioPermission", "getVideoPermission", "app_debug"})
public final class PermissionsUtil {
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.util.PermissionsUtil INSTANCE = null;
    
    private PermissionsUtil() {
        super();
    }
    
    /**
     * Returns the required permission for reading video files
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getVideoPermission() {
        return null;
    }
    
    /**
     * Returns the required permission for reading audio files
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAudioPermission() {
        return null;
    }
    
    /**
     * Returns all media permissions needed for the app
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getAllMediaPermissions() {
        return null;
    }
}