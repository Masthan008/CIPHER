package com.cipher.media.ui.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cipher.media.ui.theme.*

/**
 * Onboarding screen describing CIPHER features before prompting for authentication.
 */
@Composable
fun OnboardingScreen(
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CIPHERBackground)
    ) {
        // Decorative radial overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            CIPHERBackground.copy(alpha = 0.2f),
                            CIPHERBackground
                        )
                    )
                )
        )

        // Header - Skip Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, end = 32.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "SKIP",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                ),
                color = CIPHEROnSurfaceVariant,
                modifier = Modifier
                    .clickable { onSkipClick() }
                    .padding(8.dp)
            )
        }

        // Main Content (Asymmetric Editorial Layout)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // The Lens Illustration
            Box(
                modifier = Modifier
                    .padding(bottom = 64.dp)
                    .size(256.dp),
                contentAlignment = Alignment.Center
            ) {
                // Glow Effect
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(100.dp)
                        .background(CIPHERPrimary.copy(alpha = 0.2f), RoundedCornerShape(percent = 50))
                )

                // Outer Ring
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, CIPHERPrimary.copy(alpha = 0.1f), RoundedCornerShape(percent = 50))
                )

                // Main Shield Container
                Box(
                    modifier = Modifier
                        .size(192.dp)
                        .clip(RoundedCornerShape(32.dp))
                        .background(CIPHERSurfaceVariant)
                        .border(1.dp, CIPHEROnSurface.copy(alpha = 0.05f), RoundedCornerShape(32.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Shield,
                        contentDescription = "Shield",
                        modifier = Modifier.size(140.dp),
                        tint = CIPHERPrimary
                    )

                    // Overlay Play Icon
                    Box(
                        modifier = Modifier
                            .offset(y = 8.dp)
                            .size(64.dp)
                            .clip(RoundedCornerShape(percent = 50))
                            .background(CIPHERBackground.copy(alpha = 0.8f))
                            .border(1.dp, CIPHERPrimary.copy(alpha = 0.3f), RoundedCornerShape(percent = 50)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Play",
                            modifier = Modifier.size(40.dp),
                            tint = CIPHERPrimary
                        )
                    }
                }

                // Orbiting Tech Bits
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = (-8).dp, y = (-16).dp)
                        .background(CIPHERSurfaceBright, RoundedCornerShape(percent = 50))
                        .border(1.dp, CIPHEROutlineVariant.copy(alpha = 0.2f), RoundedCornerShape(percent = 50))
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = CIPHERTertiary
                    )
                    Text(
                        text = "ENCRYPTED",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp,
                            fontSize = 10.sp
                        ),
                        color = CIPHEROnSurfaceVariant
                    )
                }
            }

            // Typography Cluster
            Column(
                modifier = Modifier.widthIn(max = 320.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Welcome to ")
                        withStyle(style = SpanStyle(color = CIPHERPrimary)) {
                            append("CIPHER")
                        }
                    },
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-0.5).sp
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = buildAnnotatedString {
                        append("Military-grade privacy for your ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium, color = CIPHEROnSurface)) { append("videos") }
                        append(", ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium, color = CIPHEROnSurface)) { append("music") }
                        append(", and ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium, color = CIPHEROnSurface)) { append("files") }
                        append(".")
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = CIPHEROnSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Bottom Navigation Shell
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 32.dp, bottom = 48.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            // Dots Indicator
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(6.dp)
                        .width(32.dp)
                        .background(CIPHERPrimary, RoundedCornerShape(percent = 50))
                )
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(CIPHERSurfaceBright, RoundedCornerShape(percent = 50))
                )
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(CIPHERSurfaceBright, RoundedCornerShape(percent = 50))
                )
            }

            // CTA Action (Next)
            Button(
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .widthIn(max = 384.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(CIPHERPrimary, Color(0xFF693800))
                            )
                        )
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Next",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            ),
                            color = CIPHEROnPrimary
                        )
                        Box(
                            modifier = Modifier
                                .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                .padding(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = "Next",
                                tint = CIPHEROnPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}
