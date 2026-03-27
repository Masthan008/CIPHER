package com.cipher.media.ui.search;

import android.content.ContentResolver;
import android.provider.MediaStore;
import androidx.lifecycle.ViewModel;
import com.cipher.media.data.model.VaultItem;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.FlowPreview;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/cipher/media/ui/search/SearchFilter;", "", "(Ljava/lang/String;I)V", "ALL", "VIDEO", "MUSIC", "VAULT", "app_debug"})
public enum SearchFilter {
    /*public static final*/ ALL /* = new ALL() */,
    /*public static final*/ VIDEO /* = new VIDEO() */,
    /*public static final*/ MUSIC /* = new MUSIC() */,
    /*public static final*/ VAULT /* = new VAULT() */;
    
    SearchFilter() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.cipher.media.ui.search.SearchFilter> getEntries() {
        return null;
    }
}