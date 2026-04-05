package com.cipher.media.ui.settings;

import android.content.Intent;
import android.widget.Toast;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import com.cipher.media.R;
import com.cipher.media.ui.settings.theme.model.AccentColor;
import com.cipher.media.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000`\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0005\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001aT\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00032\u0018\u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\b0\u00072\u0006\u0010\t\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\rH\u0003\u001aL\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0003\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0017\u0010\u0018\u001a4\u0010\u0019\u001a\u00020\u00012\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\r2\b\b\u0002\u0010\u001c\u001a\u00020\u001dH\u0007\u001a.\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00032\u001c\u0010\u001f\u001a\u0018\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00010\r\u00a2\u0006\u0002\b!\u00a2\u0006\u0002\b\"H\u0003\u001aH\u0010#\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u00032\b\u0010$\u001a\u0004\u0018\u00010\u00032\u0006\u0010%\u001a\u00020\u00152\b\b\u0002\u0010&\u001a\u00020\u00152\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\rH\u0003\u001aR\u0010(\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00032\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020*2\u0006\u0010-\u001a\u00020\u00032\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000b2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u00010\rH\u0003\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006/"}, d2 = {"ProLabel", "", "text", "", "RadioDialog", "title", "options", "", "Lkotlin/Pair;", "selectedValue", "onDismiss", "Lkotlin/Function0;", "onSelect", "Lkotlin/Function1;", "SettingsItem", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "value", "tint", "Landroidx/compose/ui/graphics/Color;", "locked", "", "onClick", "SettingsItem-Bx497Mc", "(Landroidx/compose/ui/graphics/vector/ImageVector;Ljava/lang/String;Ljava/lang/String;JZLkotlin/jvm/functions/Function0;)V", "SettingsScreen", "onBack", "onNavigateTo", "viewModel", "Lcom/cipher/media/ui/settings/SettingsViewModel;", "SettingsSection", "content", "Landroidx/compose/foundation/layout/ColumnScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "SettingsToggle", "subtitle", "checked", "enabled", "onCheckedChange", "SliderDialog", "currentValue", "", "min", "max", "suffix", "onConfirm", "app_debug"})
public final class SettingsScreenKt {
    
    /**
     * Complete settings screen with all 25 features, tier gating, and bug fixes.
     *
     * BUG FIXES:
     * 1. Language uses BCP-47 codes + AppCompatDelegate (not old Locale API)
     * 2. All prefs saved via SettingsRepository (guaranteed .apply())
     * 3. UI observes StateFlow — always reflects current values
     * 4. All strings use stringResource to support dynamic locale changes!
     */
    @androidx.compose.runtime.Composable()
    public static final void SettingsScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateTo, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.settings.SettingsViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingsSection(java.lang.String title, kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.ColumnScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingsToggle(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String title, java.lang.String subtitle, boolean checked, boolean enabled, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ProLabel(java.lang.String text) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void RadioDialog(java.lang.String title, java.util.List<kotlin.Pair<java.lang.String, java.lang.String>> options, java.lang.String selectedValue, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSelect) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SliderDialog(java.lang.String title, int currentValue, int min, int max, java.lang.String suffix, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onConfirm) {
    }
}