package com.cipher.media.ui.vault;

import android.content.Context;
import android.net.Uri;
import android.provider.OpenableColumns;
import androidx.lifecycle.ViewModel;
import com.cipher.media.data.encryption.VaultEncryptionManager;
import com.cipher.media.data.local.VaultDao;
import com.cipher.media.data.local.VaultFolderEntity;
import com.cipher.media.data.local.VaultItemEntity;
import com.cipher.media.data.model.VaultFolder;
import com.cipher.media.data.model.VaultItem;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.*;
import java.util.UUID;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u001f\u001a\u00020 J\u000e\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020\rJ\u000e\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u0011J\u000e\u0010&\u001a\u00020\'2\u0006\u0010%\u001a\u00020\u0011J\u000e\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020\rJ\u000e\u0010*\u001a\u00020 2\u0006\u0010%\u001a\u00020\u0011J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020\rH\u0002J\u0012\u0010.\u001a\u0004\u0018\u00010\r2\u0006\u0010/\u001a\u000200H\u0002J\u0010\u00101\u001a\u0002022\u0006\u0010/\u001a\u000200H\u0002J\u0018\u00103\u001a\u00020 2\u0006\u0010/\u001a\u0002002\b\b\u0002\u00104\u001a\u00020\u000bJ\u0010\u00105\u001a\u00020 2\b\u0010)\u001a\u0004\u0018\u00010\rJ\u0006\u00106\u001a\u00020 J\f\u00107\u001a\u00020\u0011*\u000208H\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00100\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R\u0019\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0013\u00a8\u00069"}, d2 = {"Lcom/cipher/media/ui/vault/VaultViewModel;", "Landroidx/lifecycle/ViewModel;", "vaultDao", "Lcom/cipher/media/data/local/VaultDao;", "encryptionManager", "Lcom/cipher/media/data/encryption/VaultEncryptionManager;", "context", "Landroid/content/Context;", "(Lcom/cipher/media/data/local/VaultDao;Lcom/cipher/media/data/encryption/VaultEncryptionManager;Landroid/content/Context;)V", "_isImporting", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_selectedFolderId", "", "filteredItems", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/cipher/media/data/model/VaultItem;", "getFilteredItems", "()Lkotlinx/coroutines/flow/StateFlow;", "folders", "Lcom/cipher/media/data/model/VaultFolder;", "getFolders", "isImporting", "itemCount", "", "getItemCount", "selectedFolderId", "getSelectedFolderId", "vaultItems", "getVaultItems", "cleanupTemp", "", "createFolder", "name", "decryptToBytes", "", "item", "decryptToTempFile", "Ljava/io/File;", "deleteFolder", "folderId", "deleteItem", "detectFileType", "Lcom/cipher/media/data/model/VaultItem$FileType;", "fileName", "getFileName", "uri", "Landroid/net/Uri;", "getFileSize", "", "importFile", "deleteOriginal", "selectFolder", "wipeAllVaultData", "toDomain", "Lcom/cipher/media/data/local/VaultItemEntity;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class VaultViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.data.local.VaultDao vaultDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.data.encryption.VaultEncryptionManager encryptionManager = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cipher.media.data.model.VaultItem>> vaultItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cipher.media.data.model.VaultFolder>> folders = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> itemCount = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _selectedFolderId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> selectedFolderId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cipher.media.data.model.VaultItem>> filteredItems = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isImporting = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isImporting = null;
    
    @javax.inject.Inject()
    public VaultViewModel(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.local.VaultDao vaultDao, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.encryption.VaultEncryptionManager encryptionManager, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cipher.media.data.model.VaultItem>> getVaultItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cipher.media.data.model.VaultFolder>> getFolders() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getItemCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSelectedFolderId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.cipher.media.data.model.VaultItem>> getFilteredItems() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isImporting() {
        return null;
    }
    
    public final void selectFolder(@org.jetbrains.annotations.Nullable()
    java.lang.String folderId) {
    }
    
    public final void importFile(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri, boolean deleteOriginal) {
    }
    
    public final void deleteItem(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.VaultItem item) {
    }
    
    public final void createFolder(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void deleteFolder(@org.jetbrains.annotations.NotNull()
    java.lang.String folderId) {
    }
    
    /**
     * Self-destruct: wipes all vault data (files + database entries).
     */
    public final void wipeAllVaultData() {
    }
    
    /**
     * Decrypts a vault item to bytes (for images).
     */
    @org.jetbrains.annotations.NotNull()
    public final byte[] decryptToBytes(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.VaultItem item) {
        return null;
    }
    
    /**
     * Decrypts a vault item to a temp file (for video playback).
     */
    @org.jetbrains.annotations.NotNull()
    public final java.io.File decryptToTempFile(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.VaultItem item) {
        return null;
    }
    
    public final void cleanupTemp() {
    }
    
    private final java.lang.String getFileName(android.net.Uri uri) {
        return null;
    }
    
    private final long getFileSize(android.net.Uri uri) {
        return 0L;
    }
    
    private final com.cipher.media.data.model.VaultItem.FileType detectFileType(java.lang.String fileName) {
        return null;
    }
    
    private final com.cipher.media.data.model.VaultItem toDomain(com.cipher.media.data.local.VaultItemEntity $this$toDomain) {
        return null;
    }
}