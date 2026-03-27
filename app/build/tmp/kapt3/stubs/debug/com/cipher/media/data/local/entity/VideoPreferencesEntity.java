package com.cipher.media.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room entity for "Per-Video Memory" feature (Power Tier).
 * Stores playback settings (speed, subtitle sync, crop mode) per video URI.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b2\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u009d\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\t\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0011\u001a\u00020\t\u0012\b\b\u0002\u0010\u0012\u001a\u00020\t\u0012\b\b\u0002\u0010\u0013\u001a\u00020\t\u0012\b\b\u0002\u0010\u0014\u001a\u00020\t\u00a2\u0006\u0002\u0010\u0015J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0005H\u00c6\u0003J\t\u0010+\u001a\u00020\u0005H\u00c6\u0003J\t\u0010,\u001a\u00020\tH\u00c6\u0003J\t\u0010-\u001a\u00020\tH\u00c6\u0003J\t\u0010.\u001a\u00020\tH\u00c6\u0003J\t\u0010/\u001a\u00020\tH\u00c6\u0003J\t\u00100\u001a\u00020\u0005H\u00c6\u0003J\t\u00101\u001a\u00020\u0007H\u00c6\u0003J\t\u00102\u001a\u00020\tH\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\tH\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0005H\u00c6\u0003J\u00a3\u0001\u00108\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\f\u001a\u00020\t2\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00052\b\b\u0002\u0010\u0011\u001a\u00020\t2\b\b\u0002\u0010\u0012\u001a\u00020\t2\b\b\u0002\u0010\u0013\u001a\u00020\t2\b\b\u0002\u0010\u0014\u001a\u00020\tH\u00c6\u0001J\u0013\u00109\u001a\u00020\u00072\b\u0010:\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010;\u001a\u00020<H\u00d6\u0001J\t\u0010=\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0013\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0011\u0010\u0014\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0011\u0010\u000f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0010\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0011\u0010\u0011\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0017R\u0011\u0010\u0012\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0017R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0019R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0019R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0017R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0019R\u0011\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001d\u00a8\u0006>"}, d2 = {"Lcom/cipher/media/data/local/entity/VideoPreferencesEntity;", "", "videoUri", "", "playbackSpeed", "", "pitchCorrection", "", "subtitleSyncOffsetMs", "", "selectedSubtitleTrackId", "selectedAudioTrackId", "audioDelayMs", "cropMode", "zoomLevel", "panX", "panY", "pointA", "pointB", "lastPlaybackPositionMs", "lastUpdatedTimestamp", "(Ljava/lang/String;FZJLjava/lang/String;Ljava/lang/String;JLjava/lang/String;FFFJJJJ)V", "getAudioDelayMs", "()J", "getCropMode", "()Ljava/lang/String;", "getLastPlaybackPositionMs", "getLastUpdatedTimestamp", "getPanX", "()F", "getPanY", "getPitchCorrection", "()Z", "getPlaybackSpeed", "getPointA", "getPointB", "getSelectedAudioTrackId", "getSelectedSubtitleTrackId", "getSubtitleSyncOffsetMs", "getVideoUri", "getZoomLevel", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
@androidx.room.Entity(tableName = "video_preferences")
public final class VideoPreferencesEntity {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String videoUri = null;
    private final float playbackSpeed = 0.0F;
    private final boolean pitchCorrection = false;
    private final long subtitleSyncOffsetMs = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String selectedSubtitleTrackId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String selectedAudioTrackId = null;
    private final long audioDelayMs = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String cropMode = null;
    private final float zoomLevel = 0.0F;
    private final float panX = 0.0F;
    private final float panY = 0.0F;
    private final long pointA = 0L;
    private final long pointB = 0L;
    private final long lastPlaybackPositionMs = 0L;
    private final long lastUpdatedTimestamp = 0L;
    
    public VideoPreferencesEntity(@org.jetbrains.annotations.NotNull()
    java.lang.String videoUri, float playbackSpeed, boolean pitchCorrection, long subtitleSyncOffsetMs, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedSubtitleTrackId, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedAudioTrackId, long audioDelayMs, @org.jetbrains.annotations.NotNull()
    java.lang.String cropMode, float zoomLevel, float panX, float panY, long pointA, long pointB, long lastPlaybackPositionMs, long lastUpdatedTimestamp) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getVideoUri() {
        return null;
    }
    
    public final float getPlaybackSpeed() {
        return 0.0F;
    }
    
    public final boolean getPitchCorrection() {
        return false;
    }
    
    public final long getSubtitleSyncOffsetMs() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSelectedSubtitleTrackId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSelectedAudioTrackId() {
        return null;
    }
    
    public final long getAudioDelayMs() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCropMode() {
        return null;
    }
    
    public final float getZoomLevel() {
        return 0.0F;
    }
    
    public final float getPanX() {
        return 0.0F;
    }
    
    public final float getPanY() {
        return 0.0F;
    }
    
    public final long getPointA() {
        return 0L;
    }
    
    public final long getPointB() {
        return 0L;
    }
    
    public final long getLastPlaybackPositionMs() {
        return 0L;
    }
    
    public final long getLastUpdatedTimestamp() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final float component10() {
        return 0.0F;
    }
    
    public final float component11() {
        return 0.0F;
    }
    
    public final long component12() {
        return 0L;
    }
    
    public final long component13() {
        return 0L;
    }
    
    public final long component14() {
        return 0L;
    }
    
    public final long component15() {
        return 0L;
    }
    
    public final float component2() {
        return 0.0F;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final long component4() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    public final long component7() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    public final float component9() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.cipher.media.data.local.entity.VideoPreferencesEntity copy(@org.jetbrains.annotations.NotNull()
    java.lang.String videoUri, float playbackSpeed, boolean pitchCorrection, long subtitleSyncOffsetMs, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedSubtitleTrackId, @org.jetbrains.annotations.Nullable()
    java.lang.String selectedAudioTrackId, long audioDelayMs, @org.jetbrains.annotations.NotNull()
    java.lang.String cropMode, float zoomLevel, float panX, float panY, long pointA, long pointB, long lastPlaybackPositionMs, long lastUpdatedTimestamp) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}