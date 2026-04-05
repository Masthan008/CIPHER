package com.cipher.media.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Type-safe settings repository with proper SharedPreferences handling.
 * All keys are constants, all reads have defaults, all writes use .apply().
 * Provides reactive StateFlow for UI observation.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0010\u0007\n\u0002\b,\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\r\b\u0007\u0018\u0000 }2\u00020\u0001:\u0001}B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010n\u001a\u00020oJ\u0006\u0010p\u001a\u00020qJ\u0006\u0010r\u001a\u00020qJ\b\u0010s\u001a\u00020\u0007H\u0002J\u0006\u0010t\u001a\u00020oJ\u0006\u0010u\u001a\u00020oJ\u0018\u0010v\u001a\u00020o2\u0006\u0010w\u001a\u00020\t2\u0006\u0010x\u001a\u00020\u000fH\u0002J\u0018\u0010y\u001a\u00020o2\u0006\u0010w\u001a\u00020\t2\u0006\u0010x\u001a\u00020*H\u0002J\u0018\u0010z\u001a\u00020o2\u0006\u0010w\u001a\u00020\t2\u0006\u0010x\u001a\u00020\u0015H\u0002J\u0018\u0010{\u001a\u00020o2\u0006\u0010w\u001a\u00020\t2\u0006\u0010x\u001a\u00020qH\u0002J\u0018\u0010|\u001a\u00020o2\u0006\u0010w\u001a\u00020\t2\u0006\u0010x\u001a\u00020\tH\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R$\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR$\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R$\u0010\u0016\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\u00158F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR$\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u001c\u0010\u0012\"\u0004\b\u001d\u0010\u0014R$\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u001f\u0010\u0012\"\u0004\b \u0010\u0014R$\u0010!\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\"\u0010\u0012\"\u0004\b#\u0010\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R$\u0010$\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\u00158F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b%\u0010\u0018\"\u0004\b&\u0010\u001aR$\u0010\'\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b(\u0010\u0012\"\u0004\b)\u0010\u0014R$\u0010+\u001a\u00020*2\u0006\u0010\b\u001a\u00020*8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R$\u00100\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\u00158F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b1\u0010\u0018\"\u0004\b2\u0010\u001aR$\u00103\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\u00158F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b4\u0010\u0018\"\u0004\b5\u0010\u001aR$\u00106\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b7\u0010\u0012\"\u0004\b8\u0010\u0014R$\u00109\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b:\u0010\u0012\"\u0004\b;\u0010\u0014R$\u0010<\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b=\u0010\f\"\u0004\b>\u0010\u000eR$\u0010?\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b@\u0010\u0012\"\u0004\bA\u0010\u0014R$\u0010B\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bC\u0010\u0012\"\u0004\bD\u0010\u0014R$\u0010E\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bF\u0010\u0012\"\u0004\bG\u0010\u0014R\u0011\u0010H\u001a\u00020\u000f8F\u00a2\u0006\u0006\u001a\u0004\bH\u0010\u0012R\u0011\u0010I\u001a\u00020\u000f8F\u00a2\u0006\u0006\u001a\u0004\bI\u0010\u0012R$\u0010J\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bK\u0010\u0012\"\u0004\bL\u0010\u0014R$\u0010M\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bN\u0010\f\"\u0004\bO\u0010\u000eR$\u0010P\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bQ\u0010\u0012\"\u0004\bR\u0010\u0014R$\u0010S\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bT\u0010\f\"\u0004\bU\u0010\u000eR\u000e\u0010V\u001a\u00020WX\u0082\u0004\u00a2\u0006\u0002\n\u0000R$\u0010X\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bY\u0010\u0012\"\u0004\bZ\u0010\u0014R$\u0010[\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\u00158F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\\\u0010\u0018\"\u0004\b]\u0010\u001aR\u0017\u0010^\u001a\b\u0012\u0004\u0012\u00020\u00070_\u00a2\u0006\b\n\u0000\u001a\u0004\b`\u0010aR$\u0010b\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u000f8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bc\u0010\u0012\"\u0004\bd\u0010\u0014R$\u0010e\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bf\u0010\f\"\u0004\bg\u0010\u000eR$\u0010h\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bi\u0010\f\"\u0004\bj\u0010\u000eR$\u0010k\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bl\u0010\f\"\u0004\bm\u0010\u000e\u00a8\u0006~"}, d2 = {"Lcom/cipher/media/ui/settings/SettingsRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/ui/settings/FullSettings;", "v", "", "accentColorName", "getAccentColorName", "()Ljava/lang/String;", "setAccentColorName", "(Ljava/lang/String;)V", "", "autoDownloadSubs", "getAutoDownloadSubs", "()Z", "setAutoDownloadSubs", "(Z)V", "", "autoLockMinutes", "getAutoLockMinutes", "()I", "setAutoLockMinutes", "(I)V", "autoRotate", "getAutoRotate", "setAutoRotate", "biometricEnabled", "getBiometricEnabled", "setBiometricEnabled", "cloudSync", "getCloudSync", "setCloudSync", "crossfadeSec", "getCrossfadeSec", "setCrossfadeSec", "decoyPin", "getDecoyPin", "setDecoyPin", "", "defaultSpeed", "getDefaultSpeed", "()F", "setDefaultSpeed", "(F)V", "defaultTab", "getDefaultTab", "setDefaultTab", "doubleTapSeekSec", "getDoubleTapSeekSec", "setDoubleTapSeekSec", "enable4K", "getEnable4K", "setEnable4K", "enableHDR", "getEnableHDR", "setEnableHDR", "eqPreset", "getEqPreset", "setEqPreset", "gapless", "getGapless", "setGapless", "hiResAudio", "getHiResAudio", "setHiResAudio", "incognitoMode", "getIncognitoMode", "setIncognitoMode", "isPower", "isPro", "keepScreenOn", "getKeepScreenOn", "setKeepScreenOn", "languageCode", "getLanguageCode", "setLanguageCode", "materialYou", "getMaterialYou", "setMaterialYou", "nowPlayingLayout", "getNowPlayingLayout", "setNowPlayingLayout", "prefs", "Landroid/content/SharedPreferences;", "resumePlayback", "getResumePlayback", "setResumePlayback", "selfDestructAttempts", "getSelfDestructAttempts", "setSelfDestructAttempts", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "stealthMode", "getStealthMode", "setStealthMode", "subtitleSize", "getSubtitleSize", "setSubtitleSize", "themeId", "getThemeId", "setThemeId", "userTier", "getUserTier", "setUserTier", "clearCache", "", "getCacheSize", "", "getVaultSize", "load", "migrateIfNeeded", "reload", "saveBool", "key", "value", "saveFloat", "saveInt", "saveLong", "saveString", "Companion", "app_debug"})
