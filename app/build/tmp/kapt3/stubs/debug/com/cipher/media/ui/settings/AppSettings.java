package com.cipher.media.ui.settings;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0018\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BA\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u000bH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u000bH\u00c6\u0003JE\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000bH\u00c6\u0001J\u0013\u0010 \u001a\u00020\t2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020\u0005H\u00d6\u0001J\t\u0010#\u001a\u00020$H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013\u00a8\u0006%"}, d2 = {"Lcom/cipher/media/ui/settings/AppSettings;", "", "themeMode", "Lcom/cipher/media/ui/settings/ThemeMode;", "defaultTab", "", "autoLock", "Lcom/cipher/media/ui/settings/AutoLockDuration;", "biometricEveryTime", "", "cacheSize", "", "vaultSize", "(Lcom/cipher/media/ui/settings/ThemeMode;ILcom/cipher/media/ui/settings/AutoLockDuration;ZJJ)V", "getAutoLock", "()Lcom/cipher/media/ui/settings/AutoLockDuration;", "getBiometricEveryTime", "()Z", "getCacheSize", "()J", "getDefaultTab", "()I", "getThemeMode", "()Lcom/cipher/media/ui/settings/ThemeMode;", "getVaultSize", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"})
public final class AppSettings {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.settings.ThemeMode themeMode = null;
    private final int defaultTab = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.settings.AutoLockDuration autoLock = null;
    private final boolean biometricEveryTime = false;
    private final long cacheSize = 0L;
    private final long vaultSize = 0L;
    
    public AppSettings(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.ThemeMode themeMode, int defaultTab, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.AutoLockDuration autoLock, boolean biometricEveryTime, long cacheSize, long vaultSize) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.settings.ThemeMode getThemeMode() {
        return null;
    }
    
    public final int getDefaultTab() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.settings.AutoLockDuration getAutoLock() {
        return null;
    }
    
    public final boolean getBiometricEveryTime() {
        return false;
    }
    
    public final long getCacheSize() {
        return 0L;
    }
    
    public final long getVaultSize() {
        return 0L;
    }
    
    public AppSettings() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.settings.ThemeMode component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.settings.AutoLockDuration component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final long component5() {
        return 0L;
    }
    
    public final long component6() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.settings.AppSettings copy(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.ThemeMode themeMode, int defaultTab, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.AutoLockDuration autoLock, boolean biometricEveryTime, long cacheSize, long vaultSize) {
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