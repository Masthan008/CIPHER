package com.cipher.media.data.encryption

import java.io.InputStream
import java.io.OutputStream
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

/**
 * AES-256-GCM encryption/decryption utilities.
 * IV (12 bytes) is prepended to the ciphertext.
 * No unencrypted temp files are created.
 */
@Singleton
class CryptoUtil @Inject constructor() {

    companion object {
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val GCM_IV_LENGTH = 12  // bytes
        private const val GCM_TAG_LENGTH = 128 // bits
    }

    /**
     * Encrypts data from [inputStream] and writes to [outputStream].
     * Format: [12-byte IV][ciphertext+tag]
     */
    fun encrypt(key: SecretKey, inputStream: InputStream, outputStream: OutputStream) {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key)

        val iv = cipher.iv // GCM auto-generates a random IV
        outputStream.write(iv) // Prepend IV

        CipherOutputStream(outputStream, cipher).use { cipherOut ->
            inputStream.copyTo(cipherOut, bufferSize = 8192)
        }
    }

    /**
     * Decrypts data from [inputStream] (which starts with 12-byte IV)
     * and writes plaintext to [outputStream].
     */
    fun decrypt(key: SecretKey, inputStream: InputStream, outputStream: OutputStream) {
        val iv = ByteArray(GCM_IV_LENGTH)
        inputStream.read(iv) // Read prepended IV

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(GCM_TAG_LENGTH, iv))

        CipherInputStream(inputStream, cipher).use { cipherIn ->
            cipherIn.copyTo(outputStream, bufferSize = 8192)
        }
    }

    /**
     * Returns a decrypting InputStream (for on-the-fly decryption).
     * Caller is responsible for closing the stream.
     */
    fun decryptStream(key: SecretKey, inputStream: InputStream): InputStream {
        val iv = ByteArray(GCM_IV_LENGTH)
        inputStream.read(iv)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(GCM_TAG_LENGTH, iv))

        return CipherInputStream(inputStream, cipher)
    }

    /**
     * Encrypts a ByteArray and returns [IV + ciphertext].
     * Useful for encrypting small metadata (filenames, etc.)
     */
    fun encryptBytes(key: SecretKey, plaintext: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val ciphertext = cipher.doFinal(plaintext)
        return cipher.iv + ciphertext
    }

    /**
     * Decrypts a ByteArray that starts with IV.
     */
    fun decryptBytes(key: SecretKey, data: ByteArray): ByteArray {
        val iv = data.copyOfRange(0, GCM_IV_LENGTH)
        val ciphertext = data.copyOfRange(GCM_IV_LENGTH, data.size)

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(GCM_TAG_LENGTH, iv))
        return cipher.doFinal(ciphertext)
    }
}
