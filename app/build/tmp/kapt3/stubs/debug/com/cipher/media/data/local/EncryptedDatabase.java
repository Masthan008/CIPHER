package com.cipher.media.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import net.sqlcipher.database.SupportFactory;

/**
 * SQLCipher-encrypted Room database for vault items, folders, and intruder logs.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\b"}, d2 = {"Lcom/cipher/media/data/local/EncryptedDatabase;", "Landroidx/room/RoomDatabase;", "()V", "intruderLogDao", "Lcom/cipher/media/data/local/IntruderLogDao;", "vaultDao", "Lcom/cipher/media/data/local/VaultDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.cipher.media.data.local.VaultItemEntity.class, com.cipher.media.data.local.VaultFolderEntity.class, com.cipher.media.data.local.IntruderLogEntity.class}, version = 2, exportSchema = false)
public abstract class EncryptedDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.cipher.media.data.local.EncryptedDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.data.local.EncryptedDatabase.Companion Companion = null;
    
    public EncryptedDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.cipher.media.data.local.VaultDao vaultDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.cipher.media.data.local.IntruderLogDao intruderLogDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J\u0016\u0010\n\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/cipher/media/data/local/EncryptedDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/cipher/media/data/local/EncryptedDatabase;", "buildDatabase", "context", "Landroid/content/Context;", "passphrase", "", "getInstance", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cipher.media.data.local.EncryptedDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        byte[] passphrase) {
            return null;
        }
        
        private final com.cipher.media.data.local.EncryptedDatabase buildDatabase(android.content.Context context, byte[] passphrase) {
            return null;
        }
    }
}