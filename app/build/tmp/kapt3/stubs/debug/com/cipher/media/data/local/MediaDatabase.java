package com.cipher.media.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.cipher.media.data.local.entity.MediaEntity;
import com.cipher.media.data.local.entity.VideoPreferencesEntity;
import com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity;
import com.cipher.media.ui.audio.queue.model.QueueItemEntity;
import com.cipher.media.ui.audio.queue.model.QueueDao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\t"}, d2 = {"Lcom/cipher/media/data/local/MediaDatabase;", "Landroidx/room/RoomDatabase;", "()V", "mediaDao", "Lcom/cipher/media/data/local/MediaDao;", "queueDao", "Lcom/cipher/media/ui/audio/queue/model/QueueDao;", "videoPreferencesDao", "Lcom/cipher/media/data/local/VideoPreferencesDao;", "app_debug"})
@androidx.room.Database(entities = {com.cipher.media.data.local.entity.MediaEntity.class, com.cipher.media.data.local.entity.VideoPreferencesEntity.class, com.cipher.media.ui.audio.queue.model.PlaybackQueueEntity.class, com.cipher.media.ui.audio.queue.model.QueueItemEntity.class}, version = 5, exportSchema = false)
public abstract class MediaDatabase extends androidx.room.RoomDatabase {
    
    public MediaDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.cipher.media.data.local.MediaDao mediaDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.cipher.media.data.local.VideoPreferencesDao videoPreferencesDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.cipher.media.ui.audio.queue.model.QueueDao queueDao();
}