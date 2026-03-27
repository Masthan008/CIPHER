package com.cipher.media.util;

/**
 * Utility for formatting time durations.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0006\u00a8\u0006\t"}, d2 = {"Lcom/cipher/media/util/TimeUtil;", "", "()V", "formatDuration", "", "millis", "", "formatSeekDelta", "deltaMs", "app_debug"})
public final class TimeUtil {
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.util.TimeUtil INSTANCE = null;
    
    private TimeUtil() {
        super();
    }
    
    /**
     * Formats milliseconds to HH:MM:SS or MM:SS string.
     * @param millis Duration in milliseconds
     * @return Formatted time string
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatDuration(long millis) {
        return null;
    }
    
    /**
     * Formats a seek delta for display (e.g., "+10s" or "-5s").
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String formatSeekDelta(long deltaMs) {
        return null;
    }
}