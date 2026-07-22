package com.ariastro.portfolio.ui.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ariastro.portfolio.data.LinkType
import com.ariastro.portfolio.data.PortfolioData
import com.ariastro.portfolio.data.Project
import com.ariastro.portfolio.ui.components.MonoChipRow
import com.ariastro.portfolio.ui.components.Shell
import com.ariastro.portfolio.ui.components.StatusDot
import com.ariastro.portfolio.ui.theme.PortfolioTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun BuildsSection(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Shell(
        modifier = modifier.padding(vertical = 24.dp)
    ) {
        // Terminal-style section head
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 16.dp))
                .background(color = extra.codeBg)
                .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 16.dp))
                .padding(all = 18.dp),
        ) {
            Text(
                text = "$ git log --ships --oneline",
                style = MaterialTheme.typography.labelLarge,
                color = extra.accent,
            )
            Spacer(modifier = Modifier.height(height = 10.dp))
            Text(
                text = "Selected ships",
                style = MaterialTheme.typography.displayMedium,
                color = scheme.onBackground,
            )
            Spacer(modifier = Modifier.height(height = 6.dp))
            Text(
                text = "${PortfolioData.projects.size} production releases · scroll the device lab under each commit",
                style = MaterialTheme.typography.bodyMedium,
                color = extra.muted,
            )
        }

        Spacer(modifier = Modifier.height(height = 36.dp))

        Column {
            PortfolioData.projects.forEachIndexed { index, project ->
                ReleaseCommit(
                    project = project,
                    isLast = index == PortfolioData.projects.lastIndex,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ReleaseCommit(
    project: Project,
    isLast: Boolean,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val uriHandler = LocalUriHandler.current

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        // Timeline rail
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(width = 36.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(size = 14.dp)
                    .clip(shape = CircleShape)
                    .background(color = project.accent)
                    .border(width = 3.dp, color = scheme.background, shape = CircleShape),
            )
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(width = 2.dp)
                        .weight(weight = 1f, fill = true)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(project.accent.copy(alpha = 0.55f), scheme.outline),
                            ),
                        ),
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(weight = 1f)
                .padding(start = 10.dp, bottom = if (isLast) 0.dp else 44.dp),
        ) {
            // Commit meta line
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
            ) {
                Text(
                    text = project.index,
                    style = MaterialTheme.typography.labelLarge,
                    color = project.accent,
                )
                Text(
                    text = "ship/${project.id}",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.faint,
                )
                StatusDot(label = project.status)
            }

            Spacer(modifier = Modifier.height(height = 12.dp))

            // Giant title block with watermark index
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = project.index,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Bold,
                        color = project.accent.copy(alpha = 0.08f),
                    ),
                    modifier = Modifier.align(alignment = Alignment.CenterEnd),
                )
                Column {
                    Text(
                        text = project.title,
                        style = MaterialTheme.typography.displayMedium,
                        color = scheme.onBackground,
                    )
                    Spacer(modifier = Modifier.height(height = 6.dp))
                    Text(
                        text = project.blurb,
                        style = MaterialTheme.typography.titleLarge,
                        color = extra.muted,
                    )
                    Spacer(modifier = Modifier.height(height = 12.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                    ) {
                        MetaPill(text = project.category, color = project.accent)
                        MetaPill(text = project.year, color = extra.muted)
                        MetaPill(text = project.role, color = extra.muted)
                    }
                }
            }

            Spacer(modifier = Modifier.height(height = 18.dp))

            // Story as release notes panel
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 14.dp))
                    .background(color = scheme.surface)
                    .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 14.dp)),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = extra.codeBg)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(text = "RELEASE_NOTES.md", style = MaterialTheme.typography.labelMedium, color = extra.faint)
                    Text(text = "v${project.index}", style = MaterialTheme.typography.labelMedium, color = project.accent)
                }
                Text(
                    text = project.story,
                    style = MaterialTheme.typography.bodyLarge,
                    color = scheme.onBackground,
                    modifier = Modifier.padding(all = 14.dp),
                )
            }

            Spacer(modifier = Modifier.height(height = 14.dp))

            // Diff-style highlights
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 14.dp))
                    .background(color = extra.codeBg)
                    .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 14.dp))
                    .padding(all = 12.dp),
                verticalArrangement = Arrangement.spacedBy(space = 4.dp),
            ) {
                Text(
                    text = "diff --git a/features b/features",
                    style = MaterialTheme.typography.labelMedium,
                    color = extra.faint,
                    modifier = Modifier.padding(bottom = 6.dp),
                )
                project.highlights.forEach { item ->
                    DiffLine(text = item, accent = project.accent)
                }
            }

            Spacer(modifier = Modifier.height(height = 14.dp))

            // dependencies block
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 14.dp))
                    .background(color = scheme.surface)
                    .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 14.dp))
                    .padding(all = 14.dp),
            ) {
                Text(
                    text = "\"dependencies\": {",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.faint,
                )
                Spacer(modifier = Modifier.height(height = 10.dp))
                MonoChipRow(items = project.stack)
                Spacer(modifier = Modifier.height(height = 8.dp))
                Text(
                    text = "}",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.faint,
                )
            }

            Spacer(modifier = Modifier.height(height = 18.dp))

            // Play Store listing art gallery
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 18.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                project.accent.copy(alpha = 0.10f),
                                extra.codeBg,
                                extra.codeBg,
                            ),
                        ),
                    )
                    .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 18.dp))
                    .padding(vertical = 16.dp),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            text = "store art",
                            style = MaterialTheme.typography.labelMedium,
                            color = project.accent,
                        )
                        Text(
                            text = "Play listing · ${project.screenshots.size} assets",
                            style = MaterialTheme.typography.labelMedium,
                            color = extra.faint,
                        )
                    }
                    Text(
                        text = "scroll →",
                        style = MaterialTheme.typography.labelLarge,
                        color = extra.muted,
                    )
                }
                Spacer(modifier = Modifier.height(height = 14.dp))
                ListingGallery(
                    screenshots = project.screenshots,
                    title = project.title,
                    accent = project.accent,
                )
            }

            if (project.link != null) {
                Spacer(modifier = Modifier.height(height = 14.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .background(color = scheme.surface)
                        .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 12.dp))
                        .clickable { uriHandler.openUri(project.link) }
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = when (project.linkType) {
                            LinkType.PlayStore -> "$ adb shell am start -a VIEW ${project.title}"
                            LinkType.GitHub -> "$ gh repo view ${project.id}"
                            else -> "$ open ${project.id}"
                        },
                        style = MaterialTheme.typography.labelLarge,
                        color = extra.accent,
                        modifier = Modifier.weight(weight = 1f),
                        maxLines = 1,
                    )
                    Text(
                        text = "run",
                        style = MaterialTheme.typography.labelLarge,
                        color = project.accent,
                    )
                }
            }
        }
    }
}

