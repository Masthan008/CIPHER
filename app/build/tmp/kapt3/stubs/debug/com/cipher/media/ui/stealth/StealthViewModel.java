package com.cipher.media.ui.stealth;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import androidx.lifecycle.ViewModel;
import com.cipher.media.data.local.IntruderLogDao;
import com.cipher.media.data.local.IntruderLogEntity;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.*;
import java.io.File;
import java.util.UUID;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u000f\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010!\u001a\u00020\"J\u001a\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\u00172\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\bJ\u0010\u0010&\u001a\u00020\"2\b\u0010\'\u001a\u0004\u0018\u00010\bJ\u000e\u0010(\u001a\u00020\"2\u0006\u0010)\u001a\u00020\u0012J\u000e\u0010*\u001a\u00020\"2\u0006\u0010+\u001a\u00020\bJ\u0018\u0010,\u001a\u00020\"2\u0006\u0010)\u001a\u00020\u00122\b\b\u0002\u0010-\u001a\u00020\u0017J\u000e\u0010.\u001a\u00020\"2\u0006\u0010)\u001a\u00020\u0012J\u0010\u0010/\u001a\u00020\"2\u0006\u00100\u001a\u00020\u0012H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b8F\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00128F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u00128F\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\u0015\u001a\u00020\u00128F\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0013R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0010R\u0016\u0010\u0019\u001a\n \u001b*\u0004\u0018\u00010\u001a0\u001aX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u001c\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\nR\u0011\u0010\u001e\u001a\u00020\u00178F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010 \u00a8\u00061"}, d2 = {"Lcom/cipher/media/ui/stealth/StealthViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "intruderLogDao", "Lcom/cipher/media/data/local/IntruderLogDao;", "(Landroid/content/Context;Lcom/cipher/media/data/local/IntruderLogDao;)V", "decoyPinHash", "", "getDecoyPinHash", "()Ljava/lang/String;", "intruderLogs", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/cipher/media/data/local/IntruderLogEntity;", "getIntruderLogs", "()Lkotlinx/coroutines/flow/StateFlow;", "isIntruderDetectionEnabled", "", "()Z", "isSelfDestructEnabled", "isStealthEnabled", "logCount", "", "getLogCount", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "secretCode", "getSecretCode", "selfDestructAttempts", "getSelfDestructAttempts", "()I", "clearLogs", "", "logIntruderAttempt", "attemptCount", "photoPath", "setDecoyPin", "pinHash", "setIntruderDetection", "enabled", "setSecretCode", "code", "setSelfDestruct", "attempts", "setStealthEnabled", "toggleCalculatorAlias", "showCalculator", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class StealthViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.data.local.IntruderLogDao intruderLogDao = null;
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cipher.media.data.local.IntruderLogEntity>> intruderLogs = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> logCount = null;
    
    @javax.inject.Inject()
    public StealthViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.IntruderLogDao intruderLogDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cipher.media.data.local.IntruderLogEntity>> getIntruderLogs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getLogCount() {
        return null;
    }
    
    public final boolean isStealthEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSecretCode() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDecoyPinHash() {
        return null;
    }
    
    public final boolean isIntruderDetectionEnabled() {
        return false;
    }
    
    public final int getSelfDestructAttempts() {
        return 0;
    }
    
    public final boolean isSelfDestructEnabled() {
        return false;
    }
    
    public final void setStealthEnabled(boolean enabled) {
    }
    
    public final void setSecretCode(@org.jetbrains.annotations.NotNull()
    java.lang.String code) {
    }
    
    public final void setDecoyPin(@org.jetbrains.annotations.Nullable()
    java.lang.String pinHash) {
    }
    
    public final void setIntruderDetection(boolean enabled) {
    }
    
    public final void setSelfDestruct(boolean enabled, int attempts) {
    }
    
    /**
     * Toggles between real app icon and calculator alias.
     */
    private final void toggleCalculatorAlias(boolean showCalculator) {
    }
    
    /**
     * Logs a break-in attempt with GPS location.
     * Camera capture is handled separately via CameraX.
     */
    public final void logIntruderAttempt(int attemptCount, @org.jetbrains.annotations.Nullable()
    java.lang.String photoPath) {
    }
    
    public final void clearLogs() {
    }
}