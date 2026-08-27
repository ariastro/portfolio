package com.ariastro.portfolio.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.domain.model.Experience
import com.ariastro.portfolio.domain.model.Profile
import com.ariastro.portfolio.ui.components.Shell
import com.ariastro.portfolio.ui.components.rememberHover
import com.ariastro.portfolio.ui.theme.ExtraColors
import com.ariastro.portfolio.ui.theme.PortfolioTheme

/** How many recent roles stay visible before the log is expanded. */
private const val RECENT_COUNT = 4

/** Width of the commit-graph rail to the left of every entry. */
private val RAIL_WIDTH = 22.dp

/** Distance from an entry's top edge to the centre of its commit node. */
private val NODE_CENTER_Y = 7.dp

/** Radius of a commit node on the rail. */
private val NODE_RADIUS = 4.dp

/** Space below an entry's content, spanned by the rail so the graph reads as one line. */
private val ENTRY_GAP = 26.dp

/**
 * Colour of the graph line. Derived from [ExtraColors.faint] rather than the theme outline:
 * the outline is tuned for panel borders and disappears against the light theme's panel.
 */
private val railLine: Color
    @Composable get() = PortfolioTheme.extra.faint.copy(alpha = 0.4f)

/**
 * Stateless career section, rendered as a git commit graph: one node per role on a shared
 * rail, newest at the top. The graph carries the "history" idea visually, which lets the text
 * itself stay plain and scannable — period, then role, then supporting detail.
 */
@Composable
fun ExperienceSection(
    profile: Profile,
    showAll: Boolean,
    onToggleShowAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val extra = PortfolioTheme.extra
    val hiddenCount = (profile.experiences.size - RECENT_COUNT).coerceAtLeast(0)
    val visible =
        if (showAll || hiddenCount == 0) profile.experiences
        else profile.experiences.take(n = RECENT_COUNT)

    Shell(
        modifier = modifier.padding(vertical = 24.dp),
    ) {
        Panel(
            title = "experience.log",
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "$ git log --career",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.accent,
                )
                Spacer(modifier = Modifier.height(height = 20.dp))

                visible.forEachIndexed { index, experience ->
                    CommitEntry(
                        experience = experience,
                        isHead = index == 0,
                        // The rail only terminates when there is nothing left to reveal.
                        isLast = index == visible.lastIndex && hiddenCount == 0,
                    )
                }

                if (hiddenCount > 0) {
                    LogToggle(
                        showAll = showAll,
                        hiddenCount = hiddenCount,
                        onToggle = onToggleShowAll,
                    )
                }
            }
        }
    }
}

/**
 * One role on the graph.
 *
 * The rail is drawn behind the whole entry — whose height already includes [ENTRY_GAP] — so
 * consecutive entries join into an unbroken line without needing intrinsic measurement
 * (unreliable here, since the summary text wraps).
 */
@Composable
private fun CommitEntry(
    experience: Experience,
    isHead: Boolean,
    isLast: Boolean,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val railColor = railLine

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawRail(
                    lineColor = railColor,
                    top = if (isHead) NODE_CENTER_Y.toPx() else 0f,
                    bottom = if (isLast) NODE_CENTER_Y.toPx() else size.height,
                )
                drawNode(
                    color = if (isHead) extra.accent else extra.faint,
                    haloColor = if (isHead) extra.accent.copy(alpha = 0.18f) else null,
                    fillColor = if (isHead) null else scheme.surface,
                )
            },
    ) {
        Spacer(modifier = Modifier.width(width = RAIL_WIDTH))

        Column(
            modifier = Modifier
                .weight(weight = 1f)
                .padding(bottom = if (isLast) 0.dp else ENTRY_GAP),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
            ) {
                Text(
                    text = experience.period,
                    style = MaterialTheme.typography.labelMedium,
                    color = extra.faint,
                )
                if (isHead) {
                    HeadBadge()
                }
            }
            Spacer(modifier = Modifier.height(height = 6.dp))

            Text(
                text = experience.role,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = scheme.onBackground,
            )
            Text(
                text = experience.company,
                style = MaterialTheme.typography.labelLarge,
                color = extra.muted,
            )

            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = experience.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = extra.muted,
            )

            if (experience.highlights.isNotEmpty()) {
                Spacer(modifier = Modifier.height(height = 10.dp))
                Column(verticalArrangement = Arrangement.spacedBy(space = 4.dp)) {
                    experience.highlights.forEach { highlight ->
                        HighlightLine(text = highlight)
                    }
                }
            }
        }
    }
}

