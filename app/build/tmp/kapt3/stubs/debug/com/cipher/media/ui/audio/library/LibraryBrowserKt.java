package com.cipher.media.ui.audio.library;

import androidx.compose.foundation.ExperimentalFoundationApi;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.cipher.media.data.model.AudioItem;
import com.cipher.media.data.model.audiofx.Tier;
import com.cipher.media.data.model.library.LibraryTab;
import com.cipher.media.data.model.library.SortOption;
import com.cipher.media.ui.audio.library.components.*;
import com.cipher.media.ui.components.*;
import com.cipher.media.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aR\u0010\u0000\u001a\u00020\u00012\u001e\u0010\u0002\u001a\u001a\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0005\u0012\u0004\u0012\u00020\u00010\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00072\b\b\u0002\u0010\t\u001a\u00020\nH\u0007\u001ap\u0010\u000b\u001a\u00020\u00012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00052\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0012\u001a\u00020\u000e2\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u00142\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0014H\u0003\u00a8\u0006\u0017"}, d2 = {"LibraryBrowser", "", "onAudioClick", "Lkotlin/Function2;", "Lcom/cipher/media/data/model/AudioItem;", "", "onSearchClick", "Lkotlin/Function0;", "onBack", "viewModel", "Lcom/cipher/media/ui/audio/library/LibraryViewModel;", "SongsList", "songs", "isLoading", "", "selectedIds", "", "", "isSelectionMode", "onSongClick", "Lkotlin/Function1;", "onSongLongClick", "onSongInfoClick", "app_debug"})
public final class LibraryBrowserKt {
    
    /**
     * Full Library Browser with tabbed navigation:
     * Songs | Albums | Artists | Genres | Folders | Playlists
     * Supports multi-select (long press), batch editing, tag editing, and search.
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    public static final void LibraryBrowser(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.cipher.media.data.model.AudioItem, ? super java.util.List<com.cipher.media.data.model.AudioItem>, kotlin.Unit> onAudioClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSearchClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.audio.library.LibraryViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    private static final void SongsList(java.util.List<com.cipher.media.data.model.AudioItem> songs, boolean isLoading, java.util.Set<java.lang.Long> selectedIds, boolean isSelectionMode, kotlin.jvm.functions.Function1<? super com.cipher.media.data.model.AudioItem, kotlin.Unit> onSongClick, kotlin.jvm.functions.Function1<? super java.lang.Long, kotlin.Unit> onSongLongClick, kotlin.jvm.functions.Function1<? super com.cipher.media.data.model.AudioItem, kotlin.Unit> onSongInfoClick) {
    }
}