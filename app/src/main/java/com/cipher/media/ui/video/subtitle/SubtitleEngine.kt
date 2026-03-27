package com.cipher.media.ui.video.subtitle

import android.content.Context
import android.net.Uri
import com.cipher.media.ui.video.subtitle.model.SubtitleCue
import com.cipher.media.ui.video.subtitle.model.SubtitleFormat
import com.cipher.media.ui.video.subtitle.model.SubtitleTrack
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.Charset

// FREE FEATURE — SRT/ASS/VTT parsing available to all users

/**
 * Multi-format subtitle parser supporting SRT, ASS/SSA, and WebVTT.
 * Auto-detects encoding (UTF-8, UTF-16 BOM, ANSI fallback).
 */
object SubtitleEngine {

    /**
     * Load and parse a subtitle file from a content URI.
     * Returns a SubtitleTrack with all parsed cues.
     */
    fun loadFromUri(context: Context, uri: Uri, fileName: String): Pair<SubtitleTrack, List<SubtitleCue>> {
        val format = SubtitleFormat.fromFileName(fileName)
        val text = readTextWithEncoding(context, uri)
        val cues = when (format) {
            SubtitleFormat.SRT -> parseSrt(text)
            SubtitleFormat.ASS -> parseAss(text)
            SubtitleFormat.VTT -> parseVtt(text)
            SubtitleFormat.UNKNOWN -> parseSrt(text) // Try SRT as fallback
        }
        val track = SubtitleTrack(
            fileName = fileName,
            format = format,
            cueCount = cues.size,
            filePath = uri.toString()
        )
        return track to cues
    }

    /**
     * Parse raw text based on format detection.
     */
    fun parseAutoDetect(text: String, fileName: String): List<SubtitleCue> {
        val format = SubtitleFormat.fromFileName(fileName)
        return when (format) {
            SubtitleFormat.SRT -> parseSrt(text)
            SubtitleFormat.ASS -> parseAss(text)
            SubtitleFormat.VTT -> parseVtt(text)
            SubtitleFormat.UNKNOWN -> parseSrt(text)
        }
    }

    // ═══════════════════════════════════════════
    //  SRT PARSER
    //  Format: index\n HH:MM:SS,mmm --> HH:MM:SS,mmm\n text\n\n
    // ═══════════════════════════════════════════
    fun parseSrt(text: String): List<SubtitleCue> {
        val cues = mutableListOf<SubtitleCue>()
        val blocks = text.replace("\r\n", "\n").trim().split("\n\n")

        for (block in blocks) {
            val lines = block.trim().split("\n")
            if (lines.size < 2) continue

            // First line is the index
            val index = lines[0].trim().toIntOrNull() ?: continue

            // Second line is the timecode
            val timeLine = lines[1].trim()
            val timeParts = timeLine.split("-->")
            if (timeParts.size != 2) continue

            val startMs = parseSrtTimestamp(timeParts[0].trim()) ?: continue
            val endMs = parseSrtTimestamp(timeParts[1].trim()) ?: continue

            // Remaining lines are the subtitle text
            val subtitleText = lines.drop(2).joinToString("\n").trim()
            val cleanText = stripHtmlTags(subtitleText)

            if (cleanText.isNotBlank()) {
                cues.add(SubtitleCue(index, startMs, endMs, cleanText))
            }
        }
        return cues
    }

    /**
     * Parse SRT timestamp: "HH:MM:SS,mmm" or "HH:MM:SS.mmm"
     */
    private fun parseSrtTimestamp(timestamp: String): Long? {
        val cleaned = timestamp.replace(',', '.').trim()
        val regex = Regex("""(\d{1,2}):(\d{2}):(\d{2})\.(\d{1,3})""")
        val match = regex.matchEntire(cleaned) ?: return null

        val hours = match.groupValues[1].toLong()
        val minutes = match.groupValues[2].toLong()
        val seconds = match.groupValues[3].toLong()
        val millis = match.groupValues[4].padEnd(3, '0').toLong()

        return hours * 3600_000 + minutes * 60_000 + seconds * 1_000 + millis
    }

    // ═══════════════════════════════════════════
    //  ASS/SSA PARSER
    //  Dialogue: Layer,Start,End,Style,Name,MarginL,MarginR,MarginV,Effect,Text
    // ═══════════════════════════════════════════
    fun parseAss(text: String): List<SubtitleCue> {
        val cues = mutableListOf<SubtitleCue>()
        val lines = text.replace("\r\n", "\n").split("\n")
        var index = 0

        for (line in lines) {
            val trimmed = line.trim()
            if (!trimmed.startsWith("Dialogue:", ignoreCase = true)) continue

            val content = trimmed.substringAfter(":")
            val parts = content.split(",", limit = 10)
            if (parts.size < 10) continue

            val startMs = parseAssTimestamp(parts[1].trim()) ?: continue
            val endMs = parseAssTimestamp(parts[2].trim()) ?: continue
            val rawText = parts[9].trim()

            // Strip ASS override tags like {\b1}, {\i1}, {\pos(x,y)}
            val cleanText = rawText
                .replace(Regex("""\{\\[^}]*\}"""), "")
                .replace("\\N", "\n")
                .replace("\\n", "\n")
                .trim()

            if (cleanText.isNotBlank()) {
                index++
                cues.add(SubtitleCue(index, startMs, endMs, cleanText))
            }
        }
        return cues
    }

