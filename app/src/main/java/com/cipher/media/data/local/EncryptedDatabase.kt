package com.cipher.media.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SupportFactory

/**
 * SQLCipher-encrypted Room database for vault items, folders, and intruder logs.
 */
@Database(
    entities = [VaultItemEntity::class, VaultFolderEntity::class, IntruderLogEntity::class],
    version = 2,
    exportSchema = false
)
abstract class EncryptedDatabase : RoomDatabase() {

    abstract fun vaultDao(): VaultDao
    abstract fun intruderLogDao(): IntruderLogDao

    companion object {
        @Volatile
        private var INSTANCE: EncryptedDatabase? = null

        fun getInstance(context: Context, passphrase: ByteArray): EncryptedDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, passphrase).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context, passphrase: ByteArray): EncryptedDatabase {
            val factory = SupportFactory(passphrase)

            return Room.databaseBuilder(
                context.applicationContext,
                EncryptedDatabase::class.java,
                "cipher_vault.db"
            )
                .openHelperFactory(factory)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
