package com.cipher.media.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PlayerModule {

    /**
     * Provides a factory-style ExoPlayer builder. We don't make it singleton because
     * each player session may have a different lifecycle.
     * ViewModels will build their own instances from this context.
     */
    @Provides
    fun provideExoPlayerBuilder(@ApplicationContext context: Context): ExoPlayer.Builder {
        return ExoPlayer.Builder(context)
    }
}
