package com.cipher.media.data.local;

import androidx.room.*;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000b0\nH\'J\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nH\'J\u0016\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\u000f"}, d2 = {"Lcom/cipher/media/data/local/IntruderLogDao;", "", "clearAllLogs", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteLog", "log", "Lcom/cipher/media/data/local/IntruderLogEntity;", "(Lcom/cipher/media/data/local/IntruderLogEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllLogs", "Lkotlinx/coroutines/flow/Flow;", "", "getLogCount", "", "insertLog", "app_debug"})
@androidx.room.Dao()
public abstract interface IntruderLogDao {
    
    @androidx.room.Query(value = "SELECT * FROM intruder_logs ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.cipher.media.data.local.IntruderLogEntity>> getAllLogs();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM intruder_logs")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Integer> getLogCount();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertLog(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.IntruderLogEntity log, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM intruder_logs")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object clearAllLogs(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteLog(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.IntruderLogEntity log, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}