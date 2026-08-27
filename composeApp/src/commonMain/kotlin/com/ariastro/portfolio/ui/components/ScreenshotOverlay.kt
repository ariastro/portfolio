package com.ariastro.portfolio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.domain.model.Screenshot
import com.ariastro.portfolio.ui.theme.PortfolioTheme
import org.jetbrains.compose.resources.painterResource

/**
 * Full-screen viewer for a project screenshot.
 *
 * The builds workspace is a fixed-height cage, so inside it artwork can only ever be thumbnail
 * sized. This escapes the cage: it draws over the whole page, letting the screenshots — the
 * strongest evidence of shipped work on the site — be read at a useful size.
 *
 * Clicking the backdrop dismisses. The image itself swallows clicks so a stray click on it
 * does not close the overlay.
 */
@Composable
fun ScreenshotOverlay(
    screenshot: Screenshot,
    projectTitle: String,
    accent: Color,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val resource = screenshotResource(screenshot.assetId) ?: return
    val scrimAlpha = if (extra.isDark) 0.88f else 0.75f

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = scrimAlpha))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClickLabel = "Close screenshot",
                role = Role.Button,
                onClick = onDismiss,
            )
            .padding(all = 24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 520.dp)
                // Absorb clicks so only the backdrop dismisses.
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    enabled = true,
                    onClick = {},
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 14.dp),
        ) {
            Image(
                painter = painterResource(resource = resource),
                contentDescription = "$projectTitle — ${screenshot.caption}",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = 16.dp))
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.14f),
                        shape = RoundedCornerShape(size = 16.dp),
                    ),
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = screenshot.caption,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                    )
                    Text(
                        text = projectTitle,
                        style = MaterialTheme.typography.labelMedium,
                        color = accent,
                    )
                }
                CloseChip(onClick = onDismiss)
            }
        }
    }
}

/** `esc`-style dismiss affordance, so closing is discoverable without guessing at the backdrop. */
@Composable
private fun CloseChip(onClick: () -> Unit) {
    val hover = rememberHover()
    val border = if (hover.isHovered) Color.White.copy(alpha = 0.5f) else Color.White.copy(alpha = 0.2f)

    Text(
        text = "close ✕",
        style = MaterialTheme.typography.labelMedium,
        color = Color.White.copy(alpha = if (hover.isHovered) 1f else 0.75f),
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 6.dp))
            .hoverable(interactionSource = hover.interactionSource)
            .clickable(
                interactionSource = hover.interactionSource,
                indication = null,
                onClickLabel = "Close screenshot",
                role = Role.Button,
                onClick = onClick,
            )
            .border(width = 1.dp, color = border, shape = RoundedCornerShape(size = 6.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
    )
}
