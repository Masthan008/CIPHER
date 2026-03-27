package com.cipher.media.ui.premium

import android.app.Activity
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cipher.media.ui.components.*
import com.cipher.media.ui.theme.*

/**
 * Premium screen: gradient hero, 3 plan cards with badges, subscribe/remove-ads, restore.
 */
@Composable
fun PremiumScreen(
    onBack: () -> Unit,
    viewModel: PremiumViewModel = hiltViewModel()
) {
    val selectedPlan by viewModel.selectedPlan.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity

    Scaffold(containerColor = CIPHERBackground) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Gradient hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(GradientPremiumStart, GradientPremiumEnd, CIPHERBackground)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Back button
                CIPHERIconButton(
                    icon = Icons.Default.ArrowBack,
                    onClick = onBack,
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .padding(Spacing.sm)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Star, null, tint = Color.White, modifier = Modifier.size(48.dp))
                    Spacer(Modifier.height(Spacing.md))
                    Text(
                        "Unlock Full Power",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.height(Spacing.xs))
                    Text(
                        "Remove ads • Premium features • Unlimited vault",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(Modifier.height(Spacing.md))

            // Plan cards
            Column(
                modifier = Modifier.padding(horizontal = Spacing.md),
                verticalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                PlanCard(
                    title = "Pro Monthly",
                    price = "₹99/month",
                    selected = selectedPlan == 0,
                    onClick = { viewModel.selectPlan(0) }
                )
                PlanCard(
                    title = "Pro Yearly",
                    price = "₹499/year",
                    badge = "SAVE 58%",
                    selected = selectedPlan == 1,
                    onClick = { viewModel.selectPlan(1) }
                )
                PlanCard(
                    title = "Power Yearly",
                    price = "₹999/year",
                    badge = "BEST VALUE",
                    badgeColor = CIPHERTertiary,
                    selected = selectedPlan == 2,
                    onClick = { viewModel.selectPlan(2) }
                )
            }

            Spacer(Modifier.height(Spacing.lg))

            // Subscribe button
            CIPHERButton(
                text = "Subscribe Now",
                onClick = { activity?.let { viewModel.subscribe(it) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.md)
            )

            Spacer(Modifier.height(Spacing.sm))

            // Remove ads one-time
            CIPHEROutlinedButton(
                text = "Just Remove Ads — ₹199 one-time",
                onClick = { activity?.let { viewModel.removeAds(it) } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.md)
            )

            Spacer(Modifier.height(Spacing.md))

            // Restore
            TextButton(
                onClick = { viewModel.restorePurchases() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Restore Purchases", color = CIPHEROnSurfaceVariant)
            }

            Spacer(Modifier.height(Spacing.xxl))
        }
    }
}

@Composable
private fun PlanCard(
    title: String,
    price: String,
    badge: String? = null,
    badgeColor: Color = CIPHERPrimary,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .then(
                if (selected) Modifier.border(2.dp, CIPHERPrimary, RoundedCornerShape(Corners.large))
                else Modifier
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) CIPHERPrimaryContainer else CIPHERSurface
        ),
        shape = RoundedCornerShape(Corners.large)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = CIPHEROnSurface)
                    if (badge != null) {
                        Spacer(Modifier.width(Spacing.sm))
                        Surface(
                            shape = RoundedCornerShape(Corners.small),
                            color = badgeColor
                        ) {
                            Text(
                                badge,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = CIPHEROnPrimary
                            )
                        }
                    }
                }
                Text(price, style = MaterialTheme.typography.bodyMedium, color = CIPHEROnSurfaceVariant)
            }

            RadioButton(
                selected = selected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = CIPHERPrimary)
            )
        }
    }
}
