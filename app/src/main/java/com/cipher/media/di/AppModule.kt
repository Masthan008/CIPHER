package com.cipher.media.di

import android.content.ContentResolver
import android.content.Context
import com.cipher.media.data.local.MediaDatabase
import com.cipher.media.data.local.VideoPreferencesDao
import androidx.room.Room
import com.cipher.media.data.encryption.CryptoUtil
import com.cipher.media.data.encryption.KeystoreManager
import com.cipher.media.data.encryption.VaultEncryptionManager
import com.cipher.media.data.local.EncryptedDatabase
import com.cipher.media.data.local.VaultDao
import com.cipher.media.data.local.IntruderLogDao
import com.cipher.media.data.repository.EqualizerRepository
import com.cipher.media.data.repository.MediaRepository
import com.cipher.media.service.PlayerManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    @Singleton
    fun provideMediaRepository(contentResolver: ContentResolver): MediaRepository {
        return MediaRepository(contentResolver)
    }

    @Provides
    @Singleton
    fun provideEqualizerRepository(@ApplicationContext context: Context): EqualizerRepository {
        return EqualizerRepository(context)
    }

    @Provides
    @Singleton
    fun provideKeystoreManager(): KeystoreManager {
        return KeystoreManager()
    }

    @Provides
    @Singleton
    fun provideCryptoUtil(): CryptoUtil {
        return CryptoUtil()
    }

    @Provides
    @Singleton
    fun provideVaultEncryptionManager(
        @ApplicationContext context: Context,
        keystoreManager: KeystoreManager,
        cryptoUtil: CryptoUtil
    ): VaultEncryptionManager {
        return VaultEncryptionManager(context, keystoreManager, cryptoUtil)
    }

    @Provides
    @Singleton
    fun provideEncryptedDatabase(
        @ApplicationContext context: Context,
        keystoreManager: KeystoreManager
    ): EncryptedDatabase {
        // Derive a stable passphrase from the Keystore DB key
        val dbKey = keystoreManager.getOrCreateDbKey()
        val passphrase = dbKey.encoded ?: "cipher_fallback_key".toByteArray()
        return EncryptedDatabase.getInstance(context, passphrase)
    }

    @Provides
    @Singleton
    fun provideVaultDao(database: EncryptedDatabase): VaultDao {
        return database.vaultDao()
    }

    @Provides
    @Singleton
    fun provideIntruderLogDao(database: EncryptedDatabase): com.cipher.media.data.local.IntruderLogDao {
        return database.intruderLogDao()
    }

    @Provides
    @Singleton
    fun provideBillingManager(@ApplicationContext context: Context): com.cipher.media.billing.BillingManager {
        return com.cipher.media.billing.BillingManager(context)
    }

    @Provides
    @Singleton
    fun provideBillingRepository(billingManager: com.cipher.media.billing.BillingManager): com.cipher.media.billing.BillingRepository {
        return com.cipher.media.billing.BillingRepository(billingManager)
    }

    @Provides
    @Singleton
    fun provideAdManager(@ApplicationContext context: Context): com.cipher.media.ads.AdManager {
        return com.cipher.media.ads.AdManager(context)
    }

    @Provides
    @Singleton
    fun provideMediaDatabase(@ApplicationContext context: Context): MediaDatabase {
        return Room.databaseBuilder(
            context,
            MediaDatabase::class.java,
            "cipher_media.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideVideoPreferencesDao(database: MediaDatabase): VideoPreferencesDao {
        return database.videoPreferencesDao()
    }

    @Provides
    @Singleton
    fun provideMediaDao(database: MediaDatabase): com.cipher.media.data.local.MediaDao {
        return database.mediaDao()
    }

    @Provides
    @Singleton
    fun provideQueueDao(database: MediaDatabase): com.cipher.media.ui.audio.queue.model.QueueDao {
        return database.queueDao()
    }

    @Provides
    @Singleton
    fun provideQueueRepository(queueDao: com.cipher.media.ui.audio.queue.model.QueueDao): com.cipher.media.ui.audio.queue.QueueRepository {
        return com.cipher.media.ui.audio.queue.QueueRepository(queueDao)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providePlayerManager(@ApplicationContext context: Context): PlayerManager {
        return PlayerManager(context)
    }
}
