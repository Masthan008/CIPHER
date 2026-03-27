package com.cipher.media.data.encryption

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages the AES-256-GCM master key stored in Android Keystore (hardware-backed).
 * The key never leaves the Keystore and is used for all vault encryption operations.
 */
@Singleton
class KeystoreManager @Inject constructor() {

    companion object {
        private const val KEYSTORE_PROVIDER = "AndroidKeyStore"
        private const val VAULT_KEY_ALIAS = "cipher_vault_master_key"
        private const val DB_KEY_ALIAS = "cipher_db_key"
    }

    private val keyStore: KeyStore = KeyStore.getInstance(KEYSTORE_PROVIDER).apply { load(null) }

    /**
     * Gets or creates the vault master key for file encryption.
     */
    fun getOrCreateVaultKey(): SecretKey {
        return getKey(VAULT_KEY_ALIAS) ?: createKey(VAULT_KEY_ALIAS)
    }

    /**
     * Gets or creates a key specifically for database passphrase derivation.
     */
    fun getOrCreateDbKey(): SecretKey {
        return getKey(DB_KEY_ALIAS) ?: createKey(DB_KEY_ALIAS)
    }

    /**
     * Checks if the vault has been set up (keys exist).
     */
    fun isVaultInitialized(): Boolean {
        return keyStore.containsAlias(VAULT_KEY_ALIAS)
    }

    /**
     * Wipes all vault keys (nuclear option for failed auth attempts).
     */
    fun wipeKeys() {
        if (keyStore.containsAlias(VAULT_KEY_ALIAS)) {
            keyStore.deleteEntry(VAULT_KEY_ALIAS)
        }
        if (keyStore.containsAlias(DB_KEY_ALIAS)) {
            keyStore.deleteEntry(DB_KEY_ALIAS)
        }
    }

    private fun getKey(alias: String): SecretKey? {
        return if (keyStore.containsAlias(alias)) {
            (keyStore.getEntry(alias, null) as? KeyStore.SecretKeyEntry)?.secretKey
        } else null
    }

    private fun createKey(alias: String): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            KEYSTORE_PROVIDER
        )

        val spec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .setRandomizedEncryptionRequired(true)
            .build()

        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }
}
