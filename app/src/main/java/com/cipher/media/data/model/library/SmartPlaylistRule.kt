package com.cipher.media.data.model.library

/**
 * Rules engine for smart playlist auto-generation.
 */
sealed class SmartPlaylistRule(val displayName: String) {
    data class RecentlyAdded(val days: Int = 7) : SmartPlaylistRule("Recently Added (${days}d)")
    data class MostPlayed(val limit: Int = 25) : SmartPlaylistRule("Most Played (Top $limit)")
    object NeverPlayed : SmartPlaylistRule("Never Played")
    object RecentlyPlayed : SmartPlaylistRule("Recently Played")
    object HighestRated : SmartPlaylistRule("Highest Rated")

    companion object {
        val ALL = listOf(
            RecentlyAdded(7),
            RecentlyAdded(30),
            MostPlayed(25),
            MostPlayed(50),
            MostPlayed(100),
            NeverPlayed,
            RecentlyPlayed,
            HighestRated
        )
    }
}

enum class LibraryTab(val label: String) {
    SONGS("Songs"),
    ALBUMS("Albums"),
    ARTISTS("Artists"),
    GENRES("Genres"),
    FOLDERS("Folders"),
    PLAYLISTS("Playlists")
}

enum class SortOption(val label: String) {
    NAME_ASC("Name (A-Z)"),
    NAME_DESC("Name (Z-A)"),
    DATE_NEWEST("Date (Newest)"),
    DATE_OLDEST("Date (Oldest)"),
    ARTIST("Artist"),
    ALBUM("Album"),
    DURATION_LONG("Duration (Longest)"),
    DURATION_SHORT("Duration (Shortest)")
}
