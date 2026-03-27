package com.cipher.media.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.ui.components.EmptyState
import com.cipher.media.ui.components.StaggeredAnimatedItem
import com.cipher.media.ui.theme.*

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val query by viewModel.query.collectAsState()
    val filter by viewModel.filter.collectAsState()
    val results by viewModel.results.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Back", tint = CIPHEROnSurface)
                    }
                    OutlinedTextField(
                        value = query,
                        onValueChange = { viewModel.updateQuery(it) },
                        placeholder = { Text("Search files...", color = CIPHEROnSurfaceVariant) },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CIPHERPrimary,
                            unfocusedBorderColor = CIPHEROutlineVariant,
                            cursorColor = CIPHERPrimary
                        ),
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = CIPHEROnSurfaceVariant) },
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                IconButton(onClick = { viewModel.updateQuery("") }) {
                                    Icon(Icons.Default.Clear, "Clear", tint = CIPHEROnSurfaceVariant)
                                }
                            }
                        }
                    )
                }

                // Filter chips
                Row(
                    modifier = Modifier.padding(start = 48.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SearchFilter.entries.forEach { f ->
                        FilterChip(
                            selected = filter == f,
                            onClick = { viewModel.setFilter(f) },
                            label = { Text(f.name.lowercase().replaceFirstChar { it.uppercase() }) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CIPHERPrimary,
                                selectedLabelColor = CIPHEROnPrimary
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        if (isSearching) {
            Box(Modifier.fillMaxSize().padding(innerPadding), Alignment.Center) {
                CircularProgressIndicator(color = CIPHERPrimary)
            }
        } else if (results.isEmpty() && query.length >= 2) {
            EmptyState(
                icon = Icons.Default.SearchOff,
                title = "No results found",
                subtitle = "Try a different search term",
                modifier = Modifier.padding(innerPadding)
            )
        } else if (query.length < 2) {
            EmptyState(
                icon = Icons.Default.Search,
                title = "Search your files",
                subtitle = "Type at least 2 characters to search",
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(results, key = { i, r -> "${r.type}_${r.title}_$i" }) { index, result ->
                    StaggeredAnimatedItem(index = index) {
                        SearchResultItem(result)
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchResultItem(result: SearchResult) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = when (result.type) {
                SearchFilter.VIDEO -> Icons.Default.VideoFile
                SearchFilter.MUSIC -> Icons.Default.AudioFile
                SearchFilter.VAULT -> Icons.Default.Lock
                SearchFilter.ALL -> Icons.Default.InsertDriveFile
            },
            contentDescription = null,
            tint = when (result.type) {
                SearchFilter.VIDEO -> CIPHERPrimary
                SearchFilter.MUSIC -> CIPHERSecondary
                SearchFilter.VAULT -> CIPHERTertiary
                SearchFilter.ALL -> CIPHEROnSurfaceVariant
            },
            modifier = Modifier.size(24.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                result.title,
                style = MaterialTheme.typography.bodyMedium,
                color = CIPHEROnSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                result.subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = CIPHEROnSurfaceVariant
            )
        }
    }
}
