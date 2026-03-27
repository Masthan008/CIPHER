package com.cipher.media.ui.audio.queue.model;

import androidx.room.*;
import kotlinx.coroutines.flow.Flow;

/**
 * Room DAO for multi-queue CRUD operations.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0012\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u000fH\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00140\u0013H\'J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00142\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0017\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0018\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0019\u001a\u00020\u001aH\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u001b\u001a\u00020\u00032\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00160\u0014H\u00a7@\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00140\u00132\u0006\u0010\u0004\u001a\u00020\u0005H\'J$\u0010 \u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00160\u0014H\u0097@\u00a2\u0006\u0002\u0010!J\u0016\u0010\"\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010#\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010$\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010%J0\u0010&\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\'\u001a\u00020\u001a2\u0006\u0010(\u001a\u00020\u000b2\b\b\u0002\u0010)\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010*J\u0016\u0010+\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010\u00a8\u0006,"}, d2 = {"Lcom/cipher/media/ui/audio/queue/model/QueueDao;", "", "clearItems", "", "queueId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deactivateAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteItem", "itemId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteQueue", "queue", "Lcom/cipher/media/ui/audio/queue/model/PlaybackQueueEntity;", "(Lcom/cipher/media/ui/audio/queue/model/PlaybackQueueEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveQueue", "getAllQueues", "Lkotlinx/coroutines/flow/Flow;", "", "getItemsForQueue", "Lcom/cipher/media/ui/audio/queue/model/QueueItemEntity;", "getQueueById", "id", "getQueueCount", "", "insertItems", "items", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertQueue", "observeItemsForQueue", "replaceQueueItems", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setActive", "updatePlaybackMode", "mode", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePlaybackPosition", "index", "positionMs", "updatedAt", "(Ljava/lang/String;IJJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateQueue", "app_debug"})
@androidx.room.Dao()
public abstract interface QueueDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertQueue(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity queue, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateQueue(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity queue, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteQueue(@org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity queue, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM playback_queues ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity>> getAllQueues();
    
    @androidx.room.Query(value = "SELECT * FROM playback_queues WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQueueById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM playback_queues WHERE isActive = 1 LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getActiveQueue(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity> $completion);
    
    @androidx.room.Query(value = "UPDATE playback_queues SET isActive = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deactivateAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE playback_queues SET isActive = 1 WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object setActive(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM playback_queues")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getQueueCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "UPDATE playback_queues SET currentIndex = :index, currentPositionMs = :positionMs, updatedAt = :updatedAt WHERE id = :queueId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updatePlaybackPosition(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, int index, long positionMs, long updatedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE playback_queues SET playbackMode = :mode WHERE id = :queueId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updatePlaybackMode(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    java.lang.String mode, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertItems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.ui.audio.queue.model.QueueItemEntity> items, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM queue_items WHERE queueId = :queueId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearItems(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM queue_items WHERE queueId = :queueId ORDER BY position ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getItemsForQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.cipher.media.ui.audio.queue.model.QueueItemEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM queue_items WHERE queueId = :queueId ORDER BY position ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.cipher.media.ui.audio.queue.model.QueueItemEntity>> observeItemsForQueue(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId);
    
    @androidx.room.Query(value = "DELETE FROM queue_items WHERE id = :itemId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteItem(long itemId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Transaction()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object replaceQueueItems(@org.jetbrains.annotations.NotNull()
    java.lang.String queueId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.ui.audio.queue.model.QueueItemEntity> items, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Room DAO for multi-queue CRUD operations.
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
        
        @androidx.room.Transaction()
        @org.jetbrains.annotations.Nullable()
        public static java.lang.Object replaceQueueItems(@org.jetbrains.annotations.NotNull()
        com.cipher.media.ui.audio.queue.model.QueueDao $this, @org.jetbrains.annotations.NotNull()
        java.lang.String queueId, @org.jetbrains.annotations.NotNull()
        java.util.List<com.cipher.media.ui.audio.queue.model.QueueItemEntity> items, @org.jetbrains.annotations.NotNull()
        kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
            return null;
        }
    }
}