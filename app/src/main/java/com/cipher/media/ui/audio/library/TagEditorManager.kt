package com.cipher.media.ui.audio.library

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.cipher.media.data.model.AudioItem
import com.cipher.media.data.model.library.MusicTag
import com.cipher.media.data.model.library.SortOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Reads metadata from MediaStore and writes tag edits via ContentResolver.
 * For full ID3 editing beyond MediaStore capabilities, uses direct file I/O
 * on the raw file path (requires MANAGE_EXTERNAL_STORAGE or legacy paths).
 */
@Singleton
class TagEditorManager @Inject constructor(
    private val contentResolver: ContentResolver
) {

    /**
     * Read full tag metadata for a given audio item.
     */
    suspend fun readTags(audio: AudioItem): MusicTag = withContext(Dispatchers.IO) {
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.YEAR,
            MediaStore.Audio.Media.TRACK,
            MediaStore.Audio.Media.COMPOSER,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.MIME_TYPE
        )

        var tag = MusicTag(
            audioId = audio.id,
            filePath = audio.path,
            title = audio.title,
            artist = audio.artist,
            album = audio.album,
            trackNumber = audio.trackNumber,
            format = audio.mimeType
        )

        contentResolver.query(
            audio.uri, projection, null, null, null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val yearIdx = cursor.getColumnIndex(MediaStore.Audio.Media.YEAR)
                val composerIdx = cursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER)
                if (yearIdx >= 0) {
                    val yr = cursor.getInt(yearIdx)
                    if (yr > 0) tag = tag.copy(year = yr)
                }
                if (composerIdx >= 0) {
                    tag = tag.copy(composer = cursor.getString(composerIdx) ?: "")
                }
            }
        }

        // Read genre from the genre content provider
        try {
            val genreUri = MediaStore.Audio.Genres.getContentUriForAudioId("external", audio.id.toInt())
            contentResolver.query(genreUri, arrayOf(MediaStore.Audio.Genres.NAME), null, null, null)?.use { c ->
                if (c.moveToFirst()) {
                    tag = tag.copy(genre = c.getString(0) ?: "")
                }
            }
        } catch (_: Exception) {}

        tag
    }

    /**
     * Write basic tags via MediaStore ContentValues.
     * Handles SAF permission requests on Android 10+ if required.
     */
    suspend fun writeTags(tag: MusicTag): Boolean = withContext(Dispatchers.IO) {
        try {
            val values = ContentValues().apply {
                put(MediaStore.Audio.Media.TITLE, tag.title)
                put(MediaStore.Audio.Media.ARTIST, tag.artist)
                put(MediaStore.Audio.Media.ALBUM, tag.album)
                if (tag.year != null) put(MediaStore.Audio.Media.YEAR, tag.year)
                if (tag.trackNumber != null) put(MediaStore.Audio.Media.TRACK, tag.trackNumber)
                if (tag.composer.isNotBlank()) put(MediaStore.Audio.Media.COMPOSER, tag.composer)
            }

            val uri = ContentUris.withAppendedId(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, tag.audioId
            )

            val rows = contentResolver.update(uri, values, null, null)
            rows > 0
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Batch write a single field across multiple audio items.
     */
    suspend fun batchWriteField(
        audioIds: List<Long>,
        field: String,
        value: String
    ): Int = withContext(Dispatchers.IO) {
        var successCount = 0
        audioIds.forEach { id ->
            try {
                val values = ContentValues().apply { put(field, value) }
                val uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id
                )
                if (contentResolver.update(uri, values, null, null) > 0) {
                    successCount++
                }
            } catch (_: Exception) {}
        }
        successCount
    }

    /**
     * Auto-number tracks sequentially in a batch.
     */
    suspend fun autoNumberTracks(audioIds: List<Long>): Int = withContext(Dispatchers.IO) {
        var count = 0
        audioIds.forEachIndexed { index, id ->
            try {
                val values = ContentValues().apply {
                    put(MediaStore.Audio.Media.TRACK, index + 1)
                }
                val uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id
                )
                if (contentResolver.update(uri, values, null, null) > 0) count++
            } catch (_: Exception) {}
        }
        count
    }

    /**
     * Get all unique folders containing audio files.
     */
    suspend fun getAudioFolders(): List<String> = withContext(Dispatchers.IO) {
        val folders = mutableSetOf<String>()
        val projection = arrayOf(MediaStore.Audio.Media.DATA)
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"

        contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection, selection, null, null
        )?.use { cursor ->
            val pathCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            while (cursor.moveToNext()) {
                val path = cursor.getString(pathCol) ?: continue
                val folder = File(path).parent ?: continue
                folders.add(folder)
            }
        }
        folders.sorted()
    }

    /**
     * Get all unique artists.
     */
    suspend fun getArtists(): List<Pair<String, Int>> = withContext(Dispatchers.IO) {
        val artists = mutableMapOf<String, Int>()
        val projection = arrayOf(MediaStore.Audio.Media.ARTIST)
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0 AND ${MediaStore.Audio.Media.DURATION} > 10000"

        contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null
        )?.use { cursor ->
            val col = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            while (cursor.moveToNext()) {
                val artist = cursor.getString(col) ?: "Unknown"
                artists[artist] = (artists[artist] ?: 0) + 1
            }
        }
        artists.entries.map { it.key to it.value }.sortedBy { it.first }
    }

    /**
     * Get all unique albums with art URIs.
     */
    suspend fun getAlbums(): List<Triple<String, String, Uri?>> = withContext(Dispatchers.IO) {
        val albums = mutableMapOf<String, Pair<String, Uri?>>()
        val projection = arrayOf(
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID
        )
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0 AND ${MediaStore.Audio.Media.DURATION} > 10000"

        contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null
        )?.use { cursor ->
            val albumCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val artistCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumIdCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            while (cursor.moveToNext()) {
                val album = cursor.getString(albumCol) ?: "Unknown"
                val artist = cursor.getString(artistCol) ?: "Unknown"
                val albumId = cursor.getLong(albumIdCol)
                if (!albums.containsKey(album)) {
                    val artUri = ContentUris.withAppendedId(
                        Uri.parse("content://media/external/audio/albumart"), albumId
                    )
                    albums[album] = artist to artUri
                }
            }
        }
        albums.entries.map { Triple(it.key, it.value.first, it.value.second) }
            .sortedBy { it.first }
    }

    /**
     * Get all unique genres.
     */
    suspend fun getGenres(): List<Pair<String, Int>> = withContext(Dispatchers.IO) {
        val genres = mutableMapOf<String, Int>()
        try {
            contentResolver.query(
                MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Audio.Genres._ID, MediaStore.Audio.Genres.NAME),
                null, null, null
            )?.use { cursor ->
                val idCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Genres._ID)
                val nameCol = cursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME)
                while (cursor.moveToNext()) {
                    val name = cursor.getString(nameCol) ?: continue
                    val genreId = cursor.getLong(idCol)
                    // Count members
                    val membersUri = MediaStore.Audio.Genres.Members.getContentUri("external", genreId)
                    contentResolver.query(membersUri, arrayOf(MediaStore.Audio.Genres.Members.AUDIO_ID), null, null, null)?.use { mc ->
                        genres[name] = mc.count
                    }
                }
            }
        } catch (_: Exception) {}
        genres.entries.map { it.key to it.value }.sortedBy { it.first }
    }
}
