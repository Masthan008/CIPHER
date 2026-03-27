package com.cipher.media.cloud

import android.content.Context
import android.net.Uri
import com.cipher.media.BuildConfig
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CloudinaryManager @Inject constructor() {

    private val cloudinary: Cloudinary by lazy {
        Cloudinary(
            ObjectUtils.asMap(
                "cloud_name", BuildConfig.CLOUDINARY_CLOUD_NAME,
                "api_key", BuildConfig.CLOUDINARY_API_KEY,
                "api_secret", BuildConfig.CLOUDINARY_API_SECRET
            )
        )
    }

    /**
     * Uploads an encrypted vault file directly to Cloudinary.
     * @param file The local encrypted file.
     * @param userId The unique ID of the user (e.g., Firebase UID) to namespace the uploads.
     * @return The public_id of the uploaded resource, or null if failed.
     */
    suspend fun uploadEncryptedBlob(file: File, userId: String): String? = withContext(Dispatchers.IO) {
        try {
            if (!file.exists()) return@withContext null
            
            val params = ObjectUtils.asMap(
                "folder", "cipher_vault/$userId",
                "resource_type", "raw" // Required for encrypted binary blobs
            )

            val uploadResult = cloudinary.uploader().upload(file, params)
            return@withContext uploadResult["public_id"] as? String
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Deletes a blob from Cloudinary.
     * @param publicId The Cloudinary public_id of the resource.
     * @return True if deleted successfully.
     */
    suspend fun deleteBlob(publicId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val result = cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "raw"))
            return@withContext result["result"] == "ok"
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Generates a signed download URL for the encrypted blob.
     * Use this URL to download the file using an HTTP client (e.g. OkHttp) to restore it.
     */
    fun getDownloadUrl(publicId: String): String {
        return cloudinary.url()
            .resourceType("raw")
            .generate(publicId)
    }
}
