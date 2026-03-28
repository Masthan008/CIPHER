package com.cipher.media.data.model.library;

/**
 * Rules engine for smart playlist auto-generation.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00072\u00020\u0001:\u0006\u0007\b\t\n\u000b\fB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0005\r\u000e\u000f\u0010\u0011\u00a8\u0006\u0012"}, d2 = {"Lcom/cipher/media/data/model/library/SmartPlaylistRule;", "", "displayName", "", "(Ljava/lang/String;)V", "getDisplayName", "()Ljava/lang/String;", "Companion", "HighestRated", "MostPlayed", "NeverPlayed", "RecentlyAdded", "RecentlyPlayed", "Lcom/cipher/media/data/model/library/SmartPlaylistRule$HighestRated;", "Lcom/cipher/media/data/model/library/SmartPlaylistRule$MostPlayed;", "Lcom/cipher/media/data/model/library/SmartPlaylistRule$NeverPlayed;", "Lcom/cipher/media/data/model/library/SmartPlaylistRule$RecentlyAdded;", "Lcom/cipher/media/data/model/library/SmartPlaylistRule$RecentlyPlayed;", "app_debug"})
public abstract class SmartPlaylistRule {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String displayName = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.cipher.media.data.model.library.SmartPlaylistRule> ALL = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.cipher.media.data.model.library.SmartPlaylistRule.Companion Companion = null;
    
    private SmartPlaylistRule(java.lang.String displayName) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDisplayName() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/cipher/media/data/model/library/SmartPlaylistRule$Companion;", "", "()V", "ALL", "", "Lcom/cipher/media/data/model/library/SmartPlaylistRule;", "getALL", "()Ljava/util/List;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.cipher.media.data.model.library.SmartPlaylistRule> getALL() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cipher/media/data/model/library/SmartPlaylistRule$HighestRated;", "Lcom/cipher/media/data/model/library/SmartPlaylistRule;", "()V", "app_debug"})
    public static final class HighestRated extends com.cipher.media.data.model.library.SmartPlaylistRule {
        @org.jetbrains.annotations.NotNull()
        public static final com.cipher.media.data.model.library.SmartPlaylistRule.HighestRated INSTANCE = null;
        
        private HighestRated() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/cipher/media/data/model/library/SmartPlaylistRule$MostPlayed;", "Lcom/cipher/media/data/model/library/SmartPlaylistRule;", "limit", "", "(I)V", "getLimit", "()I", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "app_debug"})
    public static final class MostPlayed extends com.cipher.media.data.model.library.SmartPlaylistRule {
        private final int limit = 0;
        
        public MostPlayed(int limit) {
        }
        
        public final int getLimit() {
            return 0;
        }
        
        public MostPlayed() {
        }
        
        public final int component1() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cipher.media.data.model.library.SmartPlaylistRule.MostPlayed copy(int limit) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cipher/media/data/model/library/SmartPlaylistRule$NeverPlayed;", "Lcom/cipher/media/data/model/library/SmartPlaylistRule;", "()V", "app_debug"})
    public static final class NeverPlayed extends com.cipher.media.data.model.library.SmartPlaylistRule {
        @org.jetbrains.annotations.NotNull()
        public static final com.cipher.media.data.model.library.SmartPlaylistRule.NeverPlayed INSTANCE = null;
        
        private NeverPlayed() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/cipher/media/data/model/library/SmartPlaylistRule$RecentlyAdded;", "Lcom/cipher/media/data/model/library/SmartPlaylistRule;", "days", "", "(I)V", "getDays", "()I", "component1", "copy", "equals", "", "other", "", "hashCode", "toString", "", "app_debug"})
    public static final class RecentlyAdded extends com.cipher.media.data.model.library.SmartPlaylistRule {
        private final int days = 0;
        
        public RecentlyAdded(int days) {
        }
        
        public final int getDays() {
            return 0;
        }
        
        public RecentlyAdded() {
        }
        
        public final int component1() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.cipher.media.data.model.library.SmartPlaylistRule.RecentlyAdded copy(int days) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/cipher/media/data/model/library/SmartPlaylistRule$RecentlyPlayed;", "Lcom/cipher/media/data/model/library/SmartPlaylistRule;", "()V", "app_debug"})
    public static final class RecentlyPlayed extends com.cipher.media.data.model.library.SmartPlaylistRule {
        @org.jetbrains.annotations.NotNull()
        public static final com.cipher.media.data.model.library.SmartPlaylistRule.RecentlyPlayed INSTANCE = null;
        
        private RecentlyPlayed() {
        }
    }
}