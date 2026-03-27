package com.cipher.media.ui.video.subtitle.model;

/**
 * Constants for subtitle tier gating.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/cipher/media/ui/video/subtitle/model/SubtitleTierConfig;", "", "()V", "FREE_MAX_FONT_SIZE", "", "FREE_MAX_SYNC_MS", "", "FREE_MIN_FONT_SIZE", "PRO_MAX_FONT_SIZE", "PRO_MAX_SYNC_MS", "PRO_MIN_FONT_SIZE", "PRO_SYNC_STEP_MS", "app_debug"})
public final class SubtitleTierConfig {
    public static final int FREE_MIN_FONT_SIZE = 75;
    public static final int FREE_MAX_FONT_SIZE = 150;
    public static final long FREE_MAX_SYNC_MS = 5000L;
    public static final int PRO_MIN_FONT_SIZE = 50;
    public static final int PRO_MAX_FONT_SIZE = 200;
    public static final long PRO_MAX_SYNC_MS = 60000L;
    public static final long PRO_SYNC_STEP_MS = 50L;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.ui.video.subtitle.model.SubtitleTierConfig INSTANCE = null;
    
    private SubtitleTierConfig() {
        super();
    }
}