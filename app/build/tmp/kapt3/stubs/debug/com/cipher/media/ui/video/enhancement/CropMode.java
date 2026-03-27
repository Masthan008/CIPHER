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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f\u00a8\u0006\u0010"}, d2 = {"Lcom/cipher/media/ui/video/enhancement/CropMode;", "", "label", "", "proOnly", "", "(Ljava/lang/String;ILjava/lang/String;Z)V", "getLabel", "()Ljava/lang/String;", "getProOnly", "()Z", "FIT", "FILL", "RATIO_16_9", "RATIO_4_3", "CUSTOM", "app_debug"})
public enum CropMode {
    /*public static final*/ FIT /* = new FIT(null, false) */,
    /*public static final*/ FILL /* = new FILL(null, false) */,
    /*public static final*/ RATIO_16_9 /* = new RATIO_16_9(null, false) */,
    /*public static final*/ RATIO_4_3 /* = new RATIO_4_3(null, false) */,
    /*public static final*/ CUSTOM /* = new CUSTOM(null, false) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String label = null;
    private final boolean proOnly = false;
    
    CropMode(java.lang.String label, boolean proOnly) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel() {
        return null;
    }
    
    public final boolean getProOnly() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.cipher.media.ui.video.enhancement.CropMode> getEntries() {
        return null;
    }
}