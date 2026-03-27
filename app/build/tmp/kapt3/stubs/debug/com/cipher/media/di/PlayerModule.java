package com.cipher.media.di;

import android.content.Context;
import androidx.media3.exoplayer.ExoPlayer;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, d2 = {"Lcom/cipher/media/di/PlayerModule;", "", "()V", "provideExoPlayerBuilder", "Landroidx/media3/exoplayer/ExoPlayer$Builder;", "context", "Landroid/content/Context;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class PlayerModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.di.PlayerModule INSTANCE = null;
    
    private PlayerModule() {
        super();
    }
    
    /**
     * Provides a factory-style ExoPlayer builder. We don't make it singleton because
     * each player session may have a different lifecycle.
     * ViewModels will build their own instances from this context.
     */
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final androidx.media3.exoplayer.ExoPlayer.Builder provideExoPlayerBuilder(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
}