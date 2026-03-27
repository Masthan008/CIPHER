package com.cipher.media.di;

import android.content.ContentResolver;
import android.content.Context;
import com.cipher.media.data.encryption.CryptoUtil;
import com.cipher.media.data.encryption.KeystoreManager;
import com.cipher.media.data.encryption.VaultEncryptionManager;
import com.cipher.media.data.local.EncryptedDatabase;
import com.cipher.media.data.local.VaultDao;
import com.cipher.media.data.repository.EqualizerRepository;
import com.cipher.media.data.repository.MediaRepository;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\bH\u0007J\u0012\u0010\f\u001a\u00020\r2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\u000e\u001a\u00020\u000fH\u0007J\u001a\u0010\u0010\u001a\u00020\u00112\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0012\u0010\u0014\u001a\u00020\u00152\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0011H\u0007J\b\u0010\u0019\u001a\u00020\u0013H\u0007J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\rH\u0007J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0018\u001a\u00020\u0011H\u0007J\"\u0010\u001f\u001a\u00020 2\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010!\u001a\u00020\u000fH\u0007\u00a8\u0006\""}, d2 = {"Lcom/cipher/media/di/AppModule;", "", "()V", "provideAdManager", "Lcom/cipher/media/ads/AdManager;", "context", "Landroid/content/Context;", "provideBillingManager", "Lcom/cipher/media/billing/BillingManager;", "provideBillingRepository", "Lcom/cipher/media/billing/BillingRepository;", "billingManager", "provideContentResolver", "Landroid/content/ContentResolver;", "provideCryptoUtil", "Lcom/cipher/media/data/encryption/CryptoUtil;", "provideEncryptedDatabase", "Lcom/cipher/media/data/local/EncryptedDatabase;", "keystoreManager", "Lcom/cipher/media/data/encryption/KeystoreManager;", "provideEqualizerRepository", "Lcom/cipher/media/data/repository/EqualizerRepository;", "provideIntruderLogDao", "Lcom/cipher/media/data/local/IntruderLogDao;", "database", "provideKeystoreManager", "provideMediaRepository", "Lcom/cipher/media/data/repository/MediaRepository;", "contentResolver", "provideVaultDao", "Lcom/cipher/media/data/local/VaultDao;", "provideVaultEncryptionManager", "Lcom/cipher/media/data/encryption/VaultEncryptionManager;", "cryptoUtil", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final android.content.ContentResolver provideContentResolver(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.repository.MediaRepository provideMediaRepository(@org.jetbrains.annotations.NotNull()
    android.content.ContentResolver contentResolver) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.repository.EqualizerRepository provideEqualizerRepository(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.encryption.KeystoreManager provideKeystoreManager() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.encryption.CryptoUtil provideCryptoUtil() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.encryption.VaultEncryptionManager provideVaultEncryptionManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.encryption.KeystoreManager keystoreManager, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.encryption.CryptoUtil cryptoUtil) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.local.EncryptedDatabase provideEncryptedDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.encryption.KeystoreManager keystoreManager) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.local.VaultDao provideVaultDao(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.EncryptedDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.local.IntruderLogDao provideIntruderLogDao(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.EncryptedDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.billing.BillingManager provideBillingManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.billing.BillingRepository provideBillingRepository(@org.jetbrains.annotations.NotNull()
    com.cipher.media.billing.BillingManager billingManager) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ads.AdManager provideAdManager(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
}