package com.cipher.media.security

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Centralized manager for secure vault video playback lifecycle.
 *
 * Ensures:
 * - Temp decrypted files are deleted after playback
 * - Files are auto-deleted after MAX_LIFETIME even if player crashes
 * - All temp files are purged on app startup
 */
@Singleton
class SecurePlaybackManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val MAX_LIFETIME_MS = 2 * 60 * 60 * 1000L // 2 hours
        private const val TEMP_PREFIX = "vault_temp_"
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val activeTempFiles = mutableMapOf<String, Job>()

    /**
     * Registers a temp file for automatic cleanup.
     * The file will be deleted after [MAX_LIFETIME_MS] or when [releaseTempFile] is called.
     */
    fun registerTempFile(file: File): String {
        val key = file.absolutePath
        val job = scope.launch {
            delay(MAX_LIFETIME_MS)
            file.deleteSecurely()
            activeTempFiles.remove(key)
        }
        activeTempFiles[key] = job
        return key
    }

    /**
     * Immediately releases and deletes a specific temp file.
     */
    fun releaseTempFile(key: String) {
        activeTempFiles[key]?.cancel()
        activeTempFiles.remove(key)
        val file = File(key)
        file.deleteSecurely()
    }

    /**
     * Cleans up ALL temp vault files from cache directory.
     * Call on app startup and when vault is locked.
     */
    fun purgeAllTempFiles() {
        // Cancel all active timers
        activeTempFiles.values.forEach { it.cancel() }
        activeTempFiles.clear()

        // Delete all temp files from cache
        context.cacheDir.listFiles()?.forEach { file ->
            if (file.name.startsWith(TEMP_PREFIX)) {
                file.deleteSecurely()
            }
        }
    }

    /**
     * Securely deletes a file by overwriting with zeros before deletion.
     */
    private fun File.deleteSecurely() {
        if (!exists()) return
        try {
            val zeros = ByteArray(4096)
            outputStream().use { out ->
                var remaining = length()
                while (remaining > 0) {
                    val toWrite = minOf(remaining, zeros.size.toLong()).toInt()
                    out.write(zeros, 0, toWrite)
                    remaining -= toWrite
                }
            }
        } catch (_: Exception) { }
        delete()
    }
}
