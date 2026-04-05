package com.cipher.media.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Immutable snapshot of all settings for Compose observation.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0010\n\u0002\u0010\t\n\u0002\bI\b\u0086\b\u0018\u00002\u00020\u0001B\u00a7\u0002\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\f\u001a\u00020\n\u0012\b\b\u0002\u0010\r\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000f\u001a\u00020\n\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0015\u001a\u00020\n\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u001e\u001a\u00020\n\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u0006\u0012\b\b\u0002\u0010 \u001a\u00020\u0003\u0012\b\b\u0002\u0010!\u001a\u00020\"\u0012\b\b\u0002\u0010#\u001a\u00020\"\u00a2\u0006\u0002\u0010$J\t\u0010I\u001a\u00020\u0003H\u00c6\u0003J\t\u0010J\u001a\u00020\u0006H\u00c6\u0003J\t\u0010K\u001a\u00020\nH\u00c6\u0003J\t\u0010L\u001a\u00020\u0011H\u00c6\u0003J\t\u0010M\u001a\u00020\u0006H\u00c6\u0003J\t\u0010N\u001a\u00020\u0003H\u00c6\u0003J\t\u0010O\u001a\u00020\u0006H\u00c6\u0003J\t\u0010P\u001a\u00020\nH\u00c6\u0003J\t\u0010Q\u001a\u00020\u0006H\u00c6\u0003J\t\u0010R\u001a\u00020\u0003H\u00c6\u0003J\t\u0010S\u001a\u00020\u0006H\u00c6\u0003J\t\u0010T\u001a\u00020\u0003H\u00c6\u0003J\t\u0010U\u001a\u00020\u0006H\u00c6\u0003J\t\u0010V\u001a\u00020\u0006H\u00c6\u0003J\t\u0010W\u001a\u00020\u0006H\u00c6\u0003J\t\u0010X\u001a\u00020\u0006H\u00c6\u0003J\t\u0010Y\u001a\u00020\u0006H\u00c6\u0003J\t\u0010Z\u001a\u00020\nH\u00c6\u0003J\t\u0010[\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\\\u001a\u00020\u0003H\u00c6\u0003J\t\u0010]\u001a\u00020\"H\u00c6\u0003J\t\u0010^\u001a\u00020\"H\u00c6\u0003J\t\u0010_\u001a\u00020\u0006H\u00c6\u0003J\t\u0010`\u001a\u00020\u0003H\u00c6\u0003J\t\u0010a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010b\u001a\u00020\nH\u00c6\u0003J\t\u0010c\u001a\u00020\u0006H\u00c6\u0003J\t\u0010d\u001a\u00020\nH\u00c6\u0003J\t\u0010e\u001a\u00020\u0006H\u00c6\u0003J\u00ab\u0002\u0010f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\n2\b\b\u0002\u0010\r\u001a\u00020\u00062\b\b\u0002\u0010\u000e\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00062\b\b\u0002\u0010\u0013\u001a\u00020\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00062\b\b\u0002\u0010\u0015\u001a\u00020\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00062\b\b\u0002\u0010\u0017\u001a\u00020\u00032\b\b\u0002\u0010\u0018\u001a\u00020\u00062\b\b\u0002\u0010\u0019\u001a\u00020\u00062\b\b\u0002\u0010\u001a\u001a\u00020\u00062\b\b\u0002\u0010\u001b\u001a\u00020\u00062\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\u001d\u001a\u00020\u00062\b\b\u0002\u0010\u001e\u001a\u00020\n2\b\b\u0002\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010 \u001a\u00020\u00032\b\b\u0002\u0010!\u001a\u00020\"2\b\b\u0002\u0010#\u001a\u00020\"H\u00c6\u0001J\u0013\u0010g\u001a\u00020\u00062\b\u0010h\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010i\u001a\u00020\nH\u00d6\u0001J\t\u0010j\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\u0018\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0011\u0010\f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010\r\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010(R\u0011\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010(R\u0011\u0010!\u001a\u00020\"\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0011\u0010\u0019\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010(R\u0011\u0010\u0015\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010*R\u0011\u0010\u001d\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010(R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010*R\u0011\u0010\u000f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010*R\u0011\u0010\u001a\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010(R\u0011\u0010\u001b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010(R\u0011\u0010\u0017\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b8\u0010&R\u0011\u0010\u0016\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010(R\u0011\u0010\u0014\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b:\u0010(R\u0011\u0010\u001f\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b;\u0010(R\u0011\u0010<\u001a\u00020\u00068F\u00a2\u0006\u0006\u001a\u0004\b<\u0010(R\u0011\u0010=\u001a\u00020\u00068F\u00a2\u0006\u0006\u001a\u0004\b=\u0010(R\u0011\u0010\u000e\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b>\u0010(R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b?\u0010&R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u0010(R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bA\u0010&R\u0011\u0010\u0012\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bB\u0010(R\u0011\u0010\u001e\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010*R\u0011\u0010\u001c\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\bD\u0010(R\u0011\u0010\u0013\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u0010&R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bF\u0010&R\u0011\u0010 \u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bG\u0010&R\u0011\u0010#\u001a\u00020\"\u00a2\u0006\b\n\u0000\u001a\u0004\bH\u0010.\u00a8\u0006k"}, d2 = {"Lcom/cipher/media/ui/settings/FullSettings;", "", "themeId", "", "accentColorName", "materialYou", "", "nowPlayingLayout", "languageCode", "defaultTab", "", "biometricEnabled", "autoLockMinutes", "autoRotate", "keepScreenOn", "doubleTapSeekSec", "defaultSpeed", "", "resumePlayback", "subtitleSize", "hiResAudio", "crossfadeSec", "gapless", "eqPreset", "autoDownloadSubs", "cloudSync", "enable4K", "enableHDR", "stealthMode", "decoyPin", "selfDestructAttempts", "incognitoMode", "userTier", "cacheSize", "", "vaultSize", "(Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;IZIZZIFZLjava/lang/String;ZIZLjava/lang/String;ZZZZZZIZLjava/lang/String;JJ)V", "getAccentColorName", "()Ljava/lang/String;", "getAutoDownloadSubs", "()Z", "getAutoLockMinutes", "()I", "getAutoRotate", "getBiometricEnabled", "getCacheSize", "()J", "getCloudSync", "getCrossfadeSec", "getDecoyPin", "getDefaultSpeed", "()F", "getDefaultTab", "getDoubleTapSeekSec", "getEnable4K", "getEnableHDR", "getEqPreset", "getGapless", "getHiResAudio", "getIncognitoMode", "isPower", "isPro", "getKeepScreenOn", "getLanguageCode", "getMaterialYou", "getNowPlayingLayout", "getResumePlayback", "getSelfDestructAttempts", "getStealthMode", "getSubtitleSize", "getThemeId", "getUserTier", "getVaultSize", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class FullSettings {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String themeId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String accentColorName = null;
    private final boolean materialYou = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String nowPlayingLayout = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String languageCode = null;
    private final int defaultTab = 0;
    private final boolean biometricEnabled = false;
    private final int autoLockMinutes = 0;
    private final boolean autoRotate = false;
    private final boolean keepScreenOn = false;
    private final int doubleTapSeekSec = 0;
    private final float defaultSpeed = 0.0F;
    private final boolean resumePlayback = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String subtitleSize = null;
    private final boolean hiResAudio = false;
    private final int crossfadeSec = 0;
    private final boolean gapless = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String eqPreset = null;
    private final boolean autoDownloadSubs = false;
    private final boolean cloudSync = false;
    private final boolean enable4K = false;
    private final boolean enableHDR = false;
    private final boolean stealthMode = false;
    private final boolean decoyPin = false;
    private final int selfDestructAttempts = 0;
    private final boolean incognitoMode = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String userTier = null;
    private final long cacheSize = 0L;
    private final long vaultSize = 0L;
    
    public FullSettings(@org.jetbrains.annotations.NotNull()
    java.lang.String themeId, @org.jetbrains.annotations.NotNull()
    java.lang.String accentColorName, boolean materialYou, @org.jetbrains.annotations.NotNull()
    java.lang.String nowPlayingLayout, @org.jetbrains.annotations.NotNull()
    java.lang.String languageCode, int defaultTab, boolean biometricEnabled, int autoLockMinutes, boolean autoRotate, boolean keepScreenOn, int doubleTapSeekSec, float defaultSpeed, boolean resumePlayback, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitleSize, boolean hiResAudio, int crossfadeSec, boolean gapless, @org.jetbrains.annotations.NotNull()
    java.lang.String eqPreset, boolean autoDownloadSubs, boolean cloudSync, boolean enable4K, boolean enableHDR, boolean stealthMode, boolean decoyPin, int selfDestructAttempts, boolean incognitoMode, @org.jetbrains.annotations.NotNull()
    java.lang.String userTier, long cacheSize, long vaultSize) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getThemeId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAccentColorName() {
        return null;
    }
    
    public final boolean getMaterialYou() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNowPlayingLayout() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLanguageCode() {
        return null;
    }
    
    public final int getDefaultTab() {
        return 0;
    }
    
    public final boolean getBiometricEnabled() {
        return false;
    }
    
    public final int getAutoLockMinutes() {
        return 0;
    }
    
    public final boolean getAutoRotate() {
        return false;
    }
    
    public final boolean getKeepScreenOn() {
        return false;
    }
    
    public final int getDoubleTapSeekSec() {
        return 0;
    }
    
    public final float getDefaultSpeed() {
        return 0.0F;
    }
    
    public final boolean getResumePlayback() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSubtitleSize() {
        return null;
    }
    
    public final boolean getHiResAudio() {
        return false;
    }
    
    public final int getCrossfadeSec() {
        return 0;
    }
    
    public final boolean getGapless() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEqPreset() {
        return null;
    }
    
    public final boolean getAutoDownloadSubs() {
        return false;
    }
    
    public final boolean getCloudSync() {
        return false;
    }
    
    public final boolean getEnable4K() {
        return false;
    }
    
    public final boolean getEnableHDR() {
        return false;
    }
    
    public final boolean getStealthMode() {
        return false;
    }
    
    public final boolean getDecoyPin() {
        return false;
    }
    
    public final int getSelfDestructAttempts() {
        return 0;
    }
    
    public final boolean getIncognitoMode() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserTier() {
        return null;
    }
    
    public final long getCacheSize() {
        return 0L;
    }
    
    public final long getVaultSize() {
        return 0L;
    }
    
    public final boolean isPro() {
        return false;
    }
    
    public final boolean isPower() {
        return false;
    }
    
    public FullSettings() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final int component11() {
        return 0;
    }
    
    public final float component12() {
        return 0.0F;
    }
    
    public final boolean component13() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component14() {
        return null;
    }
    
    public final boolean component15() {
        return false;
    }
    
    public final int component16() {
        return 0;
    }
    
    public final boolean component17() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component18() {
        return null;
    }
    
    public final boolean component19() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final boolean component20() {
        return false;
    }
    
    public final boolean component21() {
        return false;
    }
    
    public final boolean component22() {
        return false;
    }
    
    public final boolean component23() {
        return false;
    }
    
    public final boolean component24() {
        return false;
    }
    
    public final int component25() {
        return 0;
    }
    
    public final boolean component26() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component27() {
        return null;
    }
    
    public final long component28() {
        return 0L;
    }
    
    public final long component29() {
        return 0L;
    }
    
    public final boolean component3() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.settings.FullSettings copy(@org.jetbrains.annotations.NotNull()
    java.lang.String themeId, @org.jetbrains.annotations.NotNull()
    java.lang.String accentColorName, boolean materialYou, @org.jetbrains.annotations.NotNull()
    java.lang.String nowPlayingLayout, @org.jetbrains.annotations.NotNull()
    java.lang.String languageCode, int defaultTab, boolean biometricEnabled, int autoLockMinutes, boolean autoRotate, boolean keepScreenOn, int doubleTapSeekSec, float defaultSpeed, boolean resumePlayback, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitleSize, boolean hiResAudio, int crossfadeSec, boolean gapless, @org.jetbrains.annotations.NotNull()
    java.lang.String eqPreset, boolean autoDownloadSubs, boolean cloudSync, boolean enable4K, boolean enableHDR, boolean stealthMode, boolean decoyPin, int selfDestructAttempts, boolean incognitoMode, @org.jetbrains.annotations.NotNull()
    java.lang.String userTier, long cacheSize, long vaultSize) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}