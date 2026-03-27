package com.cipher.media.security;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.*;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Manages automatic vault locking via:
 * - Configurable inactivity timer (1-60 min)
 * - Screen-off instant lock
 *
 * PRO TIER feature.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\"\u001a\u00020#J\u0006\u0010$\u001a\u00020#J\b\u0010%\u001a\u00020#H\u0002J\u0006\u0010&\u001a\u00020#J\b\u0010\'\u001a\u00020#H\u0002J\b\u0010(\u001a\u00020#H\u0002J\b\u0010)\u001a\u00020#H\u0002R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R$\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR$\u0010\u000f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00078F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0015R$\u0010\u0016\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00078F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0017\u0010\u0010\"\u0004\b\u0018\u0010\u0012R\u0016\u0010\u0019\u001a\n \u001b*\u0004\u0018\u00010\u001a0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010 \u001a\u0004\u0018\u00010!X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/cipher/media/security/AutoLockManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "_isVaultLocked", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "value", "", "delayMinutes", "getDelayMinutes", "()I", "setDelayMinutes", "(I)V", "isEnabled", "()Z", "setEnabled", "(Z)V", "isVaultLocked", "Lkotlinx/coroutines/flow/StateFlow;", "()Lkotlinx/coroutines/flow/StateFlow;", "lockOnScreenOff", "getLockOnScreenOff", "setLockOnScreenOff", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "scope", "Lkotlinx/coroutines/CoroutineScope;", "screenOffReceiver", "Landroid/content/BroadcastReceiver;", "timerJob", "Lkotlinx/coroutines/Job;", "lockVault", "", "onVaultUnlocked", "registerScreenOffReceiver", "resetTimer", "startTimer", "stopTimer", "unregisterScreenOffReceiver", "app_debug"})
public final class AutoLockManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isVaultLocked = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isVaultLocked = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job timerJob;
    @org.jetbrains.annotations.Nullable()
    private android.content.BroadcastReceiver screenOffReceiver;
    
    @javax.inject.Inject()
    public AutoLockManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isVaultLocked() {
        return null;
    }
    
    public final boolean isEnabled() {
        return false;
    }
    
    public final void setEnabled(boolean value) {
    }
    
    public final int getDelayMinutes() {
        return 0;
    }
    
    public final void setDelayMinutes(int value) {
    }
    
    public final boolean getLockOnScreenOff() {
        return false;
    }
    
    public final void setLockOnScreenOff(boolean value) {
    }
    
    /**
     * Call when vault is successfully unlocked.
     */
    public final void onVaultUnlocked() {
    }
    
    /**
     * Call to immediately lock the vault.
     */
    public final void lockVault() {
    }
    
    /**
     * Resets the inactivity timer (call on user interaction).
     */
    public final void resetTimer() {
    }
    
    private final void startTimer() {
    }
    
    private final void stopTimer() {
    }
    
    private final void registerScreenOffReceiver() {
    }
    
    private final void unregisterScreenOffReceiver() {
    }
}