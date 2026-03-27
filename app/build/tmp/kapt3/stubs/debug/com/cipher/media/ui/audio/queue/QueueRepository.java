package com.cipher.media.ui.audio.queue;

import com.cipher.media.data.model.AudioItem;
import com.cipher.media.ui.audio.queue.model.*;
import kotlinx.coroutines.flow.Flow;
import java.util.UUID;

/**
 * Repository for multi-queue CRUD operations.
 * Bridges Room DAO with domain models.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 92\u00020\u0001:\u00019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0011J,\u0010\u0012\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0013\u001a\u00020\u000e2\b\b\u0002\u0010\u0014\u001a\u00020\u000e2\b\b\u0002\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0019J \u0010\u001a\u001a\u0004\u0018\u00010\b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u000e\u0010\u001c\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0018\u0010 \u001a\u0004\u0018\u00010\u001f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010!\u001a\u00020\"H\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\bH\u0082@\u00a2\u0006\u0002\u0010%J\u0016\u0010&\u001a\u00020\f2\u0006\u0010\'\u001a\u00020(H\u0086@\u00a2\u0006\u0002\u0010)J\u001e\u0010*\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010+\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010,J&\u0010-\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010.\u001a\u00020\"2\u0006\u0010/\u001a\u00020(H\u0086@\u00a2\u0006\u0002\u00100J\u001e\u00101\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u00102\u001a\u000203H\u0086@\u00a2\u0006\u0002\u00104J$\u00105\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u00106\u001a\b\u0012\u0004\u0012\u00020\u00100\u0007H\u0086@\u00a2\u0006\u0002\u00107J\u0016\u00108\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0086@\u00a2\u0006\u0002\u0010\u0019R\u001d\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006:"}, d2 = {"Lcom/cipher/media/ui/audio/queue/QueueRepository;", "", "dao", "Lcom/cipher/media/ui/audio/queue/model/QueueDao;", "(Lcom/cipher/media/ui/audio/queue/model/QueueDao;)V", "allQueues", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/cipher/media/ui/audio/queue/model/PlaybackQueueEntity;", "getAllQueues", "()Lkotlinx/coroutines/flow/Flow;", "addToQueue", "", "queueId", "", "audio", "Lcom/cipher/media/data/model/AudioItem;", "(Ljava/lang/String;Lcom/cipher/media/data/model/AudioItem;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createQueue", "name", "iconName", "isPro", "", "(Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteQueue", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "duplicateQueue", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "ensureDefaultQueue", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveQueue", "Lcom/cipher/media/ui/audio/queue/model/PlaybackQueue;", "getQueue", "getQueueCount", "", "loadFullQueue", "entity", "(Lcom/cipher/media/ui/audio/queue/model/PlaybackQueueEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeFromQueue", "itemId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "renameQueue", "newName", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "savePosition", "index", "positionMs", "(Ljava/lang/String;IJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setPlaybackMode", "mode", "Lcom/cipher/media/ui/audio/queue/model/PlaybackMode;", "(Ljava/lang/String;Lcom/cipher/media/ui/audio/queue/model/PlaybackMode;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setQueueItems", "audioItems", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "switchQueue", "Companion", "app_debug"})
public final class QueueRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.audio.queue.model.QueueDao dao = null;
    public static final int FREE_QUEUE_LIMIT = 5;
    public static final int PRO_QUEUE_LIMIT = 20;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DEFAULT_QUEUE_NAME = "Now Playing";
    
    /**
     * Observe all queues (metadata only, no items).
     */
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.util.List<com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity>> allQueues = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.ui.audio.queue.QueueRepository.Companion Companion = null;
    
    public QueueRepository(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.queue.model.QueueDao dao) {
        super();
    }
    
    /**
     * Observe all queues (metadata only, no items).
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity>> getAllQueues() {
        return null;
    }
    
    /**
     * Get queue count for tier-gating.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getQueueCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    /**
     * Create a new queue. Returns null if tier limit reached.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String iconName, boolean isPro, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity> $completion) {
        return null;
    }
    
    /**
     * Delete a queue and its items (CASCADE).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Rename a queue.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object renameQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    java.lang.String newName, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Duplicate a queue (copies items).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object duplicateQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, boolean isPro, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity> $completion) {
        return null;
    }
    
    /**
     * Switch active queue.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object switchQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Get the currently active queue with items.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getActiveQueue(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.cipher.media.ui.audio.queue.model.PlaybackQueue> $completion) {
        return null;
    }
    
    /**
     * Get a specific queue with all its items.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.cipher.media.ui.audio.queue.model.PlaybackQueue> $completion) {
        return null;
    }
    
    /**
     * Save playback position for active queue.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object savePosition(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, int index, long positionMs, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Update playback mode.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setPlaybackMode(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.queue.model.PlaybackMode mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Replace all items in a queue.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setQueueItems(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.data.model.AudioItem> audioItems, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Add a single track to a queue.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addToQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.AudioItem audio, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Remove a track from a queue.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeFromQueue(long itemId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Ensure a default queue exists.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object ensureDefaultQueue(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object loadFullQueue(com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity entity, kotlin.coroutines.Continuation<? super com.cipher.media.ui.audio.queue.model.PlaybackQueue> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/cipher/media/ui/audio/queue/QueueRepository$Companion;", "", "()V", "DEFAULT_QUEUE_NAME", "", "FREE_QUEUE_LIMIT", "", "PRO_QUEUE_LIMIT", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}