package com.cipher.media.ui.video;

import android.app.Activity;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.util.Rational;
import android.view.WindowManager;
import androidx.compose.animation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.ui.AspectRatioFrameLayout;
import androidx.media3.ui.PlayerView;
import com.cipher.media.ui.video.audio.VideoEqualizerViewModel;
import com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel;
import com.cipher.media.ui.video.enhancement.ScreenshotManager;
import com.cipher.media.ui.video.enhancement.components.*;
import com.cipher.media.ui.video.subtitle.SubtitleViewModel;
import com.cipher.media.util.TimeUtil;
import android.widget.Toast;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aF\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u0007\u00a8\u0006\u000e"}, d2 = {"VideoPlayerScreen", "", "videoUri", "", "onBack", "Lkotlin/Function0;", "viewModel", "Lcom/cipher/media/ui/video/VideoPlayerViewModel;", "subtitleViewModel", "Lcom/cipher/media/ui/video/subtitle/SubtitleViewModel;", "audioViewModel", "Lcom/cipher/media/ui/video/audio/VideoEqualizerViewModel;", "enhancementViewModel", "Lcom/cipher/media/ui/video/enhancement/VideoEnhancementViewModel;", "app_debug"})
public final class VideoPlayerScreenKt {
    
    /**
     * Full-screen video player with ExoPlayer, custom controls overlay,
     * gesture-based interactions, speed control, skip ±10s, aspect ratio,
     * and Picture-in-Picture support.
     */
    @androidx.compose.runtime.Composable()
    public static final void VideoPlayerScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String videoUri, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.VideoPlayerViewModel viewModel, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.subtitle.SubtitleViewModel subtitleViewModel, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.audio.VideoEqualizerViewModel audioViewModel, @org.jetbrains.annotations.NotNull()
    com.cipher.media.ui.video.enhancement.VideoEnhancementViewModel enhancementViewModel) {
    }
}