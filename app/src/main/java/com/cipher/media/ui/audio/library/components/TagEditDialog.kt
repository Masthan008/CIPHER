package com.cipher.media.ui.audio.library.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.cipher.media.data.model.audiofx.Tier
import com.cipher.media.data.model.library.MusicTag
import com.cipher.media.ui.theme.*

/**
 * Full-screen tag editor dialog for viewing (Free) or editing (Pro) audio metadata.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagEditDialog(
    tag: MusicTag,
    userTier: Tier,
    onSave: (MusicTag) -> Unit,
    onDismiss: () -> Unit
) {
    val isPro = userTier != Tier.FREE

    var title by remember { mutableStateOf(tag.title) }
    var artist by remember { mutableStateOf(tag.artist) }
    var album by remember { mutableStateOf(tag.album) }
    var albumArtist by remember { mutableStateOf(tag.albumArtist) }
    var year by remember { mutableStateOf(tag.year?.toString() ?: "") }
    var trackNumber by remember { mutableStateOf(tag.trackNumber?.toString() ?: "") }
    var discNumber by remember { mutableStateOf(tag.discNumber?.toString() ?: "") }
    var genre by remember { mutableStateOf(tag.genre) }
    var composer by remember { mutableStateOf(tag.composer) }
    var comment by remember { mutableStateOf(tag.comment) }
    var lyrics by remember { mutableStateOf(tag.lyrics) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .clip(RoundedCornerShape(16.dp)),
            color = CIPHERBackground
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = CIPHEROnSurface)
                    }
                    Text(
                        if (isPro) "Edit Tags" else "View Tags",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = CIPHEROnSurface
                    )
                    if (isPro) {
                        TextButton(onClick = {
                            onSave(
                                tag.copy(
                                    title = title,
                                    artist = artist,
                                    album = album,
                                    albumArtist = albumArtist,
                                    year = year.toIntOrNull(),
                                    trackNumber = trackNumber.toIntOrNull(),
                                    discNumber = discNumber.toIntOrNull(),
                                    genre = genre,
                                    composer = composer,
                                    comment = comment,
                                    lyrics = lyrics
                                )
                            )
                        }) {
                            Text("Save", color = CIPHERPrimary, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Spacer(Modifier.width(48.dp))
                    }
                }

                if (!isPro) {
                    Spacer(Modifier.height(8.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CIPHERPrimary.copy(alpha = 0.1f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Lock, tint = CIPHERPrimary, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "Upgrade to Pro to edit tags",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CIPHERPrimary
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // File info (always visible)
                Card(
                    colors = CardDefaults.cardColors(containerColor = CIPHERSurface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("File Info", style = MaterialTheme.typography.labelMedium, color = CIPHEROnSurfaceVariant)
                        Spacer(Modifier.height(4.dp))
                        Text("Format: ${tag.format}", style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurface)
                        Text("Path: ${tag.filePath}", style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurfaceVariant)
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Editable fields
                TagField("Title", title, isPro) { title = it }
                TagField("Artist", artist, isPro) { artist = it }
                TagField("Album", album, isPro) { album = it }
                TagField("Album Artist", albumArtist, isPro) { albumArtist = it }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(Modifier.weight(1f)) { TagField("Year", year, isPro) { year = it } }
                    Box(Modifier.weight(1f)) { TagField("Track #", trackNumber, isPro) { trackNumber = it } }
                    Box(Modifier.weight(1f)) { TagField("Disc #", discNumber, isPro) { discNumber = it } }
                }

                TagField("Genre", genre, isPro) { genre = it }
                TagField("Composer", composer, isPro) { composer = it }
                TagField("Comment", comment, isPro) { comment = it }

                Spacer(Modifier.height(8.dp))
                Text("Lyrics", style = MaterialTheme.typography.labelMedium, color = CIPHEROnSurfaceVariant)
                OutlinedTextField(
                    value = lyrics,
                    onValueChange = { if (isPro) lyrics = it },
                    readOnly = !isPro,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    textStyle = MaterialTheme.typography.bodySmall,
                    placeholder = { Text("No lyrics", style = MaterialTheme.typography.bodySmall) }
                )

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

@Composable
private fun TagField(
    label: String,
    value: String,
    editable: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { if (editable) onValueChange(it) },
        label = { Text(label) },
        readOnly = !editable,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        textStyle = MaterialTheme.typography.bodyMedium
    )
}
