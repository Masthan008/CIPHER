package com.cipher.media.ui.vault

import android.app.Activity
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.data.model.VaultItem
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.vault.components.ImportDialog

/**
 * Vault file browser with folder management and import FAB.
 * FLAG_SECURE prevents screenshots.
 */
@Composable
fun VaultBrowserScreen(
    viewModel: VaultViewModel = hiltViewModel(),
    onItemClick: (VaultItem) -> Unit,
    onBack: () -> Unit
) {
    val items by viewModel.filteredItems.collectAsState()
    val folders by viewModel.folders.collectAsState()
    val selectedFolderId by viewModel.selectedFolderId.collectAsState()
    val isImporting by viewModel.isImporting.collectAsState()
    val itemCount by viewModel.itemCount.collectAsState()

    var showImportDialog by remember { mutableStateOf(false) }
    var showNewFolderDialog by remember { mutableStateOf(false) }
    var newFolderName by remember { mutableStateOf("") }

    val context = LocalContext.current

    // FLAG_SECURE
    DisposableEffect(Unit) {
        (context as? Activity)?.window?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        onDispose {
            (context as? Activity)?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            viewModel.cleanupTemp()
        }
    }

    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, "Back", tint = CIPHEROnSurface)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Vault",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = CIPHEROnSurface
                    )
                    Text(
                        text = "$itemCount encrypted files",
                        style = MaterialTheme.typography.bodySmall,
                        color = CIPHEROnSurfaceVariant
                    )
                }
                IconButton(onClick = { showNewFolderDialog = true }) {
                    Icon(Icons.Default.CreateNewFolder, "New Folder", tint = CIPHEROnSurfaceVariant)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showImportDialog = true },
                containerColor = CIPHERPrimary,
                contentColor = CIPHEROnPrimary
            ) {
                Icon(Icons.Default.Add, "Import")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Folder chips
            if (folders.isNotEmpty()) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedFolderId == null,
                        onClick = { viewModel.selectFolder(null) },
                        label = { Text("All") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CIPHERPrimary,
                            selectedLabelColor = CIPHEROnPrimary
                        )
                    )
                    folders.forEach { folder ->
                        FilterChip(
                            selected = selectedFolderId == folder.id,
                            onClick = { viewModel.selectFolder(folder.id) },
                            label = { Text(folder.name) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CIPHERPrimary,
                                selectedLabelColor = CIPHEROnPrimary
                            )
                        )
                    }
                }
            }

            // Loading indicator
            if (isImporting) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = CIPHERPrimary,
                    trackColor = CIPHERSurfaceVariant
                )
            }

            // Items grid
            if (items.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Lock, null,
                            modifier = Modifier.size(80.dp),
                            tint = CIPHEROnSurfaceVariant.copy(alpha = 0.3f)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text("Vault is empty", style = MaterialTheme.typography.titleMedium, color = CIPHEROnSurfaceVariant)
                        Text("Tap + to import files", style = MaterialTheme.typography.bodySmall, color = CIPHEROnSurfaceVariant.copy(alpha = 0.6f))
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items, key = { it.id }) { item ->
                        VaultItemCard(item = item, onClick = { onItemClick(item) })
                    }
                }
            }
        }
    }

    // Import dialog
    if (showImportDialog) {
        ImportDialog(
            onDismiss = { showImportDialog = false },
            onImport = { uri, delete -> viewModel.importFile(uri, delete) }
        )
    }

    // New folder dialog
    if (showNewFolderDialog) {
        AlertDialog(
            onDismissRequest = { showNewFolderDialog = false },
            containerColor = CIPHERSurface,
            title = { Text("New Folder", color = CIPHEROnSurface, fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = newFolderName,
                    onValueChange = { newFolderName = it },
                    label = { Text("Folder name") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newFolderName.isNotBlank()) {
                        viewModel.createFolder(newFolderName.trim())
                        newFolderName = ""
                        showNewFolderDialog = false
                    }
                }) { Text("CREATE", color = CIPHERPrimary) }
            },
            dismissButton = {
                TextButton(onClick = { showNewFolderDialog = false }) {
                    Text("CANCEL", color = CIPHEROnSurfaceVariant)
                }
            }
        )
    }
}

@Composable
private fun VaultItemCard(item: VaultItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CIPHERSurface)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Encrypted placeholder (no unencrypted thumbnails!)
            Icon(
                imageVector = when (item.fileType) {
                    VaultItem.FileType.IMAGE -> Icons.Default.Image
                    VaultItem.FileType.VIDEO -> Icons.Default.VideoFile
                    VaultItem.FileType.AUDIO -> Icons.Default.AudioFile
                    else -> Icons.Default.InsertDriveFile
                },
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = CIPHERPrimary.copy(alpha = 0.6f)
            )

            // Lock badge
            Icon(
                Icons.Default.Lock, null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(16.dp),
                tint = CIPHERTertiary
            )

            // File info at bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(CIPHERBackground.copy(alpha = 0.7f))
                    .padding(8.dp)
            ) {
                Text(
                    text = item.originalName,
                    style = MaterialTheme.typography.labelSmall,
                    color = CIPHEROnSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.formattedSize,
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = CIPHEROnSurfaceVariant
                )
            }
        }
    }
}
