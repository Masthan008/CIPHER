package com.cipher.media.cloud;

import com.cipher.media.data.local.VaultFolderEntity;
import com.cipher.media.data.local.VaultItemEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0086@\u00a2\u0006\u0002\u0010\u0015J\u001e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u001aR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\u001b"}, d2 = {"Lcom/cipher/media/cloud/CloudMetadataRepository;", "", "()V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "userId", "", "getUserId", "()Ljava/lang/String;", "getSyncedStorageSize", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "removeVaultItem", "", "itemId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncVaultFolder", "folder", "Lcom/cipher/media/data/local/VaultFolderEntity;", "(Lcom/cipher/media/data/local/VaultFolderEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncVaultItem", "item", "Lcom/cipher/media/data/local/VaultItemEntity;", "cloudinaryPublicId", "(Lcom/cipher/media/data/local/VaultItemEntity;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class CloudMetadataRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    
    @javax.inject.Inject()
    public CloudMetadataRepository() {
        super();
    }
    
    private final java.lang.String getUserId() {
        return null;
    }
    
    /**
     * Syncs a local VaultItem metadata to Firestore, attaching the Cloudinary publicId.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncVaultItem(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.VaultItemEntity item, @org.jetbrains.annotations.NotNull()
    java.lang.String cloudinaryPublicId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Removes an item's metadata from Firestore.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object removeVaultItem(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Syncs a Vault Folder structure to Firestore.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object syncVaultFolder(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.VaultFolderEntity folder, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Calculates total bytes synced in Firestore by summing the `size` field of all synced items.
     * This is used to enforce tier quotas before attempting a large upload.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getSyncedStorageSize(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
}