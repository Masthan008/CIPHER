package com.cipher.media.ui.audio.queue;

import androidx.lifecycle.ViewModel;
import com.cipher.media.billing.BillingRepository;
import com.cipher.media.billing.model.SubscriptionTier;
import com.cipher.media.data.model.AudioItem;
import com.cipher.media.ui.audio.queue.model.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

/**
 * UI state for multi-queue management.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\u0016\b\u0086\b\u0018\u00002\u00020\u0001Ba\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\n\u0012\b\b\u0002\u0010\r\u001a\u00020\n\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u000fJ\u000f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\t\u0010$\u001a\u00020\nH\u00c6\u0003J\t\u0010%\u001a\u00020\nH\u00c6\u0003J\t\u0010&\u001a\u00020\nH\u00c6\u0003J\t\u0010\'\u001a\u00020\nH\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003Je\u0010)\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\b\b\u0002\u0010\r\u001a\u00020\n2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001J\u0013\u0010*\u001a\u00020\n2\b\u0010+\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010,\u001a\u00020\u0018H\u00d6\u0001J\t\u0010-\u001a\u00020\u0006H\u00d6\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0015R\u0011\u0010\u0017\u001a\u00020\u00188F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u00188F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001aR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\f\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015R\u0011\u0010\r\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0015\u00a8\u0006."}, d2 = {"Lcom/cipher/media/ui/audio/queue/QueueUiState;", "", "queues", "", "Lcom/cipher/media/ui/audio/queue/model/PlaybackQueueEntity;", "activeQueueId", "", "activeQueue", "Lcom/cipher/media/ui/audio/queue/model/PlaybackQueue;", "isPro", "", "canCreateMore", "showCreateDialog", "showQueueList", "error", "(Ljava/util/List;Ljava/lang/String;Lcom/cipher/media/ui/audio/queue/model/PlaybackQueue;ZZZZLjava/lang/String;)V", "getActiveQueue", "()Lcom/cipher/media/ui/audio/queue/model/PlaybackQueue;", "getActiveQueueId", "()Ljava/lang/String;", "getCanCreateMore", "()Z", "getError", "queueCount", "", "getQueueCount", "()I", "queueLimit", "getQueueLimit", "getQueues", "()Ljava/util/List;", "getShowCreateDialog", "getShowQueueList", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class QueueUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity> queues = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String activeQueueId = null;
    @org.jetbrains.annotations.Nullable()
    private final com.cipher.media.ui.audio.queue.model.PlaybackQueue activeQueue = null;
    private final boolean isPro = false;
    private final boolean canCreateMore = false;
    private final boolean showCreateDialog = false;
    private final boolean showQueueList = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    
    public QueueUiState(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity> queues, @org.jetbrains.annotations.Nullable()
    java.lang.String activeQueueId, @org.jetbrains.annotations.Nullable()
    com.cipher.media.ui.audio.queue.model.PlaybackQueue activeQueue, boolean isPro, boolean canCreateMore, boolean showCreateDialog, boolean showQueueList, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity> getQueues() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getActiveQueueId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.cipher.media.ui.audio.queue.model.PlaybackQueue getActiveQueue() {
        return null;
    }
    
    public final boolean isPro() {
        return false;
    }
    
    public final boolean getCanCreateMore() {
        return false;
    }
    
    public final boolean getShowCreateDialog() {
        return false;
    }
    
    public final boolean getShowQueueList() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public final int getQueueLimit() {
        return 0;
    }
    
    public final int getQueueCount() {
        return 0;
    }
    
    public QueueUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.cipher.media.ui.audio.queue.model.PlaybackQueue component3() {
        return null;
    }
    
    public final boolean component4() {
        return false;
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.audio.queue.QueueUiState copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity> queues, @org.jetbrains.annotations.Nullable()
    java.lang.String activeQueueId, @org.jetbrains.annotations.Nullable()
    com.cipher.media.ui.audio.queue.model.PlaybackQueue activeQueue, boolean isPro, boolean canCreateMore, boolean showCreateDialog, boolean showQueueList, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
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