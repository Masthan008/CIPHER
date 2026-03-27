package com.cipher.media.ui.search

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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.ui.components.*
import com.cipher.media.ui.theme.*

/**
 * Search screen: animated search field, filter chips, staggered results.
 */
@Composable
fun SearchScreen(
    onBack: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val query by viewModel.query.collectAsState()
    val results by viewModel.results.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val selectedFilter by viewModel.filter.collectAsState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    Scaffold(containerColor = CIPHERBackground) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Search bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.md)
                    .windowInsetsPadding(WindowInsets.statusBars),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CIPHERIconButton(icon = Icons.Default.ArrowBack, onClick = onBack)
                TextField(
                    value = query,
                    onValueChange = { viewModel.updateQuery(it) },
                    placeholder = { Text("Search files…", color = CIPHEROnSurfaceVariant) },
                    modifier = Modifier.weight(1f).focusRequester(focusRequester),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = CIPHERSurface,
                        unfocusedContainerColor = CIPHERSurface,
                        cursorColor = CIPHERPrimary,
                        focusedIndicatorColor = CIPHERPrimary,
                        unfocusedIndicatorColor = CIPHERDivider,
                        focusedTextColor = CIPHEROnSurface,
                        unfocusedTextColor = CIPHEROnSurface
                    ),
                    shape = RoundedCornerShape(Corners.large),
                    singleLine = true,
                    trailingIcon = {
                        if (query.isNotEmpty()) {
                            CIPHERIconButton(icon = Icons.Default.Clear, onClick = { viewModel.updateQuery("") })
                        }
                    }
                )
            }

            // Filter chips
            Row(
                modifier = Modifier.padding(horizontal = Spacing.md),
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                SearchFilter.entries.forEachIndexed { _, filter ->
                    FilterChip(
                        selected = selectedFilter == filter,
                        onClick = { viewModel.setFilter(filter) },
                        label = { Text(filter.name.lowercase().replaceFirstChar { it.uppercase() }) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CIPHERPrimary,
                            selectedLabelColor = CIPHEROnPrimary,
                            containerColor = CIPHERSurface,
                            labelColor = CIPHEROnSurfaceVariant
                        )
                    )
                }
            }

            Spacer(Modifier.height(Spacing.md))

            when {
                query.length < 2 -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Type at least 2 characters to search", style = MaterialTheme.typography.bodyMedium, color = CIPHEROnSurfaceVariant)
                }

                isSearching -> ShimmerGrid(columns = 1, count = 5)

                results.isEmpty() -> EmptyState(
                    icon = Icons.Default.SearchOff,
                    title = "No results found",
                    subtitle = "Try a different search term"
                )

                else -> LazyColumn(
                    contentPadding = PaddingValues(horizontal = Spacing.md),
                    verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
                ) {
                    itemsIndexed(results) { index, result ->
                        StaggeredEntrance(index = index) {
                            MediaListItem(
                                title = result.title,
                                subtitle = result.subtitle,
                                thumbnailUri = null,
                                duration = null,
                                onClick = { }
                            )
                        }
                    }
                }
            }
        }
    }
}
