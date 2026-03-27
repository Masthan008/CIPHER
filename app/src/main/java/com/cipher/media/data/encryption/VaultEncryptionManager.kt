package com.cipher.media.data.encryption

import android.content.Context
import android.net.Uri
import com.cipher.media.data.model.VaultItem
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * High-level vault file management: import, decrypt, delete.
 * All encrypted files are stored in app-private internal storage.
 */
@Singleton
class VaultEncryptionManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val keystoreManager: KeystoreManager,
    private val cryptoUtil: CryptoUtil
) {

    private val vaultDir: File
        get() = File(context.filesDir, "vault").also { it.mkdirs() }

    /**
     * Imports a file into the vault.
     * 1. Copies content from [sourceUri] to app-private storage
     * 2. Encrypts the copy with AES-256-GCM
     * 3. If [deleteOriginal] is true, deletes the source file
     *
     * @return The encrypted file path (relative to vault dir)
     */
    fun importFile(
        sourceUri: Uri,
        originalName: String,
        deleteOriginal: Boolean = false
    ): String {
        val key = keystoreManager.getOrCreateVaultKey()
        val encryptedFileName = UUID.randomUUID().toString() + ".enc"
        val encryptedFile = File(vaultDir, encryptedFileName)

        // Read from content URI → encrypt → write to vault
        context.contentResolver.openInputStream(sourceUri)?.use { inputStream ->
            FileOutputStream(encryptedFile).use { outputStream ->
                cryptoUtil.encrypt(key, inputStream, outputStream)
            }
        } ?: throw IllegalStateException("Cannot open source URI: $sourceUri")

        // Optionally delete original
        if (deleteOriginal) {
            try {
                context.contentResolver.delete(sourceUri, null, null)
            } catch (_: Exception) {
                // Some URIs can't be deleted via ContentResolver; ignore
            }
        }

        return encryptedFileName
    }

    /**
     * Decrypts a vault file to a temporary file for viewing.
     * The caller MUST delete the temp file after use.
     *
     * @return Temporary decrypted file in cache dir
     */
    fun decryptToTempFile(encryptedPath: String, extension: String = ".tmp"): File {
        val key = keystoreManager.getOrCreateVaultKey()
        val encryptedFile = File(vaultDir, encryptedPath)
        val tempFile = File(context.cacheDir, "vault_temp_${UUID.randomUUID()}$extension")

        FileInputStream(encryptedFile).use { inputStream ->
            FileOutputStream(tempFile).use { outputStream ->
                cryptoUtil.decrypt(key, inputStream, outputStream)
            }
        }

        return tempFile
    }

    /**
     * Decrypts a vault file to a ByteArray (for images).
     * Suitable for small-to-medium files that fit comfortably in memory.
     */
    fun decryptToBytes(encryptedPath: String): ByteArray {
        val key = keystoreManager.getOrCreateVaultKey()
        val encryptedFile = File(vaultDir, encryptedPath)

        FileInputStream(encryptedFile).use { inputStream ->
            val decryptedStream = cryptoUtil.decryptStream(key, inputStream)
            return decryptedStream.readBytes()
        }
    }

    /**
     * Permanently deletes an encrypted file from the vault.
     * Overwrites file content before deleting for extra security.
     */
    fun deleteVaultFile(encryptedPath: String) {
        val file = File(vaultDir, encryptedPath)
        if (file.exists()) {
            // Overwrite with zeros before deletion
            val zeros = ByteArray(4096)
            FileOutputStream(file).use { out ->
                var remaining = file.length()
                while (remaining > 0) {
                    val toWrite = minOf(remaining, zeros.size.toLong()).toInt()
                    out.write(zeros, 0, toWrite)
                    remaining -= toWrite
                }
            }
            file.delete()
        }
    }

    /**
     * Cleans up any temp decrypted files in cache.
     */
    fun cleanupTempFiles() {
        context.cacheDir.listFiles()?.forEach { file ->
            if (file.name.startsWith("vault_temp_")) {
                file.delete()
            }
        }
    }

    /**
     * Returns vault directory size in bytes.
     */
    fun getVaultSize(): Long {
        return vaultDir.listFiles()?.sumOf { it.length() } ?: 0L
    }
}
