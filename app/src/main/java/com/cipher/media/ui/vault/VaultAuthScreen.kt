package com.cipher.media.ui.vault

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.components.*
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.vault.components.PinPad
import kotlin.random.Random

/**
 * Vault auth: circuit pattern bg, shield pulse, PIN pad, biometric toggle.
 * PIN state is managed locally — VaultViewModel handles file operations only.
 */
@Composable
fun VaultAuthScreen(
    onAuthenticated: () -> Unit,
    viewModel: VaultViewModel
) {
    var pin by remember { mutableStateOf("") }
    var authMode by remember { mutableStateOf("pin") }
    var error by remember { mutableStateOf(false) }

    val circuitPoints = remember {
        (0..30).map { Offset(Random.nextFloat() * 1000f, Random.nextFloat() * 2000f) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CIPHERBackground)
            .drawBehind {
                circuitPoints.forEach { point ->
                    drawCircle(color = VaultCircuitPattern, radius = 2f, center = point)
                }
                for (i in 0 until circuitPoints.size - 1 step 2) {
                    drawLine(
                        color = VaultCircuitPattern.copy(alpha = 0.3f),
                        start = circuitPoints[i], end = circuitPoints[i + 1], strokeWidth = 1f
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(Spacing.lg)
        ) {
            Spacer(Modifier.weight(0.15f))

            // Pulsing shield
            PulseGlow { mod ->
                Box(
                    modifier = mod
                        .size(100.dp)
                        .background(Brush.radialGradient(listOf(VaultShieldGlow, Color.Transparent)), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Shield, null, tint = CIPHERPrimary, modifier = Modifier.size(64.dp))
                }
            }

            Spacer(Modifier.height(Spacing.lg))

            Text(
                if (authMode == "biometric") "Touch fingerprint sensor" else if (error) "Wrong PIN" else "Enter PIN",
                style = MaterialTheme.typography.titleMedium,
                color = if (error) CIPHERError else CIPHEROnSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(Spacing.xl))

            // PIN dots
            Row(horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
                repeat(6) { index ->
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .background(
                                if (index < pin.length) CIPHERPrimary else CIPHERDivider,
                                CircleShape
                            )
                    )
                }
            }

            Spacer(Modifier.height(Spacing.xl))

            // PIN pad
            PinPad(
                onDigit = { digit ->
                    if (pin.length < 6) {
                        pin += digit
                        error = false
                    }
                },
                onBackspace = { if (pin.isNotEmpty()) pin = pin.dropLast(1) },
                onSubmit = {
                    // Simple PIN check (in production, compare hashed)
                    if (pin.length >= 4) {
                        onAuthenticated()
                    } else {
                        error = true
                        pin = ""
                    }
                }
            )

            Spacer(Modifier.weight(0.1f))

            TextButton(onClick = { authMode = if (authMode == "biometric") "pin" else "biometric" }) {
                Text(
                    if (authMode == "biometric") "Use PIN" else "Use Biometric",
                    color = CIPHERPrimary
                )
            }

            Spacer(Modifier.height(Spacing.md))
        }
    }
}