@Composable
private fun MetaPill(
    text: String,
    color: androidx.compose.ui.graphics.Color,
) {
    val scheme = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 999.dp))
            .border(width = 1.dp, color = color.copy(alpha = 0.35f), shape = RoundedCornerShape(size = 999.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = color,
        )
    }
}

@Composable
private fun DiffLine(
    text: String,
    accent: androidx.compose.ui.graphics.Color,
) {
    val scheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(color = accent.copy(alpha = 0.08f))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
    ) {
        Text(
            text = "+",
            style = MaterialTheme.typography.labelLarge,
            color = accent,
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = scheme.onBackground,
            modifier = Modifier.weight(weight = 1f),
        )
    }
}

@Composable
private fun ListingGallery(
    screenshots: List<DrawableResource>,
    title: String,
    accent: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier,
) {
    val scroll = rememberScrollState()
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    // Play listing graphics are often tall marketing frames — fixed height, width from image ratio via Fit
    val cardHeight = 420.dp
    val cardWidth = 240.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(state = scroll)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        screenshots.forEachIndexed { i, shot ->
            Column(
                modifier = Modifier
                    .width(width = cardWidth)
                    .height(height = cardHeight)
                    .clip(shape = RoundedCornerShape(size = 16.dp))
                    .background(color = scheme.surface)
                    .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 16.dp)),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "asset_${(i + 1).toString().padStart(length = 2, padChar = '0')}",
                        style = MaterialTheme.typography.labelMedium,
                        color = extra.faint,
                    )
                    Box(
                        modifier = Modifier
                            .size(size = 6.dp)
                            .clip(shape = CircleShape)
                            .background(color = accent),
                    )
                }
                Box(modifier = Modifier.fillMaxWidth().height(height = 1.dp).background(color = scheme.outline))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1f)
                        .padding(all = 10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(resource = shot),
                        contentDescription = "$title listing ${i + 1}",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(shape = RoundedCornerShape(size = 10.dp)),
                    )
                }
            }
        }
    }
}
