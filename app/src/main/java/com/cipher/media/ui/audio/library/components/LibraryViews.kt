package com.cipher.media.ui.audio.library.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cipher.media.ui.theme.*

/**
 * Grid layout for Albums and Artists views.
 */
@Composable
fun AlbumGrid(
    albums: List<Triple<String, String, Uri?>>, // name, artist, artUri
    onAlbumClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(Spacing.screenPadding),
        horizontalArrangement = Arrangement.spacedBy(Spacing.cardGap),
        verticalArrangement = Arrangement.spacedBy(Spacing.cardGap),
        modifier = Modifier.fillMaxSize()
    ) {
        items(albums, key = { it.first }) { (name, artist, artUri) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAlbumClick(name) },
                shape = RoundedCornerShape(Corners.medium),
                colors = CardDefaults.cardColors(containerColor = CIPHERSurface)
            ) {
                Column {
                    AsyncImage(
                        model = artUri,
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(topStart = Corners.medium, topEnd = Corners.medium))
                    )
                    Column(Modifier.padding(Spacing.cardPadding)) {
                        Text(
                            name,
                            style = MaterialTheme.typography.titleSmall,
                            color = CIPHEROnSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            artist,
                            style = MaterialTheme.typography.bodySmall,
                            color = CIPHEROnSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ArtistGrid(
    artists: List<Pair<String, Int>>, // name, count
    onArtistClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(Spacing.screenPadding),
        horizontalArrangement = Arrangement.spacedBy(Spacing.cardGap),
        verticalArrangement = Arrangement.spacedBy(Spacing.cardGap),
        modifier = Modifier.fillMaxSize()
    ) {
        items(artists, key = { it.first }) { (name, count) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onArtistClick(name) },
                shape = RoundedCornerShape(Corners.medium),
                colors = CardDefaults.cardColors(containerColor = CIPHERSurface)
            ) {
                Column(
                    modifier = Modifier.padding(Spacing.cardPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = CIPHERPrimary
                    )
                    Spacer(Modifier.height(Spacing.sm))
                    Text(
                        name,
                        style = MaterialTheme.typography.labelMedium,
                        color = CIPHEROnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        "$count songs",
                        style = MaterialTheme.typography.bodySmall,
                        color = CIPHEROnSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun GenreList(
    genres: List<Pair<String, Int>>,
    onGenreClick: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(Spacing.screenPadding),
        verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
    ) {
        items(genres, key = { it.first }) { (name, count) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onGenreClick(name) },
                shape = RoundedCornerShape(Corners.medium),
                colors = CardDefaults.cardColors(containerColor = CIPHERSurface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.md),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.MusicNote,
                        contentDescription = null,
                        tint = CIPHERPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(name, style = MaterialTheme.typography.titleSmall, color = CIPHEROnSurface)
                        Text("$count songs", style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurfaceVariant)
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = CIPHEROnSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun FolderTree(
    folders: List<String>,
    onFolderClick: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(Spacing.screenPadding),
        verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
    ) {
        items(folders) { folder ->
            val folderName = folder.substringAfterLast("/")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFolderClick(folder) },
                shape = RoundedCornerShape(Corners.medium),
                colors = CardDefaults.cardColors(containerColor = CIPHERSurface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.md),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Folder,
                        contentDescription = null,
                        tint = CIPHERPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(folderName, style = MaterialTheme.typography.titleSmall, color = CIPHEROnSurface, fontWeight = FontWeight.Medium)
                        Text(folder, style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = CIPHEROnSurfaceVariant)
                }
            }
        }
    }
}
