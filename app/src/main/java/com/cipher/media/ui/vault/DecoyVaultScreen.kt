package com.cipher.media.ui.vault

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.components.*
import com.cipher.media.ui.theme.*

/**
 * Decoy vault: identical UI to the real vault but shows harmless fake content.
 * Opens when the decoy PIN is entered instead of the real PIN.
 * PRO TIER feature.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecoyVaultScreen(
    onBack: () -> Unit
) {
    // Fake items that look legitimate
    val fakeItems = remember {
        listOf(
            DecoyItem("🎬", "Birthday Party 2024.mp4", "245 MB"),
            DecoyItem("📸", "Family Photo.jpg", "4.2 MB"),
            DecoyItem("🎵", "Favourite Song.mp3", "8.1 MB"),
            DecoyItem("🎬", "Travel Vlog.mp4", "512 MB"),
            DecoyItem("📸", "Sunset Beach.jpg", "3.7 MB"),
            DecoyItem("📸", "Holiday Pics.jpg", "5.1 MB"),
        )
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
                    CIPHERIconButton(icon = Icons.Default.Add, onClick = { /* no-op for decoy */ })
                    CIPHERIconButton(icon = Icons.Default.CreateNewFolder, onClick = { /* no-op for decoy */ })
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CIPHERBackground)
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(Spacing.screenPadding),
            horizontalArrangement = Arrangement.spacedBy(Spacing.cardGap),
            verticalArrangement = Arrangement.spacedBy(Spacing.cardGap),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            itemsIndexed(fakeItems) { index, item ->
                StaggeredEntrance(index = index) {
                    MediaCard(
                        title = item.name,
                        subtitle = item.size,
                        thumbnailUri = null,
                        badge = item.icon,
                        onClick = { /* no-op, just show the list */ }
                    )
                }
            }
        }
    }
}

private data class DecoyItem(val icon: String, val name: String, val size: String)
