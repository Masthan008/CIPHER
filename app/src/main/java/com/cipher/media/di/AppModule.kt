package com.cipher.media.di

import android.content.ContentResolver
import android.content.Context
import com.cipher.media.data.encryption.CryptoUtil
import com.cipher.media.data.encryption.KeystoreManager
import com.cipher.media.data.encryption.VaultEncryptionManager
import com.cipher.media.data.local.EncryptedDatabase
import com.cipher.media.data.local.VaultDao
import com.cipher.media.data.repository.EqualizerRepository
import com.cipher.media.data.repository.MediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
}
