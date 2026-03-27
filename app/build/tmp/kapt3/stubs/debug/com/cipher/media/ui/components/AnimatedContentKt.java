package com.cipher.media.ui.components;

import androidx.compose.animation.*;
import androidx.compose.animation.core.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u001a!\u0010\u0011\u001a\u00020\u00122\u0017\u0010\u0013\u001a\u0013\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00120\u0014\u00a2\u0006\u0002\b\u0016H\u0007\u001a!\u0010\u0017\u001a\u00020\u00122\u0017\u0010\u0013\u001a\u0013\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00120\u0014\u00a2\u0006\u0002\b\u0016H\u0007\u001a)\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u001a2\u0017\u0010\u0013\u001a\u0013\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00120\u0014\u00a2\u0006\u0002\b\u0016H\u0007\u001a#\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u001d2\u0011\u0010\u0013\u001a\r\u0012\u0004\u0012\u00020\u00120\u001e\u00a2\u0006\u0002\b\u0016H\u0007\"\u0011\u0010\u0000\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0011\u0010\b\u001a\u00020\u0001\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0003\"\u0011\u0010\n\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007\"\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001f"}, d2 = {"NavEnterTransition", "Landroidx/compose/animation/EnterTransition;", "getNavEnterTransition", "()Landroidx/compose/animation/EnterTransition;", "NavExitTransition", "Landroidx/compose/animation/ExitTransition;", "getNavExitTransition", "()Landroidx/compose/animation/ExitTransition;", "NavPopEnterTransition", "getNavPopEnterTransition", "NavPopExitTransition", "getNavPopExitTransition", "TabCrossfade", "Landroidx/compose/animation/core/FiniteAnimationSpec;", "", "getTabCrossfade", "()Landroidx/compose/animation/core/FiniteAnimationSpec;", "BreathingEffect", "", "content", "Lkotlin/Function1;", "Landroidx/compose/ui/Modifier;", "Landroidx/compose/runtime/Composable;", "PulseGlow", "ScaleOnPress", "isPressed", "", "StaggeredEntrance", "index", "", "Lkotlin/Function0;", "app_debug"})
public final class AnimatedContentKt {
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.animation.EnterTransition NavEnterTransition = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.animation.ExitTransition NavExitTransition = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.animation.EnterTransition NavPopEnterTransition = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.animation.ExitTransition NavPopExitTransition = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.compose.animation.core.FiniteAnimationSpec<java.lang.Float> TabCrossfade = null;
    
    /**
     * Items fade in + slide up with 50ms stagger per index.
     */
    @androidx.compose.runtime.Composable()
    public static final void StaggeredEntrance(int index, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    /**
     * Scale bounce: 1.0 → 0.95 → 1.0 on press.
     */
    @androidx.compose.runtime.Composable()
    public static final void ScaleOnPress(boolean isPressed, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super androidx.compose.ui.Modifier, kotlin.Unit> content) {
    }
    
    /**
     * Subtle breathing pulse: scale 1.0 → 1.02, 4s infinite loop.
     */
    @androidx.compose.runtime.Composable()
    public static final void BreathingEffect(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super androidx.compose.ui.Modifier, kotlin.Unit> content) {
    }
    
    /**
     * Pulsing alpha glow: 0.5 → 1.0, 2s loop.
     */
    @androidx.compose.runtime.Composable()
    public static final void PulseGlow(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super androidx.compose.ui.Modifier, kotlin.Unit> content) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.animation.EnterTransition getNavEnterTransition() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.animation.ExitTransition getNavExitTransition() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.animation.EnterTransition getNavPopEnterTransition() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.animation.ExitTransition getNavPopExitTransition() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.animation.core.FiniteAnimationSpec<java.lang.Float> getTabCrossfade() {
        return null;
    }
}