/** Draws the vertical graph line down the centre of the rail gutter. */
private fun DrawScope.drawRail(lineColor: Color, top: Float, bottom: Float) {
    if (bottom <= top) return
    val centerX = RAIL_WIDTH.toPx() / 2f
    drawLine(
        color = lineColor,
        start = Offset(centerX, top),
        end = Offset(centerX, bottom),
        strokeWidth = 1.dp.toPx(),
    )
}

/**
 * Draws a commit node on the rail. HEAD reads as a filled node inside a halo; older commits
 * are hollow rings punched out of the panel so the line appears to pass behind them.
 */
private fun DrawScope.drawNode(color: Color, haloColor: Color?, fillColor: Color?) {
    val center = Offset(RAIL_WIDTH.toPx() / 2f, NODE_CENTER_Y.toPx())
    val radius = NODE_RADIUS.toPx()

    haloColor?.let {
        drawCircle(color = it, radius = radius + 3.dp.toPx(), center = center)
    }
    if (fillColor == null) {
        drawCircle(color = color, radius = radius, center = center)
    } else {
        drawCircle(color = fillColor, radius = radius, center = center)
        drawCircle(
            color = color,
            radius = radius,
            center = center,
            style = Stroke(width = 1.5.dp.toPx()),
        )
    }
}

/** `HEAD -> career` marker on the current role. */
@Composable
private fun HeadBadge() {
    val extra = PortfolioTheme.extra
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 4.dp))
            .background(color = extra.accentSoft)
            .padding(horizontal = 6.dp, vertical = 2.dp),
    ) {
        Text(
            text = "HEAD -> career",
            style = MaterialTheme.typography.labelMedium,
            color = extra.accent,
        )
    }
}

/**
 * A single achievement, marked with a diff-style `+`. Only the marker is tinted — the text
 * stays body ink so a run of highlights still reads as prose rather than decoration.
 */
@Composable
private fun HighlightLine(text: String) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
    ) {
        Text(
            text = "+",
            style = MaterialTheme.typography.labelLarge,
            color = extra.accent,
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = scheme.onBackground,
            modifier = Modifier.weight(weight = 1f),
        )
    }
}

/**
 * The `git log` continuation prompt: reveals the older roles in place so the section stays
 * short by default without dropping any history. Sits on the rail as an ellipsis node.
 */
@Composable
private fun LogToggle(
    showAll: Boolean,
    hiddenCount: Int,
    onToggle: () -> Unit,
) {
    val extra = PortfolioTheme.extra
    val railColor = railLine
    val hover = rememberHover()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .hoverable(interactionSource = hover.interactionSource)
            .clickable(
                interactionSource = hover.interactionSource,
                indication = null,
                role = Role.Button,
                onClick = onToggle,
            )
            .drawBehind {
                drawRail(
                    lineColor = railColor,
                    top = 0f,
                    bottom = NODE_CENTER_Y.toPx(),
                )
                // Three stacked dots: the git-graph way of saying "history continues".
                val centerX = RAIL_WIDTH.toPx() / 2f
                repeat(times = 3) { i ->
                    drawCircle(
                        color = if (hover.isHovered) extra.accent else extra.faint,
                        radius = 1.5.dp.toPx(),
                        center = Offset(centerX, NODE_CENTER_Y.toPx() + (i * 5.dp.toPx())),
                    )
                }
            },
    ) {
        Spacer(modifier = Modifier.width(width = RAIL_WIDTH))
        Text(
            text = if (showAll) "$ git log -$RECENT_COUNT" else "$ git log --all",
            style = MaterialTheme.typography.labelLarge,
            color = if (hover.isHovered) extra.accent else extra.muted,
        )
        Spacer(modifier = Modifier.width(width = 10.dp))
        Text(
            text = if (showAll) "# collapse" else "# $hiddenCount earlier roles",
            style = MaterialTheme.typography.labelLarge,
            color = extra.faint,
        )
    }
}
