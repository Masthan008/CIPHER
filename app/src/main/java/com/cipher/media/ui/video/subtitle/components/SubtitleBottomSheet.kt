package com.cipher.media.ui.video.subtitle.components

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.video.subtitle.SubtitleViewModel
import com.cipher.media.ui.video.subtitle.model.*

/**
 * Bottom sheet for subtitle controls: load, sync, style with Free/Pro gating.
 * @param isPro Whether user has Pro subscription
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubtitleBottomSheet(
    viewModel: SubtitleViewModel,
    isPro: Boolean = false,
    onDismiss: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // File picker for subtitle files
    val subtitlePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            viewModel.loadSubtitle(it)
            Toast.makeText(context, "Subtitle loaded!", Toast.LENGTH_SHORT).show()
        }
    }

    // Sync limits based on tier
    val maxSync = if (isPro) SubtitleTierConfig.PRO_MAX_SYNC_MS else SubtitleTierConfig.FREE_MAX_SYNC_MS
    val minFontSize = if (isPro) SubtitleTierConfig.PRO_MIN_FONT_SIZE else SubtitleTierConfig.FREE_MIN_FONT_SIZE
    val maxFontSize = if (isPro) SubtitleTierConfig.PRO_MAX_FONT_SIZE else SubtitleTierConfig.FREE_MAX_FONT_SIZE
    val colorOptions = if (isPro) PRO_COLORS else FREE_COLORS

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1A1A2E)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        ) {
            // ── Header ──
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Subtitles",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                if (uiState.isLoaded) {
                    TextButton(onClick = { viewModel.clearSubtitle() }) {
                        Text("Remove", color = Color.Red.copy(alpha = 0.7f))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Load Subtitle ──
            OutlinedButton(
                onClick = { subtitlePicker.launch("*/*") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = CIPHERPrimary),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(CIPHERPrimary.copy(alpha = 0.5f))
                )
            ) {
                Icon(Icons.Default.FileOpen, null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(if (uiState.isLoaded) "Change Subtitle File" else "Load Subtitle File")
            }

            // Track info
            uiState.track?.let { track ->
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(CIPHERPrimary.copy(alpha = 0.1f))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Subtitles, null, tint = CIPHERPrimary, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(track.fileName, color = Color.White, fontSize = 13.sp)
                        Text(
                            "${track.format.name} • ${track.cueCount} cues",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // ═════════════════════════════════════════════
            //  ONLINE SUBTITLE SEARCH (PRO FEATURE)
            // ═════════════════════════════════════════════
            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
            Spacer(Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CloudDownload, null, tint = CIPHERPrimary, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Search Online", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Spacer(Modifier.weight(1f))
                if (!isPro) {
                    Text("PRO", color = CIPHERPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(CIPHERPrimary.copy(alpha = 0.15f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                } else {
                    val remaining = viewModel.getRemainingDownloads(true)
                    Text("$remaining left today", color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp)
                }
            }

            Spacer(Modifier.height(8.dp))

            // Search bar
            var searchQuery by remember { mutableStateOf("") }
            var selectedLang by remember { mutableStateOf("en") }
            val languages = listOf("en" to "English", "hi" to "Hindi", "te" to "Telugu", "ta" to "Tamil",
                "es" to "Spanish", "fr" to "French", "de" to "German", "ja" to "Japanese", "ko" to "Korean", "zh" to "Chinese")

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Movie or video name…", fontSize = 13.sp) },
                    modifier = Modifier.weight(1f).height(52.dp),
                    enabled = isPro,
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White.copy(alpha = 0.7f),
                        focusedBorderColor = CIPHERPrimary,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                        cursorColor = CIPHERPrimary,
                        disabledTextColor = Color.White.copy(alpha = 0.3f),
                        disabledBorderColor = Color.White.copy(alpha = 0.1f)
                    ),
                    textStyle = MaterialTheme.typography.bodySmall
                )
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (searchQuery.isNotBlank() && isPro) {
                            viewModel.searchOnline(searchQuery.trim(), selectedLang)
                        }
                    },
                    enabled = isPro && searchQuery.isNotBlank() && !uiState.isSearching,
                    colors = ButtonDefaults.buttonColors(containerColor = CIPHERPrimary),
                    modifier = Modifier.height(52.dp)
                ) {
                    if (uiState.isSearching) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(Icons.Default.Search, null, modifier = Modifier.size(20.dp))
                    }
                }
            }

            // Language selector
            if (isPro) {
                Spacer(Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(languages) { (code, label) ->
                        FilterChip(
                            selected = selectedLang == code,
                            onClick = { selectedLang = code },
                            label = { Text(label, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CIPHERPrimary.copy(alpha = 0.2f),
                                selectedLabelColor = CIPHERPrimary,
                                containerColor = Color.White.copy(alpha = 0.05f),
                                labelColor = Color.White.copy(alpha = 0.7f)
                            ),
                            modifier = Modifier.height(28.dp)
                        )
                    }
                }
            }

            // Search results
            if (uiState.onlineResults.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                Text("${uiState.onlineResults.size} results", color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp)
                Spacer(Modifier.height(6.dp))

                uiState.onlineResults.take(8).forEach { result ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.05f))
                            .clickable {
                                if (!uiState.isDownloading) {
                                    viewModel.downloadAndLoad(result, isPro)
                                }
                            }
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Subtitles, null, tint = CIPHERPrimary.copy(alpha = 0.7f), modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                result.fileName,
                                color = Color.White,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                            )
                            Text(
                                "${result.source} • ${result.language} • ⬇ ${result.downloadCount}",
                                color = Color.White.copy(alpha = 0.4f),
                                fontSize = 10.sp
                            )
                        }
                        Icon(Icons.Default.Download, null, tint = CIPHERPrimary, modifier = Modifier.size(18.dp))
                    }
                    Spacer(Modifier.height(4.dp))
                }
            }

            // Download status message
            uiState.downloadMessage?.let { message ->
                Spacer(Modifier.height(8.dp))
                Text(
                    message,
                    color = if (message.startsWith("✓")) Color(0xFF4CAF50) else Color(0xFFFF9800),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.White.copy(alpha = 0.05f))
                        .padding(8.dp)
                )
            }

            // Search error
            uiState.searchError?.let { error ->
                Spacer(Modifier.height(8.dp))
                Text(error, color = Color.Red.copy(alpha = 0.7f), fontSize = 12.sp)
            }

            // Downloading indicator
            if (uiState.isDownloading) {
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(16.dp), color = CIPHERPrimary, strokeWidth = 2.dp)
                    Spacer(Modifier.width(8.dp))
                    Text("Downloading subtitle…", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                }
            }

            // ── Only show settings if subtitle is loaded ──
            if (uiState.isLoaded) {
                Spacer(Modifier.height(20.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                Spacer(Modifier.height(16.dp))

                // ═══ SYNC ADJUSTMENT ═══
                SectionHeader("Sync Adjustment", isFree = true)
                Spacer(Modifier.height(8.dp))

                // Current sync value
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Offset: ${formatSyncOffset(uiState.syncOffsetMs)}",
                        color = if (uiState.syncOffsetMs == 0L) Color.White.copy(alpha = 0.6f) else CIPHERPrimary,
                        fontWeight = FontWeight.Medium
                    )
                    TextButton(onClick = { viewModel.resetSync() }) {
                        Text("Reset", color = CIPHERPrimary, fontSize = 12.sp)
                    }
                }

                Slider(
                    value = uiState.syncOffsetMs.toFloat(),
                    onValueChange = { viewModel.setSyncOffset(it.toLong()) },
                    valueRange = -maxSync.toFloat()..maxSync.toFloat(),
                    steps = if (isPro) ((maxSync * 2) / SubtitleTierConfig.PRO_SYNC_STEP_MS).toInt() - 1 else 19,
                    colors = SliderDefaults.colors(
                        thumbColor = CIPHERPrimary,
                        activeTrackColor = CIPHERPrimary,
                        inactiveTrackColor = Color.White.copy(alpha = 0.15f)
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("-${maxSync / 1000}s", color = Color.White.copy(alpha = 0.4f), fontSize = 11.sp)
                    Text("+${maxSync / 1000}s", color = Color.White.copy(alpha = 0.4f), fontSize = 11.sp)
                }

                Spacer(Modifier.height(20.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                Spacer(Modifier.height(16.dp))

                // ═══ FONT SIZE ═══
                SectionHeader("Font Size", isFree = true)
                Spacer(Modifier.height(8.dp))

                Text(
                    "${uiState.style.fontSize}%",
                    color = CIPHERPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Slider(
                    value = uiState.style.fontSize.toFloat(),
                    onValueChange = { viewModel.setFontSize(it.toInt()) },
                    valueRange = minFontSize.toFloat()..maxFontSize.toFloat(),
                    steps = (maxFontSize - minFontSize) / 25,
                    colors = SliderDefaults.colors(
                        thumbColor = CIPHERPrimary,
                        activeTrackColor = CIPHERPrimary,
                        inactiveTrackColor = Color.White.copy(alpha = 0.15f)
                    )
                )

                Spacer(Modifier.height(16.dp))

                // ═══ TEXT COLOR ═══
                SectionHeader("Text Color", isFree = true)
                Spacer(Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(colorOptions) { (color, name) ->
                        val isSelected = uiState.style.fontColor == color
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(color)
                                .then(
                                    if (isSelected) Modifier.border(2.dp, CIPHERPrimary, CircleShape)
                                    else Modifier.border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                                )
                                .clickable { viewModel.setFontColor(color) }
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))
                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                Spacer(Modifier.height(16.dp))

                // ═══ PRO FEATURES ═══

                // Position (Pro)
                SectionHeader("Position", isFree = false, isPro = isPro)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SubtitlePosition.entries.forEach { pos ->
                        val selected = uiState.style.position == pos
                        FilterChip(
                            selected = selected,
                            onClick = {
                                if (isPro || pos == SubtitlePosition.BOTTOM) {
                                    viewModel.setPosition(pos)
                                } else {
                                    Toast.makeText(context, "Upgrade to Pro for position options", Toast.LENGTH_SHORT).show()
                                }
                            },
                            label = { Text(pos.label, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CIPHERPrimary.copy(alpha = 0.2f),
                                selectedLabelColor = CIPHERPrimary,
                                labelColor = if (!isPro && pos != SubtitlePosition.BOTTOM)
                                    Color.White.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.7f)
                            ),
                            enabled = isPro || pos == SubtitlePosition.BOTTOM
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Background Opacity (Pro)
                SectionHeader("Background Opacity", isFree = false, isPro = isPro)
                Spacer(Modifier.height(8.dp))
                Slider(
                    value = uiState.style.backgroundOpacity,
                    onValueChange = {
                        if (isPro) viewModel.setBackgroundOpacity(it)
                        else Toast.makeText(context, "Upgrade to Pro", Toast.LENGTH_SHORT).show()
                    },
                    enabled = isPro,
                    colors = SliderDefaults.colors(
                        thumbColor = if (isPro) CIPHERPrimary else Color.Gray,
                        activeTrackColor = if (isPro) CIPHERPrimary else Color.Gray,
                        inactiveTrackColor = Color.White.copy(alpha = 0.15f)
                    )
                )

                Spacer(Modifier.height(16.dp))

                // Font Family (Pro)
                SectionHeader("Font Family", isFree = false, isPro = isPro)
                Spacer(Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(SubtitleFont.entries) { font ->
                        val selected = uiState.style.fontFamily == font
                        val locked = font.isPro && !isPro
                        FilterChip(
                            selected = selected,
                            onClick = {
                                if (!locked) viewModel.setFontFamily(font)
                                else Toast.makeText(context, "Upgrade to Pro", Toast.LENGTH_SHORT).show()
                            },
                            label = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(font.label, fontSize = 12.sp)
                                    if (locked) {
                                        Spacer(Modifier.width(4.dp))
                                        Icon(Icons.Default.Lock, null, modifier = Modifier.size(12.dp), tint = Color.White.copy(alpha = 0.4f))
                                    }
                                }
                            },
                            enabled = !locked,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = CIPHERPrimary.copy(alpha = 0.2f),
                                selectedLabelColor = CIPHERPrimary,
                                labelColor = if (locked) Color.White.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.7f)
                            )
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

/**
 * Section header with optional Pro badge.
 */
@Composable
private fun SectionHeader(title: String, isFree: Boolean, isPro: Boolean = true) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Spacer(Modifier.width(8.dp))
        if (isFree) {
            Text(
                "FREE",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Green,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Green.copy(alpha = 0.15f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        } else if (!isPro) {
            Text(
                "PRO",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = CIPHERPrimary,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(CIPHERPrimary.copy(alpha = 0.15f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

private fun formatSyncOffset(ms: Long): String {
    val sign = if (ms >= 0) "+" else ""
    val seconds = ms / 1000f
    return "${sign}%.1fs".format(seconds)
}
