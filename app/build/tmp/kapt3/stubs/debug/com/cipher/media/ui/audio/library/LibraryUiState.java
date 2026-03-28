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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\'\b\u0086\b\u0018\u00002\u00020\u0001B\u00d9\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\"\b\u0002\u0010\t\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\n0\u0007\u0012\u001a\b\u0002\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0007\u0012\u001a\b\u0002\u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0007\u0012\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0007\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u000b\u0012\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u001c\u00a2\u0006\u0002\u0010\u001dJ\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\u000f\u00102\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u00c6\u0003J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u001aH\u00c6\u0003J\t\u00105\u001a\u00020\u001cH\u00c6\u0003J\t\u00106\u001a\u00020\u0005H\u00c6\u0003J\u000f\u00107\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J#\u00108\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\n0\u0007H\u00c6\u0003J\u001b\u00109\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0007H\u00c6\u0003J\u001b\u0010:\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0007H\u00c6\u0003J\u000f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0007H\u00c6\u0003J\t\u0010<\u001a\u00020\u0013H\u00c6\u0003J\t\u0010=\u001a\u00020\u000bH\u00c6\u0003J\u00dd\u0001\u0010>\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\"\b\u0002\u0010\t\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\n0\u00072\u001a\b\u0002\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000f0\u000e0\u00072\u001a\b\u0002\u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000f0\u000e0\u00072\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00072\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u000b2\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00162\b\b\u0002\u0010\u0018\u001a\u00020\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u001cH\u00c6\u0001J\u0013\u0010?\u001a\u00020\u00032\b\u0010@\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010A\u001a\u00020\u000fH\u00d6\u0001J\t\u0010B\u001a\u00020\u000bH\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR+\u0010\t\u001a\u001c\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\n0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R#\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010!R\u0013\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010!R#\u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000f0\u000e0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010!R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\'R\u0011\u0010\u0018\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\'R\u0011\u0010\u0014\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010!R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0011\u0010\u001b\u001a\u00020\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u00100\u00a8\u0006C"}, d2 = {"Lcom/cipher/media/ui/audio/library/LibraryUiState;", "", "isLoading", "", "activeTab", "Lcom/cipher/media/data/model/library/LibraryTab;", "songs", "", "Lcom/cipher/media/data/model/AudioItem;", "albums", "Lkotlin/Triple;", "", "Landroid/net/Uri;", "artists", "Lkotlin/Pair;", "", "genres", "folders", "sortOption", "Lcom/cipher/media/data/model/library/SortOption;", "searchQuery", "selectedIds", "", "", "isSelectionMode", "editingTag", "Lcom/cipher/media/data/model/library/MusicTag;", "userTier", "Lcom/cipher/media/data/model/audiofx/Tier;", "(ZLcom/cipher/media/data/model/library/LibraryTab;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/cipher/media/data/model/library/SortOption;Ljava/lang/String;Ljava/util/Set;ZLcom/cipher/media/data/model/library/MusicTag;Lcom/cipher/media/data/model/audiofx/Tier;)V", "getActiveTab", "()Lcom/cipher/media/data/model/library/LibraryTab;", "getAlbums", "()Ljava/util/List;", "getArtists", "getEditingTag", "()Lcom/cipher/media/data/model/library/MusicTag;", "getFolders", "getGenres", "()Z", "getSearchQuery", "()Ljava/lang/String;", "getSelectedIds", "()Ljava/util/Set;", "getSongs", "getSortOption", "()Lcom/cipher/media/data/model/library/SortOption;", "getUserTier", "()Lcom/cipher/media/data/model/audiofx/Tier;", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class LibraryUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.data.model.library.LibraryTab activeTab = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.cipher.media.data.model.AudioItem> songs = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<kotlin.Triple<java.lang.String, java.lang.String, android.net.Uri>> albums = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<kotlin.Pair<java.lang.String, java.lang.Integer>> artists = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<kotlin.Pair<java.lang.String, java.lang.Integer>> genres = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> folders = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.data.model.library.SortOption sortOption = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.Long> selectedIds = null;
    private final boolean isSelectionMode = false;
    @org.jetbrains.annotations.Nullable()
    private final com.cipher.media.data.model.library.MusicTag editingTag = null;
    @org.jetbrains.annotations.NotNull()
    private final com.cipher.media.data.model.audiofx.Tier userTier = null;
    
    public LibraryUiState(boolean isLoading, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.library.LibraryTab activeTab, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.data.model.AudioItem> songs, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends kotlin.Triple<java.lang.String, java.lang.String, ? extends android.net.Uri>> albums, @org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<java.lang.String, java.lang.Integer>> artists, @org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<java.lang.String, java.lang.Integer>> genres, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> folders, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.library.SortOption sortOption, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.Long> selectedIds, boolean isSelectionMode, @org.jetbrains.annotations.Nullable()
    com.cipher.media.data.model.library.MusicTag editingTag, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.audiofx.Tier userTier) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.model.library.LibraryTab getActiveTab() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.data.model.AudioItem> getSongs() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Triple<java.lang.String, java.lang.String, android.net.Uri>> getAlbums() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<java.lang.String, java.lang.Integer>> getArtists() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<java.lang.String, java.lang.Integer>> getGenres() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getFolders() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.model.library.SortOption getSortOption() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.Long> getSelectedIds() {
        return null;
    }
    
    public final boolean isSelectionMode() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.cipher.media.data.model.library.MusicTag getEditingTag() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.model.audiofx.Tier getUserTier() {
        return null;
    }
    
    public LibraryUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.Long> component10() {
        return null;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.cipher.media.data.model.library.MusicTag component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.model.audiofx.Tier component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.model.library.LibraryTab component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.data.model.AudioItem> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Triple<java.lang.String, java.lang.String, android.net.Uri>> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<java.lang.String, java.lang.Integer>> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<kotlin.Pair<java.lang.String, java.lang.Integer>> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.model.library.SortOption component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.ui.audio.library.LibraryUiState copy(boolean isLoading, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.library.LibraryTab activeTab, @org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.data.model.AudioItem> songs, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends kotlin.Triple<java.lang.String, java.lang.String, ? extends android.net.Uri>> albums, @org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<java.lang.String, java.lang.Integer>> artists, @org.jetbrains.annotations.NotNull()
    java.util.List<kotlin.Pair<java.lang.String, java.lang.Integer>> genres, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> folders, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.library.SortOption sortOption, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.Long> selectedIds, boolean isSelectionMode, @org.jetbrains.annotations.Nullable()
    com.cipher.media.data.model.library.MusicTag editingTag, @org.jetbrains.annotations.NotNull()
    com.cipher.media.data.model.audiofx.Tier userTier) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}