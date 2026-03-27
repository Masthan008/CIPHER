package com.cipher.media.ui.auth

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cipher.media.ui.components.CIPHERButton
import com.cipher.media.ui.theme.*
import kotlinx.coroutines.launch

data class OnboardingPage(
    val icon: ImageVector,
    val title: String,
    val description: String
)

val onboardingPages = listOf(
    OnboardingPage(Icons.Default.Shield, "Welcome to CIPHER", "Your privacy-first media player"),
    OnboardingPage(Icons.Default.Lock, "Military-Grade Privacy", "Keep your private files safe with AES-256 encryption"),
    OnboardingPage(Icons.Default.LibraryMusic, "All Your Media", "Play video & music with a premium experience"),
    OnboardingPage(Icons.Default.Fingerprint, "Let's Set Up", "Enable biometric lock to protect your vault")
)

/**
 * 4-page horizontal onboarding with animated icons, dot indicators, skip/next.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CIPHERBackground)
    ) {
        // Skip button
        TextButton(
            onClick = onSkipClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(Spacing.md)
        ) {
            Text("Skip", color = CIPHEROnSurfaceVariant)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(0.2f))

            // Pager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(0.6f)
            ) { page ->
                OnboardingPageContent(onboardingPages[page])
            }

            // Dot indicators
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                modifier = Modifier.padding(vertical = Spacing.lg)
            ) {
                repeat(onboardingPages.size) { index ->
                    val isActive = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .size(if (isActive) 24.dp else 8.dp, 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (isActive) CIPHERPrimary else CIPHERDivider
                            )
                    )
                }
            }

            // Next / Get Started button
            CIPHERButton(
                text = if (pagerState.currentPage == onboardingPages.lastIndex) "Get Started" else "Next",
                onClick = {
                    if (pagerState.currentPage == onboardingPages.lastIndex) {
                        onNextClick()
                    } else {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.xxl)
            )

            Spacer(Modifier.weight(0.1f))
        }
    }
}

@Composable
private fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.xl),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Animated icon with gradient glow
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            CIPHERPrimary.copy(alpha = 0.15f),
                            CIPHERBackground
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                page.icon, null,
                modifier = Modifier.size(80.dp),
                tint = CIPHERPrimary
            )
        }

        Spacer(Modifier.height(Spacing.xl))

        Text(
            page.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = CIPHEROnSurface,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(Spacing.md))

        Text(
            page.description,
            style = MaterialTheme.typography.bodyLarge,
            color = CIPHEROnSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}
