package com.cipher.media.ui.video.enhancement;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import androidx.lifecycle.ViewModel;
import androidx.media3.common.PlaybackParameters;
import androidx.media3.exoplayer.ExoPlayer;
import com.cipher.media.billing.BillingRepository;
import com.cipher.media.billing.model.SubscriptionTier;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b \n\u0002\u0010\b\n\u0002\b#\b\u0086\b\u0018\u00002\u00020\u0001B\u00d9\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0012\u001a\u00020\r\u0012\b\b\u0002\u0010\u0013\u001a\u00020\r\u0012\b\b\u0002\u0010\u0014\u001a\u00020\b\u0012\b\b\u0002\u0010\u0015\u001a\u00020\b\u0012\b\b\u0002\u0010\u0016\u001a\u00020\b\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u001eJ\t\u0010F\u001a\u00020\u0003H\u00c6\u0003J\t\u0010G\u001a\u00020\u0005H\u00c6\u0003J\t\u0010H\u001a\u00020\u0005H\u00c6\u0003J\t\u0010I\u001a\u00020\rH\u00c6\u0003J\t\u0010J\u001a\u00020\rH\u00c6\u0003J\t\u0010K\u001a\u00020\bH\u00c6\u0003J\t\u0010L\u001a\u00020\bH\u00c6\u0003J\t\u0010M\u001a\u00020\bH\u00c6\u0003J\t\u0010N\u001a\u00020\u0018H\u00c6\u0003J\t\u0010O\u001a\u00020\u0005H\u00c6\u0003J\t\u0010P\u001a\u00020\u0005H\u00c6\u0003J\t\u0010Q\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010R\u001a\u0004\u0018\u00010\u001cH\u00c6\u0003J\t\u0010S\u001a\u00020\u0005H\u00c6\u0003J\t\u0010T\u001a\u00020\u0005H\u00c6\u0003J\t\u0010U\u001a\u00020\bH\u00c6\u0003J\t\u0010V\u001a\u00020\u0005H\u00c6\u0003J\t\u0010W\u001a\u00020\u0005H\u00c6\u0003J\t\u0010X\u001a\u00020\u0005H\u00c6\u0003J\t\u0010Y\u001a\u00020\rH\u00c6\u0003J\t\u0010Z\u001a\u00020\u000fH\u00c6\u0003J\u00dd\u0001\u0010[\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u00052\b\b\u0002\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\r2\b\b\u0002\u0010\u0013\u001a\u00020\r2\b\b\u0002\u0010\u0014\u001a\u00020\b2\b\b\u0002\u0010\u0015\u001a\u00020\b2\b\b\u0002\u0010\u0016\u001a\u00020\b2\b\b\u0002\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u00052\b\b\u0002\u0010\u001a\u001a\u00020\u00052\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\\\u001a\u00020\u00052\b\u0010]\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010^\u001a\u00020=H\u00d6\u0001J\t\u0010_\u001a\u00020\u001cH\u00d6\u0001R\u0011\u0010\u0011\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010%\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b&\u0010 R\u0011\u0010\'\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b(\u0010 R\u0011\u0010)\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b)\u0010 R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010 R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010 R\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010\u0015\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010$R\u0011\u0010\u0016\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010$R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010 R\u0011\u0010\u0012\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u0011\u0010\u0013\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u00100R\u0011\u0010\u001a\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u0010 R\u0011\u0010\u0019\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010 R\u0011\u0010\u001d\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b4\u0010 R\u0011\u0010\u0010\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010 R\u0011\u0010\n\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b6\u0010 R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010 R\u0011\u0010:\u001a\u00020\u001c8F\u00a2\u0006\u0006\u001a\u0004\b;\u0010+R\u0011\u0010<\u001a\u00020=8F\u00a2\u0006\u0006\u001a\u0004\b>\u0010?R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b@\u00100R\u0011\u0010A\u001a\u00020=8F\u00a2\u0006\u0006\u001a\u0004\bB\u0010?R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010DR\u0011\u0010\u0014\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\bE\u0010$\u00a8\u0006`"}, d2 = {"Lcom/cipher/media/ui/video/enhancement/EnhancementUiState;", "", "tier", "Lcom/cipher/media/billing/model/SubscriptionTier;", "isPro", "", "isPower", "currentSpeed", "", "pitchCorrectionEnabled", "showSpeedSelector", "sleepTimerActive", "sleepTimerRemainingMs", "", "sleepTimerAction", "Lcom/cipher/media/ui/video/enhancement/SleepTimerAction;", "showSleepTimerDialog", "abRepeatEnabled", "pointA", "pointB", "zoomLevel", "panX", "panY", "cropMode", "Lcom/cipher/media/ui/video/enhancement/CropMode;", "showCropZoomDialog", "screenshotInProgress", "lastScreenshotPath", "", "showScreenshotFeedback", "(Lcom/cipher/media/billing/model/SubscriptionTier;ZZFZZZJLcom/cipher/media/ui/video/enhancement/SleepTimerAction;ZZJJFFFLcom/cipher/media/ui/video/enhancement/CropMode;ZZLjava/lang/String;Z)V", "getAbRepeatEnabled", "()Z", "getCropMode", "()Lcom/cipher/media/ui/video/enhancement/CropMode;", "getCurrentSpeed", "()F", "hasPointA", "getHasPointA", "hasPointB", "getHasPointB", "isABLooping", "getLastScreenshotPath", "()Ljava/lang/String;", "getPanX", "getPanY", "getPitchCorrectionEnabled", "getPointA", "()J", "getPointB", "getScreenshotInProgress", "getShowCropZoomDialog", "getShowScreenshotFeedback", "getShowSleepTimerDialog", "getShowSpeedSelector", "getSleepTimerAction", "()Lcom/cipher/media/ui/video/enhancement/SleepTimerAction;", "getSleepTimerActive", "sleepTimerDisplay", "getSleepTimerDisplay", "sleepTimerMinutesRemaining", "", "getSleepTimerMinutesRemaining", "()I", "getSleepTimerRemainingMs", "sleepTimerSecondsRemaining", "getSleepTimerSecondsRemaining", "getTier", "()Lcom/cipher/media/billing/model/SubscriptionTier;", "getZoomLevel", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class EnhancementUiState {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.billing.model.SubscriptionTier tier = null;
    private final boolean isPro = false;
    private final boolean isPower = false;
    private final float currentSpeed = 0.0F;
    private final boolean pitchCorrectionEnabled = false;
    private final boolean showSpeedSelector = false;
    private final boolean sleepTimerActive = false;
    private final long sleepTimerRemainingMs = 0L;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.video.enhancement.SleepTimerAction sleepTimerAction = null;
    private final boolean showSleepTimerDialog = false;
    private final boolean abRepeatEnabled = false;
    private final long pointA = 0L;
    private final long pointB = 0L;
    private final float zoomLevel = 0.0F;
    private final float panX = 0.0F;
    private final float panY = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.video.enhancement.CropMode cropMode = null;
    private final boolean showCropZoomDialog = false;
    private final boolean screenshotInProgress = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String lastScreenshotPath = null;
    private final boolean showScreenshotFeedback = false;
    
    public EnhancementUiState(@org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.model.SubscriptionTier tier, boolean isPro, boolean isPower, float currentSpeed, boolean pitchCorrectionEnabled, boolean showSpeedSelector, boolean sleepTimerActive, long sleepTimerRemainingMs, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.enhancement.SleepTimerAction sleepTimerAction, boolean showSleepTimerDialog, boolean abRepeatEnabled, long pointA, long pointB, float zoomLevel, float panX, float panY, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.enhancement.CropMode cropMode, boolean showCropZoomDialog, boolean screenshotInProgress, @org.jetbrains.annotations.Nullable()
    java.lang.String lastScreenshotPath, boolean showScreenshotFeedback) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.billing.model.SubscriptionTier getTier() {
        return null;
    }
    
    public final boolean isPro() {
        return false;
    }
    
    public final boolean isPower() {
        return false;
    }
    
    public final float getCurrentSpeed() {
        return 0.0F;
    }
    
    public final boolean getPitchCorrectionEnabled() {
        return false;
    }
    
    public final boolean getShowSpeedSelector() {
        return false;
    }
    
    public final boolean getSleepTimerActive() {
        return false;
    }
    
    public final long getSleepTimerRemainingMs() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.enhancement.SleepTimerAction getSleepTimerAction() {
        return null;
    }
    
    public final boolean getShowSleepTimerDialog() {
        return false;
    }
    
    public final boolean getAbRepeatEnabled() {
        return false;
    }
    
    public final long getPointA() {
        return 0L;
    }
    
    public final long getPointB() {
        return 0L;
    }
    
    public final float getZoomLevel() {
        return 0.0F;
    }
    
    public final float getPanX() {
        return 0.0F;
    }
    
    public final float getPanY() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.enhancement.CropMode getCropMode() {
        return null;
    }
    
    public final boolean getShowCropZoomDialog() {
        return false;
    }
    
    public final boolean getScreenshotInProgress() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLastScreenshotPath() {
        return null;
    }
    
    public final boolean getShowScreenshotFeedback() {
        return false;
    }
    
    public final int getSleepTimerMinutesRemaining() {
        return 0;
    }
    
    public final int getSleepTimerSecondsRemaining() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSleepTimerDisplay() {
        return null;
    }
    
    public final boolean getHasPointA() {
        return false;
    }
    
    public final boolean getHasPointB() {
        return false;
    }
    
    public final boolean isABLooping() {
        return false;
    }
    
    public EnhancementUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.billing.model.SubscriptionTier component1() {
        return null;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final boolean component11() {
        return false;
    }
    
    public final long component12() {
        return 0L;
    }
    
    public final long component13() {
        return 0L;
    }
    
    public final float component14() {
        return 0.0F;
    }
    
    public final float component15() {
        return 0.0F;
    }
    
    public final float component16() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.enhancement.CropMode component17() {
        return null;
    }
    
    public final boolean component18() {
        return false;
    }
    
    public final boolean component19() {
        return false;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component20() {
        return null;
    }
    
    public final boolean component21() {
        return false;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final float component4() {
        return 0.0F;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final long component8() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.enhancement.SleepTimerAction component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.enhancement.EnhancementUiState copy(@org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.model.SubscriptionTier tier, boolean isPro, boolean isPower, float currentSpeed, boolean pitchCorrectionEnabled, boolean showSpeedSelector, boolean sleepTimerActive, long sleepTimerRemainingMs, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.enhancement.SleepTimerAction sleepTimerAction, boolean showSleepTimerDialog, boolean abRepeatEnabled, long pointA, long pointB, float zoomLevel, float panX, float panY, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.enhancement.CropMode cropMode, boolean showCropZoomDialog, boolean screenshotInProgress, @org.jetbrains.annotations.Nullable()
    java.lang.String lastScreenshotPath, boolean showScreenshotFeedback) {
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