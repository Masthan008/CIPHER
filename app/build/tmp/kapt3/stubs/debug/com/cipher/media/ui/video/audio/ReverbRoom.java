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

/**
 * Defines available Reverb Room Types.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/cipher/media/ui/video/audio/ReverbRoom;", "", "id", "", "label", "", "(Ljava/lang/String;ISLjava/lang/String;)V", "getId", "()S", "getLabel", "()Ljava/lang/String;", "NONE", "SMALL_ROOM", "MEDIUM_ROOM", "LARGE_ROOM", "CONCERT_HALL", "app_debug"})
public enum ReverbRoom {
    /*public static final*/ NONE /* = new NONE(0, null) */,
    /*public static final*/ SMALL_ROOM /* = new SMALL_ROOM(0, null) */,
    /*public static final*/ MEDIUM_ROOM /* = new MEDIUM_ROOM(0, null) */,
    /*public static final*/ LARGE_ROOM /* = new LARGE_ROOM(0, null) */,
    /*public static final*/ CONCERT_HALL /* = new CONCERT_HALL(0, null) */;
    private final short id = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String label = null;
    
    ReverbRoom(short id, java.lang.String label) {
    }
    
    public final short getId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.cipher.media.ui.video.audio.ReverbRoom> getEntries() {
        return null;
    }
}