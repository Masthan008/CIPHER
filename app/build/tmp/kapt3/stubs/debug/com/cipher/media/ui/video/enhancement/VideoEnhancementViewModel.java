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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\b\u0007\u0018\u0000 <2\u00020\u0001:\u0001<B\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\rJ\u0006\u0010\u0017\u001a\u00020\u0015J\u0006\u0010\u0018\u001a\u00020\u0015J\b\u0010\u0019\u001a\u00020\u0015H\u0002J\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bJ\u0006\u0010\u001d\u001a\u00020\u0015J\u0006\u0010\u001e\u001a\u00020\u0015J\u0006\u0010\u001f\u001a\u00020\u0015J\b\u0010 \u001a\u00020\u0015H\u0014J\u0006\u0010!\u001a\u00020\u0015J\u000e\u0010\"\u001a\u00020\u00152\u0006\u0010#\u001a\u00020$J\u0016\u0010%\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u001c2\u0006\u0010\'\u001a\u00020\u001cJ\u0006\u0010(\u001a\u00020\u0015J\u0006\u0010)\u001a\u00020\u0015J\u000e\u0010*\u001a\u00020\u00152\u0006\u0010+\u001a\u00020,J\u000e\u0010-\u001a\u00020\u00152\u0006\u0010.\u001a\u00020\u001cJ\u000e\u0010/\u001a\u00020\u00152\u0006\u00100\u001a\u00020\u001cJ\u0006\u00101\u001a\u00020\u0015J\u000e\u00102\u001a\u00020\u00152\u0006\u00103\u001a\u000204J\u0006\u00105\u001a\u00020\u0015J\u0006\u00106\u001a\u00020\u0015J\b\u00107\u001a\u00020\u0015H\u0002J\u000e\u00108\u001a\u00020\u00152\u0006\u00109\u001a\u00020:J\u0006\u0010;\u001a\u00020\u0015R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\t0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006="}, d2 = {"Lcom/cipher/media/ui/video/enhancement/VideoEnhancementViewModel;", "Landroidx/lifecycle/ViewModel;", "billingRepository", "Lcom/cipher/media/billing/BillingRepository;", "context", "Landroid/content/Context;", "(Lcom/cipher/media/billing/BillingRepository;Landroid/content/Context;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/ui/video/enhancement/EnhancementUiState;", "abRepeatJob", "Lkotlinx/coroutines/Job;", "player", "Landroidx/media3/exoplayer/ExoPlayer;", "sleepTimer", "Landroid/os/CountDownTimer;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "attachPlayer", "", "exoPlayer", "cancelSleepTimer", "clearABRepeat", "executeSleepAction", "getAvailableSpeeds", "", "", "hideCropZoomDialog", "hideSleepTimerDialog", "hideSpeedSelector", "onCleared", "resetZoomPan", "setCropMode", "mode", "Lcom/cipher/media/ui/video/enhancement/CropMode;", "setPan", "dx", "dy", "setPointA", "setPointB", "setSleepTimerAction", "action", "Lcom/cipher/media/ui/video/enhancement/SleepTimerAction;", "setSpeed", "speed", "setZoomLevel", "zoom", "showCropZoomDialog", "showScreenshotFeedback", "path", "", "showSleepTimerDialog", "showSpeedSelector", "startABLoop", "startSleepTimer", "minutes", "", "togglePitchCorrection", "Companion", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class VideoEnhancementViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.billing.BillingRepository billingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.Float> FREE_SPEEDS = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.Float> PRO_SPEEDS = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<java.lang.Integer> FREE_SLEEP_PRESETS = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.ui.video.enhancement.EnhancementUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.video.enhancement.EnhancementUiState> uiState = null;
    @org.jetbrains.annotations.Nullable()
    private androidx.media3.exoplayer.ExoPlayer player;
    @org.jetbrains.annotations.Nullable()
    private android.os.CountDownTimer sleepTimer;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job abRepeatJob;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel.Companion Companion = null;
    
    @javax.inject.Inject()
    public VideoEnhancementViewModel(@org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.BillingRepository billingRepository, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.video.enhancement.EnhancementUiState> getUiState() {
        return null;
    }
    
    public final void attachPlayer(@org.jetbrains.annotations.NotNull()
    androidx.media3.exoplayer.ExoPlayer exoPlayer) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Float> getAvailableSpeeds() {
        return null;
    }
    
    public final void setSpeed(float speed) {
    }
    
    public final void togglePitchCorrection() {
    }
    
    public final void showSpeedSelector() {
    }
    
    public final void hideSpeedSelector() {
    }
    
    public final void showSleepTimerDialog() {
    }
    
    public final void hideSleepTimerDialog() {
    }
    
    public final void setSleepTimerAction(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.enhancement.SleepTimerAction action) {
    }
    
    public final void startSleepTimer(int minutes) {
    }
    
    public final void cancelSleepTimer() {
    }
    
    private final void executeSleepAction() {
    }
    
    public final void setPointA() {
    }
    
    public final void setPointB() {
    }
    
    public final void clearABRepeat() {
    }
    
    private final void startABLoop() {
    }
    
    public final void showCropZoomDialog() {
    }
    
    public final void hideCropZoomDialog() {
    }
    
    public final void setZoomLevel(float zoom) {
    }
    
    public final void setPan(float dx, float dy) {
    }
    
    public final void setCropMode(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.enhancement.CropMode mode) {
    }
    
    public final void resetZoomPan() {
    }
    
    public final void showScreenshotFeedback(@org.jetbrains.annotations.NotNull()
    java.lang.String path) {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0007\u00a8\u0006\r"}, d2 = {"Lcom/cipher/media/ui/video/enhancement/VideoEnhancementViewModel$Companion;", "", "()V", "FREE_SLEEP_PRESETS", "", "", "getFREE_SLEEP_PRESETS", "()Ljava/util/List;", "FREE_SPEEDS", "", "getFREE_SPEEDS", "PRO_SPEEDS", "getPRO_SPEEDS", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.Float> getFREE_SPEEDS() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.Float> getPRO_SPEEDS() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<java.lang.Integer> getFREE_SLEEP_PRESETS() {
            return null;
        }
    }
}