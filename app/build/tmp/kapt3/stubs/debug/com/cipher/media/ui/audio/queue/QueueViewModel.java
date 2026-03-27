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
 * ViewModel for multiple queue management.
 * FREE: 5 queues, PRO: 20 queues.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u000fJ\u0018\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u00112\b\b\u0002\u0010\u0017\u001a\u00020\u0011J\u000e\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u001a\u001a\u00020\u000fJ\u0006\u0010\u001b\u001a\u00020\u000fJ\u0006\u0010\u001c\u001a\u00020\u000fJ\u000e\u0010\u001d\u001a\u00020\u000fH\u0082@\u00a2\u0006\u0002\u0010\u001eJ\u000e\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020!J\u0016\u0010\"\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010#\u001a\u00020\u0011J\u0016\u0010$\u001a\u00020\u000f2\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020!J\u000e\u0010(\u001a\u00020\u000f2\u0006\u0010)\u001a\u00020*J\u001c\u0010+\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00130-J\u0006\u0010.\u001a\u00020\u000fJ\u0006\u0010/\u001a\u00020\u000fJ\u000e\u00100\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u00061"}, d2 = {"Lcom/cipher/media/ui/audio/queue/QueueViewModel;", "Landroidx/lifecycle/ViewModel;", "repo", "Lcom/cipher/media/ui/audio/queue/QueueRepository;", "billingRepository", "Lcom/cipher/media/billing/BillingRepository;", "(Lcom/cipher/media/ui/audio/queue/QueueRepository;Lcom/cipher/media/billing/BillingRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/ui/audio/queue/QueueUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addToQueue", "", "queueId", "", "audio", "Lcom/cipher/media/data/model/AudioItem;", "clearError", "createQueue", "name", "iconName", "deleteQueue", "duplicateQueue", "hideCreateDialog", "hideQueueList", "loadActiveQueue", "refreshCanCreate", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeFromQueue", "itemId", "", "renameQueue", "newName", "savePlaybackPosition", "index", "", "positionMs", "setPlaybackMode", "mode", "Lcom/cipher/media/ui/audio/queue/model/PlaybackMode;", "setQueueItems", "items", "", "showCreateDialog", "showQueueList", "switchQueue", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class QueueViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.audio.queue.QueueRepository repo = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.billing.BillingRepository billingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.ui.audio.queue.QueueUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.audio.queue.QueueUiState> uiState = null;
    
    @javax.inject.Inject()
    public QueueViewModel(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.queue.QueueRepository repo, @org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.BillingRepository billingRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.audio.queue.QueueUiState> getUiState() {
        return null;
    }
    
    public final void createQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String iconName) {
    }
    
    public final void deleteQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId) {
    }
    
    public final void renameQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    java.lang.String newName) {
    }
    
    public final void duplicateQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId) {
    }
    
    public final void switchQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId) {
    }
    
    public final void loadActiveQueue() {
    }
    
    public final void setQueueItems(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.data.model.AudioItem> items) {
    }
    
    public final void addToQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.AudioItem audio) {
    }
    
    public final void removeFromQueue(long itemId) {
    }
    
    public final void savePlaybackPosition(int index, long positionMs) {
    }
    
    public final void setPlaybackMode(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.queue.model.PlaybackMode mode) {
    }
    
    public final void showCreateDialog() {
    }
    
    public final void hideCreateDialog() {
    }
    
    public final void showQueueList() {
    }
    
    public final void hideQueueList() {
    }
    
    public final void clearError() {
    }
    
    private final java.lang.Object refreshCanCreate(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}