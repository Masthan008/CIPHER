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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2 = {"Lcom/cipher/media/ui/video/enhancement/SleepTimerAction;", "", "label", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getLabel", "()Ljava/lang/String;", "PAUSE", "CLOSE", "app_debug"})
public enum SleepTimerAction {
    /*public static final*/ PAUSE /* = new PAUSE(null) */,
    /*public static final*/ CLOSE /* = new CLOSE(null) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String label = null;
    
    SleepTimerAction(java.lang.String label) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.cipher.media.ui.video.enhancement.SleepTimerAction> getEntries() {
        return null;
    }
}