public final class SettingsRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.ui.settings.FullSettings> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.settings.FullSettings> state = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String PREFS_NAME = "cipher_settings_v2";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_VERSION = "settings_version";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_THEME = "theme_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_ACCENT = "accent_color";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_MATERIAL_YOU = "material_you";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_NOW_PLAYING = "now_playing_layout";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_LANGUAGE = "language_code";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_DEFAULT_TAB = "default_tab";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_BIOMETRIC = "biometric_enabled";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_AUTO_LOCK = "auto_lock_minutes";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_AUTO_ROTATE = "auto_rotate";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_KEEP_SCREEN_ON = "keep_screen_on";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_DOUBLE_TAP_SEEK = "double_tap_seek_sec";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_DEFAULT_SPEED = "default_speed";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_RESUME_PLAYBACK = "resume_playback";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_SUBTITLE_SIZE = "subtitle_size";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_HIRES = "hi_res_audio";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_CROSSFADE = "crossfade_sec";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_GAPLESS = "gapless_enabled";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_EQ_PRESET = "eq_preset";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_AUTO_SUBS = "auto_download_subs";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_CLOUD_SYNC = "cloud_sync";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_4K = "enable_4k";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_HDR = "enable_hdr";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_STEALTH = "stealth_mode";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_DECOY_PIN = "decoy_pin";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_SELF_DESTRUCT = "self_destruct_attempts";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_INCOGNITO = "incognito_mode";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String KEY_TIER = "user_tier";
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.ui.settings.SettingsRepository.Companion Companion = null;
    
    @javax.inject.Inject()
    public SettingsRepository(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.settings.FullSettings> getState() {
        return null;
    }
    
    private final void saveString(java.lang.String key, java.lang.String value) {
    }
    
    private final void saveBool(java.lang.String key, boolean value) {
    }
    
    private final void saveInt(java.lang.String key, int value) {
    }
    
    private final void saveFloat(java.lang.String key, float value) {
    }
    
    private final void saveLong(java.lang.String key, long value) {
    }
    
    public final void reload() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getThemeId() {
        return null;
    }
    
    public final void setThemeId(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAccentColorName() {
        return null;
    }
    
    public final void setAccentColorName(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final boolean getMaterialYou() {
        return false;
    }
    
    public final void setMaterialYou(boolean v) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNowPlayingLayout() {
        return null;
    }
    
    public final void setNowPlayingLayout(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLanguageCode() {
        return null;
    }
    
    public final void setLanguageCode(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final int getDefaultTab() {
        return 0;
    }
    
    public final void setDefaultTab(int v) {
    }
    
    public final boolean getBiometricEnabled() {
        return false;
    }
    
    public final void setBiometricEnabled(boolean v) {
    }
    
    public final int getAutoLockMinutes() {
        return 0;
    }
    
    public final void setAutoLockMinutes(int v) {
    }
    
    public final boolean getAutoRotate() {
        return false;
    }
    
    public final void setAutoRotate(boolean v) {
    }
    
    public final boolean getKeepScreenOn() {
        return false;
    }
    
    public final void setKeepScreenOn(boolean v) {
    }
    
    public final int getDoubleTapSeekSec() {
        return 0;
    }
    
    public final void setDoubleTapSeekSec(int v) {
    }
    
    public final float getDefaultSpeed() {
        return 0.0F;
    }
    
    public final void setDefaultSpeed(float v) {
    }
    
    public final boolean getResumePlayback() {
        return false;
    }
    
    public final void setResumePlayback(boolean v) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSubtitleSize() {
        return null;
    }
    
    public final void setSubtitleSize(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final boolean getHiResAudio() {
        return false;
    }
    
    public final void setHiResAudio(boolean v) {
    }
    
    public final int getCrossfadeSec() {
        return 0;
    }
    
    public final void setCrossfadeSec(int v) {
    }
    
    public final boolean getGapless() {
        return false;
    }
    
    public final void setGapless(boolean v) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEqPreset() {
        return null;
    }
    
    public final void setEqPreset(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final boolean getAutoDownloadSubs() {
        return false;
    }
    
    public final void setAutoDownloadSubs(boolean v) {
    }
    
    public final boolean getCloudSync() {
        return false;
    }
    
    public final void setCloudSync(boolean v) {
    }
    
    public final boolean getEnable4K() {
        return false;
    }
    
    public final void setEnable4K(boolean v) {
    }
    
    public final boolean getEnableHDR() {
        return false;
    }
    
    public final void setEnableHDR(boolean v) {
    }
    
    public final boolean getStealthMode() {
        return false;
    }
    
    public final void setStealthMode(boolean v) {
    }
    
    public final boolean getDecoyPin() {
        return false;
    }
    
    public final void setDecoyPin(boolean v) {
    }
    
    public final int getSelfDestructAttempts() {
        return 0;
    }
    
    public final void setSelfDestructAttempts(int v) {
    }
    
    public final boolean getIncognitoMode() {
        return false;
    }
    
    public final void setIncognitoMode(boolean v) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUserTier() {
        return null;
    }
    
    public final void setUserTier(@org.jetbrains.annotations.NotNull()
    java.lang.String v) {
    }
    
    public final boolean isPro() {
        return false;
    }
    
    public final boolean isPower() {
        return false;
    }
    
    public final long getCacheSize() {
        return 0L;
    }
    
    public final long getVaultSize() {
        return 0L;
    }
    
    public final void clearCache() {
    }
    
    public final void migrateIfNeeded() {
    }
    
    private final com.cipher.media.ui.settings.FullSettings load() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001d\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2 = {"Lcom/cipher/media/ui/settings/SettingsRepository$Companion;", "", "()V", "KEY_4K", "", "KEY_ACCENT", "KEY_AUTO_LOCK", "KEY_AUTO_ROTATE", "KEY_AUTO_SUBS", "KEY_BIOMETRIC", "KEY_CLOUD_SYNC", "KEY_CROSSFADE", "KEY_DECOY_PIN", "KEY_DEFAULT_SPEED", "KEY_DEFAULT_TAB", "KEY_DOUBLE_TAP_SEEK", "KEY_EQ_PRESET", "KEY_GAPLESS", "KEY_HDR", "KEY_HIRES", "KEY_INCOGNITO", "KEY_KEEP_SCREEN_ON", "KEY_LANGUAGE", "KEY_MATERIAL_YOU", "KEY_NOW_PLAYING", "KEY_RESUME_PLAYBACK", "KEY_SELF_DESTRUCT", "KEY_STEALTH", "KEY_SUBTITLE_SIZE", "KEY_THEME", "KEY_TIER", "KEY_VERSION", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}