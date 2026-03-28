package com.cipher.media.ui.audio.lyrics.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cipher.media.data.model.AudioItem
import com.cipher.media.data.model.audiofx.Tier
import com.cipher.media.ui.audio.lyrics.model.LyricsSource
import com.cipher.media.ui.audio.lyrics.model.ParsedLyrics
import com.cipher.media.ui.theme.*

/**
 * Bottom sheet showing lyrics with download/paste options.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LyricsBottomSheet(
    audio: AudioItem?,
    lyrics: ParsedLyrics,
    currentPositionMs: Long,
    userTier: Tier,
    isSearching: Boolean,
    onDismiss: () -> Unit,
    onDownload: () -> Unit,
    onPaste: (String) -> Unit,
    onFullScreen: () -> Unit,
    onSeek: (Long) -> Unit
) {
    val isPro = userTier != Tier.FREE
    var showPasteDialog by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = CIPHERSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.md)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Lyrics",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = CIPHEROnSurface
                )
                Row {
                    // Paste button
                    IconButton(onClick = { showPasteDialog = true }) {
                        Icon(Icons.Default.ContentPaste, "Paste", tint = CIPHEROnSurfaceVariant)
                    }
                    // Download (Pro)
                    IconButton(
                        onClick = { if (isPro) onDownload() },
                        enabled = isPro
                    ) {
                        Icon(
                            Icons.Default.Download,
                            "Download",
                            tint = if (isPro) CIPHERPrimary else CIPHEROnSurfaceVariant.copy(alpha = 0.3f)
                        )
                    }
                    // Fullscreen (Pro)
                    IconButton(
                        onClick = { if (isPro) onFullScreen() },
                        enabled = isPro
                    ) {
                        Icon(
                            Icons.Default.Fullscreen,
                            "Fullscreen",
                            tint = if (isPro) CIPHERPrimary else CIPHEROnSurfaceVariant.copy(alpha = 0.3f)
                        )
                    }
                }
            }

            // Source badge
            lyrics.source.takeIf { it != LyricsSource.NONE }?.let { src ->
                AssistChip(
                    onClick = {},
                    label = { Text(src.name.lowercase().replaceFirstChar { it.uppercase() }) },
                    leadingIcon = {
                        Icon(
                            when (src) {
                                LyricsSource.EMBEDDED -> Icons.Default.MusicNote
                                LyricsSource.LOCAL_LRC -> Icons.Default.InsertDriveFile
                                LyricsSource.ONLINE -> Icons.Default.Cloud
                                LyricsSource.MANUAL -> Icons.Default.Edit
                                LyricsSource.NONE -> Icons.Default.QuestionMark
                            },
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            // Loading indicator
            if (isSearching) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = CIPHERPrimary
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Searching for lyrics...", style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurfaceVariant)
                }
                Spacer(Modifier.height(8.dp))
            }

            // Lyrics view (limited height in sheet)
            Box(modifier = Modifier.height(350.dp)) {
                SyncedLyricsView(
                    lyrics = lyrics,
                    currentPositionMs = currentPositionMs,
                    isSynced = lyrics.isSync && isPro,
                    onSeekToLine = if (isPro && lyrics.isSync) { ms -> onSeek(ms) } else null
                )
            }

            // Pro upsell
            if (!isPro && lyrics.isSync) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CIPHERPrimary.copy(alpha = 0.1f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Lock, tint = CIPHERPrimary, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Upgrade to Pro for synced scrolling, fullscreen mode & online lyrics",
                            style = MaterialTheme.typography.bodySmall,
                            color = CIPHERPrimary
                        )
                    }
                }
            }

            Spacer(Modifier.height(Spacing.xl))
        }
    }

    // Paste dialog
    if (showPasteDialog) {
        var pasteText by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showPasteDialog = false },
            title = { Text("Paste Lyrics") },
            text = {
                OutlinedTextField(
                    value = pasteText,
                    onValueChange = { pasteText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    placeholder = { Text("Paste lyrics or LRC text here...") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    onPaste(pasteText)
                    showPasteDialog = false
                }) { Text("Save", color = CIPHERPrimary) }
            },
            dismissButton = {
                TextButton(onClick = { showPasteDialog = false }) { Text("Cancel") }
            }
        )
    }
}
