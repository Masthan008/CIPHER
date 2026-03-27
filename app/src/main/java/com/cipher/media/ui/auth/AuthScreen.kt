package com.cipher.media.ui.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*

/**
 * Vault Authentication (Login) screen. Requires a 6-digit PIN to proceed.
 */
@Composable
fun AuthScreen(
    onAuthSuccess: () -> Unit
) {
    // Current PIN state (max 6 digits)
    var pin by remember { mutableStateOf("") }
    val maxPinLength = 6
    val currentLength = pin.length

    // Automatically trigger login randomly (simulation for Phase 1.5)
    LaunchedEffect(currentLength) {
        if (currentLength == maxPinLength) {
            // For Phase 1, automatically accept any 6-digit PIN and route
            onAuthSuccess()
            pin = "" // clear for next time
        }
    }

    val onNumberClick: (Int) -> Unit = { digit ->
        if (pin.length < maxPinLength) {
            pin += digit
        }
    }
    val onBackspaceClick: () -> Unit = {
        if (pin.isNotEmpty()) {
            pin = pin.dropLast(1)
        }
    }

    Scaffold(
        containerColor = CIPHERBackground,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .height(64.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                        tint = CIPHERPrimary
                    )
                    Text(
                        text = "CIPHER",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.5).sp
                        ),
                        color = CIPHERPrimary
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Auth Lens Section
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(CIPHERSurfaceVariant, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Shield,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = CIPHERPrimary
                )
                // Asymmetric decoration
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 8.dp, y = (-8).dp)
                        .size(16.dp)
                        .background(CIPHERTertiaryContainer, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Enter Vault PIN",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    ),
                    color = CIPHEROnSurface
                )
                Text(
                    text = "6-DIGIT ENCRYPTION KEY REQUIRED",
                    style = MaterialTheme.typography.labelSmall.copy(
                        letterSpacing = 1.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = CIPHEROnSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // PIN Indicators
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 0 until maxPinLength) {
                    val isActive = i == currentLength
                    val isFilled = i < currentLength
                    
                    val color by animateColorAsState(
                        targetValue = when {
                            isActive || isFilled -> CIPHERPrimary
                            else -> CIPHERSurfaceBright
                        },
                        label = "pin_color"
                    )

                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .background(color, CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Numpad Grid
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.width(280.dp)
            ) {
                // Rows 1-3
                for (row in 0..2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (col in 1..3) {
                            val digit = row * 3 + col
                            NumPadButton(
                                digit = digit.toString(),
                                onClick = { onNumberClick(digit) }
                            )
                        }
                    }
                }
                // Row 4
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Biometric / Filler
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clickable { }, // Stub for biometric
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Fingerprint,
                            contentDescription = "Biometric Login",
                            tint = CIPHEROnSurfaceVariant,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    NumPadButton(
                        digit = "0",
                        onClick = { onNumberClick(0) }
                    )

                    // Backspace
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clickable { onBackspaceClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Backspace,
                            contentDescription = "Delete",
                            tint = CIPHEROnSurfaceVariant,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Transactional Actions (Biometric & Forgot Pin)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "USE BIOMETRIC",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp
                    ),
                    color = CIPHERPrimary,
                    modifier = Modifier.clickable { onAuthSuccess() } // Fast-track login
                )
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(48.dp)
                        .background(CIPHEROutlineVariant.copy(alpha = 0.3f), RoundedCornerShape(percent = 50))
                )
                Text(
                    text = "Forgot PIN?",
                    style = MaterialTheme.typography.labelMedium,
                    color = CIPHEROnSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
}

@Composable
private fun NumPadButton(
    digit: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(CIPHERSurfaceVariant)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = digit,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = CIPHEROnSurface
        )
    }
}
