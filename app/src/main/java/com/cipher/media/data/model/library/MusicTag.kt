package com.cipher.media.data.model.library

/**
 * Represents the full editable metadata of an audio file.
 * Maps to ID3v2.4 / Vorbis Comment / APE tags.
 */
data class MusicTag(
    val audioId: Long,
    val filePath: String,
    val title: String = "",
    val artist: String = "",
    val album: String = "",
    val albumArtist: String = "",
    val year: Int? = null,
    val trackNumber: Int? = null,
    val discNumber: Int? = null,
    val genre: String = "",
    val composer: String = "",
    val comment: String = "",
    val lyrics: String = "",
    val albumArtBytes: ByteArray? = null,
    val bitrate: Int = 0,
    val sampleRate: Int = 0,
    val channels: Int = 0,
    val format: String = "" // mp3, flac, ogg, etc.
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MusicTag) return false
        return audioId == other.audioId && filePath == other.filePath
    }

    override fun hashCode(): Int = audioId.hashCode()
}
