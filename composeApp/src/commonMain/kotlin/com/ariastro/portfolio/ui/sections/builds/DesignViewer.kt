package com.ariastro.portfolio.ui.sections.builds

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.domain.model.Project
import com.ariastro.portfolio.domain.model.Screenshot
import com.ariastro.portfolio.ui.components.rememberHover
import com.ariastro.portfolio.ui.components.screenshotResource
import com.ariastro.portfolio.ui.theme.PortfolioTheme
import com.ariastro.portfolio.ui.theme.accentColor
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/** Never smaller than this, or the phone UI inside stops being readable at all. */
private val MIN_CARD_WIDTH = 168.dp

/** Beyond this a single card starts to dominate the pane instead of reading as a gallery. */
private val MAX_CARD_WIDTH = 420.dp

/**
 * "Design" pane: the project's screenshots, captioned, in a horizontally scrolling gallery.
 *
 * Card width is derived from the pane it is given rather than hard-coded, so Design mode
 * actually cashes in the width it gains over the side-by-side layout. Tapping a card raises
 * the full-screen overlay, which is the only way to see this artwork at a useful size inside
 * a caged workspace — the caption travels with it there, and into the image's
 * content description, rather than sitting under every thumbnail.
 */
@Composable
internal fun DesignViewer(
    project: Project,
    compact: Boolean = false,
    onOpenScreenshot: (String) -> Unit,
) {
    val extra = PortfolioTheme.extra
    val accent = project.brand.accentColor
    val screenshots = remember(project) {
        project.screenshots.mapNotNull { shot ->
            screenshotResource(shot.assetId)?.let { shot to it }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = 10.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "layout preview",
                style = MaterialTheme.typography.labelMedium,
                color = accent,
            )
            Text(
                text = if (screenshots.size > 1) "scroll → · tap to expand" else "tap to expand",
                style = MaterialTheme.typography.labelSmall,
                color = extra.muted,
            )
        }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1f),
        ) {
            val cardWidth = cardWidthFor(
                paneWidth = maxWidth,
                paneHeight = maxHeight,
                compact = compact,
            )

            LazyRow(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                contentPadding = PaddingValues(horizontal = 2.dp),
            ) {
                itemsIndexed(
                    items = screenshots,
                    key = { _, (shot, _) -> "${project.id}-${shot.assetId}" },
                ) { _, (shot, resource) ->
                    ScreenshotCard(
                        screenshot = shot,
                        resource = resource,
                        width = cardWidth,
                        accent = accent,
                        projectTitle = project.title,
                        onClick = { onOpenScreenshot(shot.assetId) },
                    )
                }
            }
        }
    }
}

/**
 * Sizes a card to the pane.
 *
 * Two ceilings apply: leave roughly a third of the pane for the next card so the row reads as
 * scrollable, and keep a portrait image inside the pane's height. Whichever binds first wins.
 */
private fun cardWidthFor(paneWidth: Dp, paneHeight: Dp, compact: Boolean): Dp {
    val imageBudget = paneHeight.coerceAtLeast(minimumValue = 180.dp)
    val byWidth = paneWidth * if (compact) 0.82f else 0.62f
    // 0.52 suits the portrait assets here (phone captures and store artwork alike).
    val byHeight = imageBudget * 0.52f
    return minOf(byWidth, byHeight).coerceIn(
        minimumValue = MIN_CARD_WIDTH,
        maximumValue = MAX_CARD_WIDTH,
    )
}

/** One gallery card: the framed image on its own; the caption lives in the overlay. */
@Composable
private fun ScreenshotCard(
    screenshot: Screenshot,
    resource: DrawableResource,
    width: Dp,
    accent: Color,
    projectTitle: String,
    onClick: () -> Unit,
) {
    val scheme = MaterialTheme.colorScheme
    val hover = rememberHover()
    val painter = painterResource(resource = resource)
    val intrinsic = painter.intrinsicSize
    val aspect = if (
        intrinsic.width.isFinite() &&
        intrinsic.height.isFinite() &&
        intrinsic.width > 0f &&
        intrinsic.height > 0f
    ) {
        intrinsic.width / intrinsic.height
    } else {
        9f / 19.5f
    }

    Column(
        modifier = Modifier
            .width(width = width)
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(color = scheme.surface)
            .border(
                width = 1.dp,
                color = if (hover.isHovered) accent.copy(alpha = 0.6f) else scheme.outline,
                shape = RoundedCornerShape(size = 12.dp),
            )
            .hoverable(interactionSource = hover.interactionSource)
            .clickable(
                interactionSource = hover.interactionSource,
                indication = null,
                onClickLabel = "Expand ${screenshot.caption}",
                role = Role.Button,
                onClick = onClick,
            ),
    ) {
        Image(
            painter = painter,
            contentDescription = "$projectTitle — ${screenshot.caption}",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = aspect)
                .clip(shape = RoundedCornerShape(size = 12.dp)),
        )
    }
}
