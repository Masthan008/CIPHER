package com.cipher.media.ui.video.subtitle;

import android.content.Context;
import android.net.Uri;
import com.cipher.media.ui.video.subtitle.model.SubtitleCue;
import com.cipher.media.ui.video.subtitle.model.SubtitleFormat;
import com.cipher.media.ui.video.subtitle.model.SubtitleTrack;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Multi-format subtitle parser supporting SRT, ASS/SSA, and WebVTT.
 * Auto-detects encoding (UTF-8, UTF-16 BOM, ANSI fallback).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J(\u0010\u0003\u001a\u0004\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\bJ0\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00060\u000b2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0014\u001a\u00020\u0012J\u0017\u0010\u0015\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0016\u001a\u00020\u0012H\u0002\u00a2\u0006\u0002\u0010\u0017J\u001c\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0012J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0014\u001a\u00020\u0012J\u0017\u0010\u001a\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0016\u001a\u00020\u0012H\u0002\u00a2\u0006\u0002\u0010\u0017J\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00062\u0006\u0010\u0014\u001a\u00020\u0012J\u0017\u0010\u001c\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0016\u001a\u00020\u0012H\u0002\u00a2\u0006\u0002\u0010\u0017J\u0018\u0010\u001d\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0012H\u0002\u00a8\u0006\u001f"}, d2 = {"Lcom/cipher/media/ui/video/subtitle/SubtitleEngine;", "", "()V", "findActiveCue", "Lcom/cipher/media/ui/video/subtitle/model/SubtitleCue;", "cues", "", "positionMs", "", "syncOffsetMs", "loadFromUri", "Lkotlin/Pair;", "Lcom/cipher/media/ui/video/subtitle/model/SubtitleTrack;", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "fileName", "", "parseAss", "text", "parseAssTimestamp", "timestamp", "(Ljava/lang/String;)Ljava/lang/Long;", "parseAutoDetect", "parseSrt", "parseSrtTimestamp", "parseVtt", "parseVttTimestamp", "readTextWithEncoding", "stripHtmlTags", "app_debug"})
public final class SubtitleEngine {
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.ui.video.subtitle.SubtitleEngine INSTANCE = null;
    
    private SubtitleEngine() {
        super();
    }
    
    /**
     * Load and parse a subtitle file from a content URI.
     * Returns a SubtitleTrack with all parsed cues.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlin.Pair<com.cipher.media.ui.video.subtitle.model.SubtitleTrack, java.util.List<com.cipher.media.ui.video.subtitle.model.SubtitleCue>> loadFromUri(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName) {
        return null;
    }
    
    /**
     * Parse raw text based on format detection.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.ui.video.subtitle.model.SubtitleCue> parseAutoDetect(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    java.lang.String fileName) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.ui.video.subtitle.model.SubtitleCue> parseSrt(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
        return null;
    }
    
    /**
     * Parse SRT timestamp: "HH:MM:SS,mmm" or "HH:MM:SS.mmm"
     */
    private final java.lang.Long parseSrtTimestamp(java.lang.String timestamp) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.ui.video.subtitle.model.SubtitleCue> parseAss(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
        return null;
    }
    
    /**
     * Parse ASS timestamp: "H:MM:SS.cc" (centiseconds)
     */
    private final java.lang.Long parseAssTimestamp(java.lang.String timestamp) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.cipher.media.ui.video.subtitle.model.SubtitleCue> parseVtt(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
        return null;
    }
    
    /**
     * Parse VTT timestamp: "HH:MM:SS.mmm" or "MM:SS.mmm"
     */
    private final java.lang.Long parseVttTimestamp(java.lang.String timestamp) {
        return null;
    }
    
    /**
     * Strip HTML/XML tags from subtitle text.
     */
    private final java.lang.String stripHtmlTags(java.lang.String text) {
        return null;
    }
    
    /**
     * Read text from URI with automatic encoding detection.
     * Handles UTF-8, UTF-16 LE/BE BOM, and falls back to system default.
     */
    private final java.lang.String readTextWithEncoding(android.content.Context context, android.net.Uri uri) {
        return null;
    }
    
    /**
     * Find the active subtitle cue for the given playback position.
     * Uses binary search for efficiency on large subtitle files.
     */
    @org.jetbrains.annotations.Nullable()
    public final com.cipher.media.ui.video.subtitle.model.SubtitleCue findActiveCue(@org.jetbrains.annotations.NotNull()
    java.util.List<com.cipher.media.ui.video.subtitle.model.SubtitleCue> cues, long positionMs, long syncOffsetMs) {
        return null;
    }
}