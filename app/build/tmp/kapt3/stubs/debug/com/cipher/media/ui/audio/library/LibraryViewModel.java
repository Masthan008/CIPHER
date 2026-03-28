package com.cipher.media.ui.audio.library;

import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.cipher.media.data.model.AudioItem;
import com.cipher.media.data.model.audiofx.Tier;
import com.cipher.media.data.model.library.LibraryTab;
import com.cipher.media.data.model.library.MusicTag;
import com.cipher.media.data.model.library.SortOption;
import com.cipher.media.data.repository.MediaRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012J\u0006\u0010\u0014\u001a\u00020\u000fJ\u0006\u0010\u0015\u001a\u00020\u000fJ\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u001a\u001a\u00020\u0012J\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u001c\u001a\u00020\u0012J\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00180\u00172\u0006\u0010\u001e\u001a\u00020\u0012J\u0006\u0010\u001f\u001a\u00020\u000fJ\u000e\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\u0018J\u000e\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020$J\u0006\u0010%\u001a\u00020\u000fJ\u000e\u0010&\u001a\u00020\u000f2\u0006\u0010\'\u001a\u00020\u0012J\u000e\u0010(\u001a\u00020\u000f2\u0006\u0010)\u001a\u00020*J\u000e\u0010+\u001a\u00020\u000f2\u0006\u0010,\u001a\u00020-J\u000e\u0010.\u001a\u00020\u000f2\u0006\u0010/\u001a\u000200R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00061"}, d2 = {"Lcom/cipher/media/ui/audio/library/LibraryViewModel;", "Landroidx/lifecycle/ViewModel;", "mediaRepository", "Lcom/cipher/media/data/repository/MediaRepository;", "tagEditor", "Lcom/cipher/media/ui/audio/library/TagEditorManager;", "(Lcom/cipher/media/data/repository/MediaRepository;Lcom/cipher/media/ui/audio/library/TagEditorManager;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/cipher/media/ui/audio/library/LibraryUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "batchAutoNumber", "", "batchSetField", "field", "", "value", "clearSelection", "dismissTagEditor", "getFilteredSongs", "", "Lcom/cipher/media/data/model/AudioItem;", "getSongsForAlbum", "albumName", "getSongsForArtist", "artistName", "getSongsInFolder", "folder", "loadAll", "loadTagForEditing", "audio", "saveTags", "tag", "Lcom/cipher/media/data/model/library/MusicTag;", "selectAll", "setSearch", "query", "setSort", "option", "Lcom/cipher/media/data/model/library/SortOption;", "setTab", "tab", "Lcom/cipher/media/data/model/library/LibraryTab;", "toggleSelection", "id", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class LibraryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.data.repository.MediaRepository mediaRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.ui.audio.library.TagEditorManager tagEditor = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.cipher.media.ui.audio.library.LibraryUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.audio.library.LibraryUiState> state = null;
    
    @javax.inject.Inject()
    public LibraryViewModel(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.repository.MediaRepository mediaRepository, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.library.TagEditorManager tagEditor) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.cipher.media.ui.audio.library.LibraryUiState> getState() {
        return null;
    }
    
    public final void loadAll() {
    }
    
    public final void setTab(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.library.LibraryTab tab) {
    }
    
    public final void setSort(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.library.SortOption option) {
    }
    
    public final void setSearch(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.data.model.AudioItem> getFilteredSongs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.data.model.AudioItem> getSongsInFolder(@org.jetbrains.annotations.NotNull()
    java.lang.String folder) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.data.model.AudioItem> getSongsForAlbum(@org.jetbrains.annotations.NotNull()
    java.lang.String albumName) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.data.model.AudioItem> getSongsForArtist(@org.jetbrains.annotations.NotNull()
    java.lang.String artistName) {
        return null;
    }
    
    public final void toggleSelection(long id) {
    }
    
    public final void clearSelection() {
    }
    
    public final void selectAll() {
    }
    
    public final void loadTagForEditing(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.AudioItem audio) {
    }
    
    public final void saveTags(@org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.library.MusicTag tag) {
    }
    
    public final void dismissTagEditor() {
    }
    
    public final void batchSetField(@org.jetbrains.annotations.NotNull()
    java.lang.String field, @org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void batchAutoNumber() {
    }
}