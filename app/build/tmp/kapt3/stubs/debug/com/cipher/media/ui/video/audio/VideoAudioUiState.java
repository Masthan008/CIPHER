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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\"\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0089\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0007\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0015J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00110\u0007H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0013H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010+\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010,\u001a\u00020\bH\u00c6\u0003J\t\u0010-\u001a\u00020\bH\u00c6\u0003J\t\u0010.\u001a\u00020\bH\u00c6\u0003J\t\u0010/\u001a\u00020\rH\u00c6\u0003J\t\u00100\u001a\u00020\u000fH\u00c6\u0003J\u008d\u0001\u00101\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0003H\u00c6\u0001J\u0013\u00102\u001a\u00020\u00032\b\u00103\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00104\u001a\u00020\bH\u00d6\u0001J\t\u00105\u001a\u000206H\u00d6\u0001R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u001dR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u001dR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u001dR\u0011\u0010\u000b\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001cR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0014\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001dR\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001c\u00a8\u00067"}, d2 = {"Lcom/cipher/media/ui/video/audio/VideoAudioUiState;", "", "isPro", "", "isPower", "isHiRes", "bandGains", "", "", "bassBoostStrength", "virtualizerStrength", "loudnessEnhancerGain", "reverbPreset", "Lcom/cipher/media/ui/video/audio/ReverbRoom;", "selectedPreset", "Lcom/cipher/media/ui/video/audio/model/EQPreset;", "availableAudioTracks", "Lcom/cipher/media/ui/video/audio/model/AudioTrackInfo;", "audioDelayMs", "", "showBottomSheet", "(ZZZLjava/util/List;IIILcom/cipher/media/ui/video/audio/ReverbRoom;Lcom/cipher/media/ui/video/audio/model/EQPreset;Ljava/util/List;JZ)V", "getAudioDelayMs", "()J", "getAvailableAudioTracks", "()Ljava/util/List;", "getBandGains", "getBassBoostStrength", "()I", "()Z", "getLoudnessEnhancerGain", "getReverbPreset", "()Lcom/cipher/media/ui/video/audio/ReverbRoom;", "getSelectedPreset", "()Lcom/cipher/media/ui/video/audio/model/EQPreset;", "getShowBottomSheet", "getVirtualizerStrength", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"})
public final class VideoAudioUiState {
    private final boolean isPro = false;
    private final boolean isPower = false;
    private final boolean isHiRes = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.Integer> bandGains = null;
    private final int bassBoostStrength = 0;
    private final int virtualizerStrength = 0;
    private final int loudnessEnhancerGain = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.video.audio.ReverbRoom reverbPreset = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.video.audio.model.EQPreset selectedPreset = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.cipher.media.ui.video.audio.model.AudioTrackInfo> availableAudioTracks = null;
    private final long audioDelayMs = 0L;
    private final boolean showBottomSheet = false;
    
    public VideoAudioUiState(boolean isPro, boolean isPower, boolean isHiRes, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> bandGains, int bassBoostStrength, int virtualizerStrength, int loudnessEnhancerGain, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.audio.ReverbRoom reverbPreset, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.audio.model.EQPreset selectedPreset, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.ui.video.audio.model.AudioTrackInfo> availableAudioTracks, long audioDelayMs, boolean showBottomSheet) {
        super();
    }
    
    public final boolean isPro() {
        return false;
    }
    
    public final boolean isPower() {
        return false;
    }
    
    public final boolean isHiRes() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> getBandGains() {
        return null;
    }
    
    public final int getBassBoostStrength() {
        return 0;
    }
    
    public final int getVirtualizerStrength() {
        return 0;
    }
    
    public final int getLoudnessEnhancerGain() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.audio.ReverbRoom getReverbPreset() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.audio.model.EQPreset getSelectedPreset() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.ui.video.audio.model.AudioTrackInfo> getAvailableAudioTracks() {
        return null;
    }
    
    public final long getAudioDelayMs() {
        return 0L;
    }
    
    public final boolean getShowBottomSheet() {
        return false;
    }
    
    public VideoAudioUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.ui.video.audio.model.AudioTrackInfo> component10() {
        return null;
    }
    
    public final long component11() {
        return 0L;
    }
    
    public final boolean component12() {
        return false;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final boolean component3() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.Integer> component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.audio.ReverbRoom component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.audio.model.EQPreset component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.video.audio.VideoAudioUiState copy(boolean isPro, boolean isPower, boolean isHiRes, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.Integer> bandGains, int bassBoostStrength, int virtualizerStrength, int loudnessEnhancerGain, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.audio.ReverbRoom reverbPreset, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.audio.model.EQPreset selectedPreset, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.ui.video.audio.model.AudioTrackInfo> availableAudioTracks, long audioDelayMs, boolean showBottomSheet) {
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