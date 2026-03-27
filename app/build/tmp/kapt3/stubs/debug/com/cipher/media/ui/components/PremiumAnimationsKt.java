package com.cipher.media.ui.components;

import androidx.compose.animation.*;
import androidx.compose.animation.core.*;
import androidx.compose.foundation.interaction.PressInteraction;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.*;
import androidx.compose.ui.graphics.*;
import androidx.compose.ui.unit.Dp;
import com.cipher.media.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u000b\u001a)\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\u0002\b\u0006H\u0007\u001a#\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00032\u0011\u0010\u0004\u001a\r\u0012\u0004\u0012\u00020\u00010\t\u00a2\u0006\u0002\b\u0006H\u0007\u001a9\u0010\n\u001a\u00020\u00012\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\u0011\u0010\u0004\u001a\r\u0012\u0004\u0012\u00020\u00010\t\u00a2\u0006\u0002\b\u0006H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u000f\u0010\u0010\u001a3\u0010\u0011\u001a\u00020\u00012\b\b\u0002\u0010\u000b\u001a\u00020\f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\u0011\u0010\u0004\u001a\r\u0012\u0004\u0012\u00020\u00010\t\u00a2\u0006\u0002\b\u0006H\u0007\u001a5\u0010\u0013\u001a\u00020\u00012\b\b\u0002\u0010\u0014\u001a\u00020\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00162\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\u0002\b\u0006H\u0007\u001aC\u0010\u0017\u001a\u00020\u00012\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u0018\u001a\u00020\u000e2\u0011\u0010\u0004\u001a\r\u0012\u0004\u0012\u00020\u00010\t\u00a2\u0006\u0002\b\u0006H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0019\u0010\u001a\u001a;\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\u001d\u001a\u00020\u00162\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\u0002\b\u0006H\u0007\u001a/\u0010\u001e\u001a\u00020\u00012\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u001f\u001a\u00020\u00032\u0011\u0010\u0004\u001a\r\u0012\u0004\u0012\u00020\u00010\t\u00a2\u0006\u0002\b\u0006H\u0007\u001a/\u0010 \u001a\u00020\u00012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\u0002\b\u0006H\u0007\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006!"}, d2 = {"AnimatedCounterSlide", "", "targetValue", "", "content", "Lkotlin/Function1;", "Landroidx/compose/runtime/Composable;", "FloatingEntrance", "index", "Lkotlin/Function0;", "GlassmorphicCard", "modifier", "Landroidx/compose/ui/Modifier;", "cornerRadius", "Landroidx/compose/ui/unit/Dp;", "GlassmorphicCard-lG28NQ4", "(Landroidx/compose/ui/Modifier;FLkotlin/jvm/functions/Function0;)V", "GlowRippleContainer", "onClick", "IdleFloat", "speed", "amplitude", "", "NeonGlowBorder", "glowRadius", "NeonGlowBorder-ghNngFA", "(Landroidx/compose/ui/Modifier;FFLkotlin/jvm/functions/Function0;)V", "ParallaxOffset", "scrollProgress", "depth", "ParticleShimmerOverlay", "particleCount", "SpringPressEffect", "app_debug"})
public final class PremiumAnimationsKt {
    
    /**
     * A spring-based press effect that gives cards and buttons a natural, bouncy feel.
     * Uses physics-based spring animation rather than linear tween.
     */
    @androidx.compose.runtime.Composable()
    public static final void SpringPressEffect(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super androidx.compose.ui.Modifier, kotlin.Unit> content) {
    }
    
    /**
     * Gives items a subtle parallax offset based on their scroll position index.
     * Creates a layered depth effect in lists and grids.
     */
    @androidx.compose.runtime.Composable()
    public static final void ParallaxOffset(float scrollProgress, int index, float depth, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super androidx.compose.ui.Modifier, kotlin.Unit> content) {
    }
    
    /**
     * Radial glow ripple effect on tap. Creates a circle of light that expands
     * from the touch point and fades out, like a premium touch response.
     */
    @androidx.compose.runtime.Composable()
    public static final void GlowRippleContainer(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    /**
     * Items float up from below with a gentle rotation and spring physics.
     * More dramatic than StaggeredEntrance — ideal for hero cards and feature screens.
     */
    @androidx.compose.runtime.Composable()
    public static final void FloatingEntrance(int index, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    /**
     * A gentle floating idle animation that makes elements feel alive.
     * Combines slow Y oscillation with subtle scale breathing.
     */
    @androidx.compose.runtime.Composable()
    public static final void IdleFloat(int speed, float amplitude, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super androidx.compose.ui.Modifier, kotlin.Unit> content) {
    }
    
    /**
     * Smoothly animates number changes by sliding old value up/out and new value up/in.
     * Great for dynamic counters (play counts, file counts, timer, etc.).
     */
    @androidx.compose.runtime.Composable()
    public static final void AnimatedCounterSlide(int targetValue, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> content) {
    }
    
    /**
     * Draws tiny animated particles that slowly drift across a surface,
     * giving a "magic dust" effect to premium cards and hero sections.
     */
    @androidx.compose.runtime.Composable()
    public static final void ParticleShimmerOverlay(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, int particleCount, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
}