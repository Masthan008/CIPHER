package com.cipher.media.ui.video.audio;

import android.media.audiofx.PresetReverb;
import androidx.lifecycle.ViewModel;
import androidx.media3.common.C;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.TrackSelectionOverride;
import androidx.media3.exoplayer.ExoPlayer;
import com.cipher.media.billing.BillingRepository;
import com.cipher.media.billing.model.SubscriptionTier;
import com.cipher.media.ui.video.audio.model.AudioTrackInfo;
import com.cipher.media.ui.video.audio.model.EQPreset;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u000e\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u000bJ\u0006\u0010\u0014\u001a\u00020\u0011J\b\u0010\u0015\u001a\u00020\u0011H\u0014J\u0006\u0010\u0016\u001a\u00020\u0011J\u000e\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u001fJ\u0016\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\"J\u000e\u0010$\u001a\u00020\u00112\u0006\u0010%\u001a\u00020\"J\u001e\u0010&\u001a\u00020\u00112\u0006\u0010\'\u001a\u00020\"2\u0006\u0010(\u001a\u00020\"2\u0006\u0010)\u001a\u00020\"J\u000e\u0010*\u001a\u00020\u00112\u0006\u0010+\u001a\u00020\"J\u000e\u0010,\u001a\u00020\u00112\u0006\u0010-\u001a\u00020.J\u000e\u0010/\u001a\u00020\u00112\u0006\u0010%\u001a\u00020\"J\u0006\u00100\u001a\u00020\u0011R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u00061"}, d2 = {"Lcom/cipher/media/ui/video/audio/VideoEqualizerViewModel;", "Landroidx/lifecycle/ViewModel;", "billingRepository", "Lcom/cipher/media/billing/BillingRepository;", "(Lcom/cipher/media/billing/BillingRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/ui/video/audio/VideoAudioUiState;", "audioManager", "Lcom/cipher/media/ui/video/audio/VideoAudioEffectsManager;", "exoPlayer", "Landroidx/media3/exoplayer/ExoPlayer;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "applyTierConstraints", "", "attachPlayer", "player", "hideBottomSheet", "onCleared", "refreshAudioTracks", "selectAudioTrack", "info", "Lcom/cipher/media/ui/video/audio/model/AudioTrackInfo;", "selectPreset", "preset", "Lcom/cipher/media/ui/video/audio/model/EQPreset;", "setAudioDelay", "delayMs", "", "setBandGain", "logicalIndex", "", "gainMillibels", "setBassBoost", "strength", "setFreeTier3Band", "lowMb", "midMb", "highMb", "setLoudnessEnhancer", "gain", "setReverb", "room", "Lcom/cipher/media/ui/video/audio/ReverbRoom;", "setVirtualizer", "showBottomSheet", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class VideoEqualizerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.billing.BillingRepository billingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.video.audio.VideoAudioEffectsManager audioManager = null;
    @org.jetbrains.annotations.Nullable()
    private androidx.media3.exoplayer.ExoPlayer exoPlayer;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.ui.video.audio.VideoAudioUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.video.audio.VideoAudioUiState> uiState = null;
    
    @javax.inject.Inject()
    public VideoEqualizerViewModel(@org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.BillingRepository billingRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.video.audio.VideoAudioUiState> getUiState() {
        return null;
    }
    
    /**
     * Attach the ViewModel to the active ExoPlayer instance.
     */
    public final void attachPlayer(@org.jetbrains.annotations.NotNull()
    androidx.media3.exoplayer.ExoPlayer player) {
    }
    
    private final void applyTierConstraints() {
    }
    
    public final void setBandGain(int logicalIndex, int gainMillibels) {
    }
    
    public final void setFreeTier3Band(int lowMb, int midMb, int highMb) {
    }
    
    public final void selectPreset(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.audio.model.EQPreset preset) {
    }
    
    public final void setBassBoost(int strength) {
    }
    
    public final void setVirtualizer(int strength) {
    }
    
    public final void setLoudnessEnhancer(int gain) {
    }
    
    public final void setReverb(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.audio.ReverbRoom room) {
    }
    
    public final void refreshAudioTracks() {
    }
    
    public final void selectAudioTrack(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.audio.model.AudioTrackInfo info) {
    }
    
    public final void setAudioDelay(long delayMs) {
    }
    
    public final void showBottomSheet() {
    }
    
    public final void hideBottomSheet() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}