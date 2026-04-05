package com.cipher.media.data.online.jamendo

/**
 * Data models for Jamendo API v3.0
 * Free music streaming API - https://developer.jamendo.com/v3.0
 */

data class JamendoResponse(
    val headers: JamendoHeaders,
    val results: List<JamendoTrack>
)

data class JamendoHeaders(
    val status: String,
    val code: Int,
    val error_message: String?,
    val warnings: String?,
    val results_count: Int
)

data class JamendoTrack(
    val id: String,
    val name: String,
    val duration: Int,
    val artist_id: String,
    val artist_name: String,
    val artist_idstr: String,
    val album_name: String,
    val album_id: String,
    val license_ccurl: String,
    val position: Int,
    val releasedate: String,
    val album_image: String,
    val image: String,
    val audio: String,
    val audiodownload: String?,
    val prourl: String?,
    val shorturl: String,
    val shareurl: String,
    val waveform: String,
    val musicinfo: MusicInfo?,
    val stats: TrackStats?,
    val lyrics: String?
) {
    fun toOnlineTrack(): OnlineTrack {
        return OnlineTrack(
            id = "jamendo_$id",
            title = name,
            artist = artist_name,
            album = album_name,
            duration = duration * 1000L,
            artworkUrl = album_image,
            streamUrl = audio,
            source = TrackSource.JAMENDO,
            license = license_ccurl,
            lyrics = lyrics
        )
    }
}

data class MusicInfo(
    val tags: List<String>? = null, // Can be array or object from API
    val language: String? = null,
    val gender: String? = null,
    val acousticelectric: String? = null,
    val vocalinstrumental: String? = null,
    val speed: String? = null
)

data class TrackStats(
    val rate_downloads_total: Int,
    val rate_listened_total: Int,
    val playlisted: Int,
    val favored: Int,
    val likes: Int,
    val dislikes: Int,
    val average_rating: Float?
)

enum class TrackSource {
    JAMENDO,
    LOCAL,
    RADIO
}

data class OnlineTrack(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val artworkUrl: String,
    val streamUrl: String,
    val source: TrackSource,
    val license: String,
    val lyrics: String? = null
) {
    fun formatDuration(): String {
        val totalSeconds = duration / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%d:%02d".format(minutes, seconds)
    }
}

object SupportedLanguages {
    val LANGUAGES = listOf(
        "all" to "All",
        "hindi" to "हिन्दी",
        "tamil" to "தமிழ்",
        "telugu" to "తెలుగు",
        "punjabi" to "ਪੰਜਾਬੀ",
        "marathi" to "मराठी",
        "bengali" to "বাংলা",
        "english" to "English",
        "malayalam" to "മലയാളം",
        "kannada" to "ಕನ್ನಡ",
        "gujarati" to "ગુજરાતી"
    )

    val GENRES = listOf(
        "Pop", "Rock", "Electronic", "Hip-Hop", "Jazz",
        "Classical", "Folk", "Indian", "Bollywood"
    )
}
