package com.cipher.media.cloud;

import android.content.Context;
import android.net.Uri;
import com.cipher.media.BuildConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import kotlinx.coroutines.Dispatchers;
import java.io.File;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u000e\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\fJ \u0010\u000f\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\u0013R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0014"}, d2 = {"Lcom/cipher/media/cloud/CloudinaryManager;", "", "()V", "cloudinary", "Lcom/cloudinary/Cloudinary;", "getCloudinary", "()Lcom/cloudinary/Cloudinary;", "cloudinary$delegate", "Lkotlin/Lazy;", "deleteBlob", "", "publicId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDownloadUrl", "uploadEncryptedBlob", "file", "Ljava/io/File;", "userId", "(Ljava/io/File;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class CloudinaryManager {
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy cloudinary$delegate = null;
    
    @javax.inject.Inject()
    public CloudinaryManager() {
        super();
    }
    
    private final com.cloudinary.Cloudinary getCloudinary() {
        return null;
    }
    
    /**
     * Uploads an encrypted vault file directly to Cloudinary.
     * @param file The local encrypted file.
     * @param userId The unique ID of the user (e.g., Firebase UID) to namespace the uploads.
     * @return The public_id of the uploaded resource, or null if failed.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object uploadEncryptedBlob(@org.jetbrains.annotations.NotNull()
    java.io.File file, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    /**
     * Deletes a blob from Cloudinary.
     * @param publicId The Cloudinary public_id of the resource.
     * @return True if deleted successfully.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteBlob(@org.jetbrains.annotations.NotNull()
    java.lang.String publicId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Generates a signed download URL for the encrypted blob.
     * Use this URL to download the file using an HTTP client (e.g. OkHttp) to restore it.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDownloadUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String publicId) {
        return null;
    }
}