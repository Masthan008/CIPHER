package com.cipher.media.ui.vault

import android.widget.Toast
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.security.IntruderCameraManager
import com.cipher.media.ui.components.*
import com.cipher.media.ui.stealth.StealthViewModel
import com.cipher.media.ui.theme.*
import com.cipher.media.ui.vault.components.PinPad
import java.security.MessageDigest
import kotlin.random.Random

/**
 * Vault auth: circuit pattern bg, shield pulse, PIN pad, biometric toggle.
 * Now with proper PIN hash verification, intruder selfie, and self-destruct.
 */
@Composable
fun VaultAuthScreen(
    onAuthenticated: () -> Unit,
    viewModel: VaultViewModel,
    stealthViewModel: StealthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var pin by remember { mutableStateOf("") }
    var authMode by remember { mutableStateOf("pin") }
    var error by remember { mutableStateOf(false) }
    var failedAttempts by remember { mutableIntStateOf(0) }
    var isLocked by remember { mutableStateOf(false) }

    val prefs = remember {
        context.getSharedPreferences("cipher_vault_prefs", android.content.Context.MODE_PRIVATE)
    }
    val storedPinHash = remember { prefs.getString("vault_pin_hash", null) }

    val intruderCamera = remember { IntruderCameraManager(context) }
    val maxAttempts = stealthViewModel.selfDestructAttempts

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

            // Glassmorphic PIN entry panel
            GlassmorphicCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.md),
                cornerRadius = 24.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = Spacing.lg, horizontal = Spacing.md)
                ) {
                    val statusText = when {
                        isLocked -> "Vault Locked — Too many attempts"
                        authMode == "biometric" -> "Touch fingerprint sensor"
                        error -> "Wrong PIN (${failedAttempts} failed)"
                        else -> "Enter PIN"
                    }

                    Text(
                        statusText,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (error || isLocked) CIPHERError else CIPHEROnSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(Spacing.xl))

                    // Animated PIN dots with spring scale
                    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
                        repeat(6) { index ->
                            val filled = index < pin.length
                            val dotScale by animateFloatAsState(
                                targetValue = if (filled) 1.3f else 1f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessHigh
                                ),
                                label = "dot_$index"
                            )
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .graphicsLayer { scaleX = dotScale; scaleY = dotScale }
                                    .background(
                                        if (filled) CIPHERPrimary else CIPHERDivider,
                                        CircleShape
                                    )
                            )
                        }
                    }

                    Spacer(Modifier.height(Spacing.xl))

                    // PIN pad (already has spring animations from PinPad upgrade)
                    PinPad(
                        onDigit = { digit ->
                            if (!isLocked && pin.length < 6) {
                                pin += digit
                                error = false
                            }
                        },
                        onBackspace = { if (pin.isNotEmpty()) pin = pin.dropLast(1) },
                        onSubmit = {
                            if (isLocked) return@PinPad
                            if (pin.length >= 4) {
                                val enteredHash = hashPin(pin)
                                if (enteredHash == storedPinHash) {
                                    failedAttempts = 0
                                    onAuthenticated()
                                } else {
                                    failedAttempts++
                                    error = true
                                    pin = ""
                                    if (stealthViewModel.isIntruderDetectionEnabled) {
                                        intruderCamera.captureIntruderPhoto { photoPath ->
                                            stealthViewModel.logIntruderAttempt(failedAttempts, photoPath)
                                        }
                                    }
                                    if (stealthViewModel.isSelfDestructEnabled && failedAttempts >= maxAttempts) {
                                        isLocked = true
                                        viewModel.wipeAllVaultData()
                                        Toast.makeText(context, "Vault wiped — self-destruct activated", Toast.LENGTH_LONG).show()
                                    }
                                }
                            } else {
                                error = true
                                pin = ""
                            }
                        }
                    )
                }
            }

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

/** SHA-256 hash of a PIN string */
private fun hashPin(pin: String): String {
    val digest = MessageDigest.getInstance("SHA-256")
    val hashBytes = digest.digest(pin.toByteArray())
    return hashBytes.joinToString("") { "%02x".format(it) }
}
