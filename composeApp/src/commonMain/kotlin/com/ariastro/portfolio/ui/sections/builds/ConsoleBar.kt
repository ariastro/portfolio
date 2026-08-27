package com.ariastro.portfolio.ui.sections.builds

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.domain.model.LinkType
import com.ariastro.portfolio.domain.model.Project
import com.ariastro.portfolio.ui.components.rememberHover
import com.ariastro.portfolio.ui.theme.PortfolioTheme

/** Human-readable destination for the Run action, derived from the domain [LinkType]. */
private val LinkType.destination: String
    get() = when (this) {
        LinkType.PLAY_STORE -> "Play Store"
        LinkType.GITHUB -> "GitHub"
        LinkType.WEBSITE -> "Website"
        LinkType.NONE -> ""
    }

/**
 * IDE status bar: ready state + project position, prev/next project navigation, and a Run action
 * that opens the project's external link. Prev/next solve discoverability of projects 2..N.
 */
@Composable
internal fun ConsoleBar(
    project: Project,
    selectedIndex: Int,
    projectCount: Int,
    onSelectProject: (Int) -> Unit,
    onOpenLink: (String) -> Unit,
    compact: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val extra = PortfolioTheme.extra
    val position = "${project.index} / ${projectCount.toString().padStart(2, '0')}"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = if (compact) 12.dp else 14.dp,
                vertical = 10.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "> Ready · $position",
            style = MaterialTheme.typography.labelLarge,
            color = extra.accent,
            maxLines = 1,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            ConsoleNavButton(
                glyph = "←",
                contentDescription = "Previous project",
                enabled = selectedIndex > 0,
                onClick = { onSelectProject(selectedIndex - 1) },
            )
            ConsoleNavButton(
                glyph = "→",
                contentDescription = "Next project",
                enabled = selectedIndex < projectCount - 1,
                onClick = { onSelectProject(selectedIndex + 1) },
            )

            val link = project.link
            if (link != null) {
                val destination = project.linkType.destination
                val runText = if (destination.isNotEmpty()) "Run ↗ $destination" else "Run ↗"
                RunButton(text = runText, onClick = { onOpenLink(link) })
            }
        }
    }
}

@Composable
private fun ConsoleNavButton(
    glyph: String,
    contentDescription: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val hover = rememberHover()
    val background = when {
        !enabled -> Color.Transparent
        hover.isHovered -> extra.accentSoft
        else -> Color.Transparent
    }
    val color = if (enabled) scheme.onSurface else extra.faint

    Text(
        text = glyph,
        style = MaterialTheme.typography.labelLarge,
        color = color,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 6.dp))
            .hoverable(interactionSource = hover.interactionSource)
            .clickable(
                interactionSource = hover.interactionSource,
                indication = null,
                enabled = enabled,
                onClickLabel = contentDescription,
                role = Role.Button,
                onClick = onClick,
            )
            .background(color = background)
            .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 6.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
    )
}

@Composable
private fun RunButton(
    text: String,
    onClick: () -> Unit,
) {
    val extra = PortfolioTheme.extra
    val accent = extra.accent
    val hover = rememberHover()
    val background = if (hover.isHovered) accent.copy(alpha = 0.18f) else extra.accentSoft

    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = accent,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 6.dp))
            .hoverable(interactionSource = hover.interactionSource)
            .clickable(
                interactionSource = hover.interactionSource,
                indication = null,
                onClickLabel = "Open project link",
                role = Role.Button,
                onClick = onClick,
            )
            .background(color = background)
            .border(
                width = 1.dp,
                color = accent.copy(alpha = 0.5f),
                shape = RoundedCornerShape(size = 6.dp),
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
    )
}
