package com.cipher.media.ui.vault

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.ui.components.*
import com.cipher.media.ui.theme.*

/**
 * Vault browser: shield header, lock badges, import via gallery picker.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VaultBrowserScreen(
    onFileClick: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: VaultViewModel = hiltViewModel()
) {
    val vaultItems by viewModel.vaultItems.collectAsState()
    val isImporting by viewModel.isImporting.collectAsState()
    val context = LocalContext.current

    // File picker launcher — picks multiple files from gallery
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        if (uris.isNotEmpty()) {
            uris.forEach { uri ->
                viewModel.importFile(uri)
            }
            Toast.makeText(context, "Importing ${uris.size} file(s)…", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Shield, null, tint = CIPHERPrimary, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(Spacing.sm))
                        Text("Vault", fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
                    }
                },
                navigationIcon = { CIPHERIconButton(icon = Icons.Default.ArrowBack, onClick = onBack) },
                actions = {
                    CIPHERIconButton(icon = Icons.Default.Add, onClick = {
                        filePickerLauncher.launch("*/*") // Accept all file types
                    })
                    CIPHERIconButton(icon = Icons.Default.CreateNewFolder, onClick = { viewModel.createFolder("New Folder") })
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CIPHERBackground)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (isImporting) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = CIPHERPrimary
                )
            }

            when {
                vaultItems.isEmpty() -> EmptyState(
                    icon = Icons.Default.Lock,
                    title = "Your vault is empty",
                    subtitle = "Import files to keep them encrypted",
                    actionText = "Import from gallery",
                    onAction = { filePickerLauncher.launch("*/*") }
                )

                else -> LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(Spacing.screenPadding),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.cardGap),
                    verticalArrangement = Arrangement.spacedBy(Spacing.cardGap)
                ) {
                    itemsIndexed(vaultItems) { index, item ->
                        StaggeredEntrance(index = index) {
                            MediaCard(
                                title = item.originalName,
                                subtitle = item.formattedSize,
                                thumbnailUri = null,
                                badge = "🔐",
                                onClick = { onFileClick(item.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
