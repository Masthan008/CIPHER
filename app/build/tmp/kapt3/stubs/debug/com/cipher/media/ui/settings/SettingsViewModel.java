package com.cipher.media.ui.settings;

import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000f\u001a\u00020\u0010J\b\u0010\u0011\u001a\u00020\u0007H\u0002J\u0006\u0010\u0012\u001a\u00020\u0010J\u000e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u0018J\u000e\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020 J\u000e\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020 J\u000e\u0010#\u001a\u00020\u00102\u0006\u0010$\u001a\u00020 J\u000e\u0010%\u001a\u00020\u00102\u0006\u0010&\u001a\u00020\'R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \n*\u0004\u0018\u00010\t0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006("}, d2 = {"Lcom/cipher/media/ui/settings/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_settings", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/ui/settings/AppSettings;", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "settings", "Lkotlinx/coroutines/flow/StateFlow;", "getSettings", "()Lkotlinx/coroutines/flow/StateFlow;", "clearCache", "", "loadSettings", "refreshStorageInfo", "setAutoLock", "duration", "Lcom/cipher/media/ui/settings/AutoLockDuration;", "setAutoRotate", "enabled", "", "setBiometricMode", "everyTime", "setDefaultTab", "tab", "", "setLanguage", "language", "", "setPlaybackSpeed", "speed", "setSubtitleSize", "size", "setTheme", "mode", "Lcom/cipher/media/ui/settings/ThemeMode;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.ui.settings.AppSettings> _settings = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.settings.AppSettings> settings = null;
    
    @javax.inject.Inject()
    public SettingsViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.settings.AppSettings> getSettings() {
        return null;
    }
    
    public final void setTheme(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.ThemeMode mode) {
    }
    
    public final void setDefaultTab(int tab) {
    }
    
    public final void setAutoLock(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.AutoLockDuration duration) {
    }
    
    public final void setBiometricMode(boolean everyTime) {
    }
    
    public final void setLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String language) {
    }
    
    public final void setAutoRotate(boolean enabled) {
    }
    
    public final void setPlaybackSpeed(@org.jetbrains.annotations.NotNull()
    java.lang.String speed) {
    }
    
    public final void setSubtitleSize(@org.jetbrains.annotations.NotNull()
    java.lang.String size) {
    }
    
    public final void clearCache() {
    }
    
    public final void refreshStorageInfo() {
    }
    
    private final com.cipher.media.ui.settings.AppSettings loadSettings() {
        return null;
    }
}