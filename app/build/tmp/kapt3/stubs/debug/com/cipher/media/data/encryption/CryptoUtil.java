package com.cipher.media.data.encryption;

import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * AES-256-GCM encryption/decryption utilities.
 * IV (12 bytes) is prepended to the ciphertext.
 * No unencrypted temp files are created.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0007\b\u0007\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\fJ\u0016\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u001e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0016\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\f\u00a8\u0006\u0013"}, d2 = {"Lcom/cipher/media/data/encryption/CryptoUtil;", "", "()V", "decrypt", "", "key", "Ljavax/crypto/SecretKey;", "inputStream", "Ljava/io/InputStream;", "outputStream", "Ljava/io/OutputStream;", "decryptBytes", "", "data", "decryptStream", "encrypt", "encryptBytes", "plaintext", "Companion", "app_debug"})
public final class CryptoUtil {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.data.encryption.CryptoUtil.Companion Companion = null;
    
    @javax.inject.Inject()
    public CryptoUtil() {
        super();
    }
    
    /**
     * Encrypts data from [inputStream] and writes to [outputStream].
     * Format: [12-byte IV][ciphertext+tag]
     */
    public final void encrypt(@org.jetbrains.annotations.NotNull()
    javax.crypto.SecretKey key, @org.jetbrains.annotations.NotNull()
    java.io.InputStream inputStream, @org.jetbrains.annotations.NotNull()
    java.io.OutputStream outputStream) {
    }
    
    /**
     * Decrypts data from [inputStream] (which starts with 12-byte IV)
     * and writes plaintext to [outputStream].
     */
    public final void decrypt(@org.jetbrains.annotations.NotNull()
    javax.crypto.SecretKey key, @org.jetbrains.annotations.NotNull()
    java.io.InputStream inputStream, @org.jetbrains.annotations.NotNull()
    java.io.OutputStream outputStream) {
    }
    
    /**
     * Returns a decrypting InputStream (for on-the-fly decryption).
     * Caller is responsible for closing the stream.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.io.InputStream decryptStream(@org.jetbrains.annotations.NotNull()
    javax.crypto.SecretKey key, @org.jetbrains.annotations.NotNull()
    java.io.InputStream inputStream) {
        return null;
    }
    
    /**
     * Encrypts a ByteArray and returns [IV + ciphertext].
     * Useful for encrypting small metadata (filenames, etc.)
     */
    @org.jetbrains.annotations.NotNull()
    public final byte[] encryptBytes(@org.jetbrains.annotations.NotNull()
    javax.crypto.SecretKey key, @org.jetbrains.annotations.NotNull()
    byte[] plaintext) {
        return null;
    }
    
    /**
     * Decrypts a ByteArray that starts with IV.
     */
    @org.jetbrains.annotations.NotNull()
    public final byte[] decryptBytes(@org.jetbrains.annotations.NotNull()
    javax.crypto.SecretKey key, @org.jetbrains.annotations.NotNull()
    byte[] data) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/cipher/media/data/encryption/CryptoUtil$Companion;", "", "()V", "GCM_IV_LENGTH", "", "GCM_TAG_LENGTH", "TRANSFORMATION", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}