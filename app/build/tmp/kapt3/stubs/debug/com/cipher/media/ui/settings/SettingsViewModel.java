package com.cipher.media.ui.settings;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

/**
 * Rewritten SettingsViewModel with:
 * - Type-safe SettingsRepository (no hardcoded keys)
 * - Modern LanguageManager (AppCompatDelegate locale)
 * - Reactive StateFlow (UI always in sync)
 * - All 25 settings with proper tier gating
 * - Storage info refresh
 * - Backup export/import support
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0019\b\u0007\u0018\u00002\u00020\u0001B!\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u000fJ\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0011J\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u001e\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u001bJ\u000e\u0010!\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020$J\u000e\u0010%\u001a\u00020\u000f2\u0006\u0010&\u001a\u00020\u001bJ\u000e\u0010\'\u001a\u00020\u000f2\u0006\u0010(\u001a\u00020\u001bJ\u000e\u0010)\u001a\u00020\u000f2\u0006\u0010*\u001a\u00020\u0011J\u000e\u0010+\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010,\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010-\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010.\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010/\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u00100\u001a\u00020\u00152\u0006\u00101\u001a\u00020\u0011J\u000e\u00102\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u00103\u001a\u00020\u000f2\u0006\u00104\u001a\u00020\u0011J\u000e\u00105\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u00106\u001a\u00020\u000f2\u0006\u00107\u001a\u00020\u001bJ\u000e\u00108\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u00109\u001a\u00020\u000f2\u0006\u0010:\u001a\u00020\u0011J\u000e\u0010;\u001a\u00020\u000f2\u0006\u0010<\u001a\u00020\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006="}, d2 = {"Lcom/cipher/media/ui/settings/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "repo", "Lcom/cipher/media/ui/settings/SettingsRepository;", "languageManager", "Lcom/cipher/media/ui/settings/LanguageManager;", "(Landroid/content/Context;Lcom/cipher/media/ui/settings/SettingsRepository;Lcom/cipher/media/ui/settings/LanguageManager;)V", "settings", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/cipher/media/ui/settings/FullSettings;", "getSettings", "()Lkotlinx/coroutines/flow/StateFlow;", "clearCache", "", "getLanguageDisplayName", "", "refreshStorage", "set4K", "enabled", "", "setAccentColor", "name", "setAutoDownloadSubs", "setAutoLock", "minutes", "", "setAutoRotate", "setBiometric", "setCloudSync", "setCrossfade", "sec", "setDecoyPin", "setDefaultSpeed", "speed", "", "setDefaultTab", "tab", "setDoubleTapSeek", "seconds", "setEqPreset", "preset", "setGapless", "setHDR", "setHiResAudio", "setIncognito", "setKeepScreenOn", "setLanguage", "code", "setMaterialYou", "setNowPlayingLayout", "layout", "setResumePlayback", "setSelfDestruct", "attempts", "setStealth", "setSubtitleSize", "size", "setTheme", "themeId", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.settings.SettingsRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.settings.LanguageManager languageManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.settings.FullSettings> settings = null;
    
    @javax.inject.Inject()
    public SettingsViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.SettingsRepository repo, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.LanguageManager languageManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.settings.FullSettings> getSettings() {
        return null;
    }
    
    public final void setTheme(@org.jetbrains.annotations.NotNull()
    java.lang.String themeId) {
    }
    
    public final void setAccentColor(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void setMaterialYou(boolean enabled) {
    }
    
    public final void setNowPlayingLayout(@org.jetbrains.annotations.NotNull()
    java.lang.String layout) {
    }
    
    /**
     * Sets language using BCP-47 code via modern AndroidX API.
     * @return true if a restart dialog should be shown
     */
    public final boolean setLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String code) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLanguageDisplayName() {
        return null;
    }
    
    public final void setDefaultTab(int tab) {
    }
    
    public final void setBiometric(boolean enabled) {
    }
    
    public final void setAutoLock(int minutes) {
    }
    
    public final void setAutoRotate(boolean enabled) {
    }
    
    public final void setKeepScreenOn(boolean enabled) {
    }
    
    public final void setDoubleTapSeek(int seconds) {
    }
    
    public final void setDefaultSpeed(float speed) {
    }
    
    public final void setResumePlayback(boolean enabled) {
    }
    
    public final void setSubtitleSize(@org.jetbrains.annotations.NotNull()
    java.lang.String size) {
    }
    
    public final void setHiResAudio(boolean enabled) {
    }
    
    public final void setCrossfade(int sec) {
    }
    
    public final void setGapless(boolean enabled) {
    }
    
    public final void setEqPreset(@org.jetbrains.annotations.NotNull()
    java.lang.String preset) {
    }
    
    public final void setAutoDownloadSubs(boolean enabled) {
    }
    
    public final void setCloudSync(boolean enabled) {
    }
    
    public final void set4K(boolean enabled) {
    }
    
    public final void setHDR(boolean enabled) {
    }
    
    public final void setStealth(boolean enabled) {
    }
    
    public final void setDecoyPin(boolean enabled) {
    }
    
    public final void setSelfDestruct(int attempts) {
    }
    
    public final void setIncognito(boolean enabled) {
    }
    
    public final void clearCache() {
    }
    
    public final void refreshStorage() {
    }
}