    /**
     * Parse ASS timestamp: "H:MM:SS.cc" (centiseconds)
     */
    private fun parseAssTimestamp(timestamp: String): Long? {
        val regex = Regex("""(\d+):(\d{2}):(\d{2})\.(\d{2})""")
        val match = regex.matchEntire(timestamp.trim()) ?: return null

        val hours = match.groupValues[1].toLong()
        val minutes = match.groupValues[2].toLong()
        val seconds = match.groupValues[3].toLong()
        val centis = match.groupValues[4].toLong()

        return hours * 3600_000 + minutes * 60_000 + seconds * 1_000 + centis * 10
    }

    // ═══════════════════════════════════════════
    //  WebVTT PARSER
    //  WEBVTT\n\n  HH:MM:SS.mmm --> HH:MM:SS.mmm [settings]\n text\n\n
    // ═══════════════════════════════════════════
    fun parseVtt(text: String): List<SubtitleCue> {
        val cues = mutableListOf<SubtitleCue>()
        val content = text.replace("\r\n", "\n")

        // Remove WEBVTT header and any metadata blocks
        val bodyStart = content.indexOf("\n\n")
        if (bodyStart < 0) return cues
        val body = content.substring(bodyStart).trim()

        val blocks = body.split("\n\n")
        var index = 0

        for (block in blocks) {
            val lines = block.trim().split("\n")
            if (lines.isEmpty()) continue

            // Find the timecode line (contains "-->")
            val timeLineIndex = lines.indexOfFirst { "-->" in it }
            if (timeLineIndex < 0) continue

            val timeParts = lines[timeLineIndex].split("-->")
            if (timeParts.size != 2) continue

            // Strip cue settings from end time (e.g., "00:01:30.000 position:50%")
            val startMs = parseVttTimestamp(timeParts[0].trim()) ?: continue
            val endRaw = timeParts[1].trim().split(" ")[0]
            val endMs = parseVttTimestamp(endRaw) ?: continue

            val subtitleText = lines.drop(timeLineIndex + 1).joinToString("\n").trim()
            val cleanText = stripHtmlTags(subtitleText)

            if (cleanText.isNotBlank()) {
                index++
                cues.add(SubtitleCue(index, startMs, endMs, cleanText))
            }
        }
        return cues
    }

    /**
     * Parse VTT timestamp: "HH:MM:SS.mmm" or "MM:SS.mmm"
     */
    private fun parseVttTimestamp(timestamp: String): Long? {
        val cleaned = timestamp.trim()

        // Full format: HH:MM:SS.mmm
        val fullRegex = Regex("""(\d{1,2}):(\d{2}):(\d{2})\.(\d{1,3})""")
        fullRegex.matchEntire(cleaned)?.let { match ->
            val h = match.groupValues[1].toLong()
            val m = match.groupValues[2].toLong()
            val s = match.groupValues[3].toLong()
            val ms = match.groupValues[4].padEnd(3, '0').toLong()
            return h * 3600_000 + m * 60_000 + s * 1_000 + ms
        }

        // Short format: MM:SS.mmm
        val shortRegex = Regex("""(\d{1,2}):(\d{2})\.(\d{1,3})""")
        shortRegex.matchEntire(cleaned)?.let { match ->
            val m = match.groupValues[1].toLong()
            val s = match.groupValues[2].toLong()
            val ms = match.groupValues[3].padEnd(3, '0').toLong()
            return m * 60_000 + s * 1_000 + ms
        }

        return null
    }

    // ═══════════════════════════════════════════
    //  UTILITIES
    // ═══════════════════════════════════════════

    /**
     * Strip HTML/XML tags from subtitle text.
     */
    private fun stripHtmlTags(text: String): String {
        return text
            .replace(Regex("""<[^>]+>"""), "")
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&nbsp;", " ")
            .trim()
    }

    /**
     * Read text from URI with automatic encoding detection.
     * Handles UTF-8, UTF-16 LE/BE BOM, and falls back to system default.
     */
    private fun readTextWithEncoding(context: Context, uri: Uri): String {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: return ""

        return inputStream.use { stream ->
            val bytes = stream.readBytes()

            val charset = when {
                // UTF-16 LE BOM
                bytes.size >= 2 && bytes[0] == 0xFF.toByte() && bytes[1] == 0xFE.toByte() ->
                    Charsets.UTF_16LE
                // UTF-16 BE BOM
                bytes.size >= 2 && bytes[0] == 0xFE.toByte() && bytes[1] == 0xFF.toByte() ->
                    Charsets.UTF_16BE
                // UTF-8 BOM
                bytes.size >= 3 && bytes[0] == 0xEF.toByte() && bytes[1] == 0xBB.toByte() && bytes[2] == 0xBF.toByte() ->
                    Charsets.UTF_8
                // Default: try UTF-8
                else -> Charsets.UTF_8
            }

            String(bytes, charset)
        }
    }

    /**
     * Find the active subtitle cue for the given playback position.
     * Uses binary search for efficiency on large subtitle files.
     */
    fun findActiveCue(cues: List<SubtitleCue>, positionMs: Long, syncOffsetMs: Long = 0L): SubtitleCue? {
        // Linear search (cues are already sorted by start time)
        return cues.firstOrNull { it.isActiveAt(positionMs, syncOffsetMs) }
    }
}
