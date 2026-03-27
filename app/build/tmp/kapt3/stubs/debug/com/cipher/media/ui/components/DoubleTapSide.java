package com.cipher.media.ui.components;

import android.content.Context;
import android.media.AudioManager;
import android.view.Window;
import androidx.compose.animation.*;
import androidx.compose.animation.core.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.input.pointer.PointerEventType;
import androidx.compose.ui.text.font.FontWeight;
import kotlinx.coroutines.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/cipher/media/ui/components/DoubleTapSide;", "", "(Ljava/lang/String;I)V", "LEFT", "CENTER", "RIGHT", "app_debug"})
enum DoubleTapSide {
    /*public static final*/ LEFT /* = new LEFT() */,
    /*public static final*/ CENTER /* = new CENTER() */,
    /*public static final*/ RIGHT /* = new RIGHT() */;
    
    DoubleTapSide() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.cipher.media.ui.components.DoubleTapSide> getEntries() {
        return null;
    }
}