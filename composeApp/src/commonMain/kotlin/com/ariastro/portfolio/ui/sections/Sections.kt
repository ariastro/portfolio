package com.ariastro.portfolio.ui.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import com.ariastro.portfolio.ui.components.LinkText
import com.ariastro.portfolio.ui.components.MonoChip
import com.ariastro.portfolio.ui.components.MonoChipRow
import com.ariastro.portfolio.ui.components.Shell
import com.ariastro.portfolio.ui.components.StatusDot
import com.ariastro.portfolio.ui.components.ThemeToggle
import com.ariastro.portfolio.ui.theme.PortfolioTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopBar(
    isDark: Boolean,
    onToggleTheme: () -> Unit,
    onNav: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Column(
        modifier
            .fillMaxWidth()
            .background(scheme.background),
    ) {
        Shell {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(0xFFFF5F57, 0xFFFEBC2E, 0xFF28C840).forEach { c ->
                            Box(
                                Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(androidx.compose.ui.graphics.Color(c)),
                            )
                        }
                    }
                    Text(
                        text = "portfolio.kt",
                        style = MaterialTheme.typography.labelLarge,
                        color = extra.muted,
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    listOf("readme", "builds", "connect").forEach { tab ->
                        Text(
                            text = tab,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (tab == "builds") extra.accent else extra.muted,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { onNav(tab) }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                        )
                    }
                    Spacer(Modifier.width(6.dp))
                    ThemeToggle(isDark, onToggleTheme)
                }
            }
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(scheme.outline))
    }
}

@Composable
fun HeroSection(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Shell(modifier.padding(top = 40.dp, bottom = 48.dp)) {
        BoxWithConstraints(Modifier.fillMaxWidth()) {
            val wide = maxWidth >= 760.dp

            if (wide) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(28.dp),
                ) {
                    CodeWindow(Modifier.weight(1.15f))
                    HeroMeta(Modifier.weight(0.85f))
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    CodeWindow(Modifier.fillMaxWidth())
                    HeroMeta(Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
private fun CodeWindow(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Column(
        modifier
            .clip(RoundedCornerShape(14.dp))
            .background(extra.codeBg)
            .border(1.dp, scheme.outline, RoundedCornerShape(14.dp)),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(scheme.surface.copy(alpha = 0.5f))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Main.kt", style = MaterialTheme.typography.labelLarge, color = extra.muted)
            StatusDot("BUILD SUCCESSFUL")
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(scheme.outline))
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            PortfolioData.heroLines.forEachIndexed { i, line ->
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = (i + 1).toString().padStart(2, ' '),
                        style = MaterialTheme.typography.labelSmall,
                        color = extra.faint,
                    )
                    Text(
                        text = line.ifEmpty { " " },
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 13.sp,
                            lineHeight = 22.sp,
                        ),
                        color = when {
                            line.startsWith("package") -> extra.muted
                            line.startsWith("/**") || line.startsWith(" *") || line.startsWith(" */") ->
                                extra.faint
                            line.contains("fun ") || line == "}" -> scheme.onBackground
                            line.trim().endsWith("()") -> extra.accent
                            else -> scheme.onBackground
                        },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun HeroMeta(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Column(modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = PortfolioData.HANDLE,
            style = MaterialTheme.typography.labelMedium,
            color = extra.accent,
        )
        Text(
            text = PortfolioData.FULL_NAME,
            style = MaterialTheme.typography.displayLarge,
            color = scheme.onBackground,
        )
        Text(
            text = PortfolioData.TITLE,
            style = MaterialTheme.typography.headlineLarge,
            color = extra.muted,
        )
        Text(
            text = "I ship production Android systems — not demos. " +
                "From telecom scale to personal side builds.",
            style = MaterialTheme.typography.bodyLarge,
            color = extra.muted,
        )
        Spacer(Modifier.height(4.dp))
        FlowFacts()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FlowFacts() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        PortfolioData.facts.forEach { (value, label) ->
            FactCell(value, label)
        }
    }
}

@Composable
private fun FactCell(value: String, label: String) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    Column(
        Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(scheme.surface)
            .border(1.dp, scheme.outline, RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
    ) {
        Text(value, style = MaterialTheme.typography.titleLarge, color = extra.accent)
        Text(label, style = MaterialTheme.typography.labelMedium, color = extra.faint)
    }
}

@Composable
fun ReadmeSection(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Shell(modifier.padding(vertical = 24.dp)) {
        Panel(title = "README.md") {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = "# About",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.accent,
                )
                Spacer(Modifier.height(12.dp))
                PortfolioData.ABOUT.split("\n\n").forEachIndexed { i, paragraph ->
                    if (i > 0) Spacer(Modifier.height(14.dp))
                    Text(
                        text = paragraph,
                        style = MaterialTheme.typography.bodyLarge,
                        color = scheme.onBackground,
                    )
                }
                Spacer(Modifier.height(22.dp))
                Text(
                    text = "## stack",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.accent,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Tools I reach for when shipping Android products.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = extra.muted,
                )
                Spacer(Modifier.height(12.dp))
                MonoChipRow(PortfolioData.stack, Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun BuildsSection(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Shell(modifier.padding(vertical = 24.dp)) {
        // Terminal-style section head
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(extra.codeBg)
                .border(1.dp, scheme.outline, RoundedCornerShape(16.dp))
                .padding(18.dp),
        ) {
            Text(
                text = "$ git log --ships --oneline",
                style = MaterialTheme.typography.labelLarge,
                color = extra.accent,
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Selected ships",
                style = MaterialTheme.typography.displayMedium,
                color = scheme.onBackground,
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text = "${PortfolioData.projects.size} production releases · scroll the device lab under each commit",
                style = MaterialTheme.typography.bodyMedium,
                color = extra.muted,
            )
        }

        Spacer(Modifier.height(36.dp))

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

    Row(modifier.fillMaxWidth()) {
        // Timeline rail
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(36.dp),
        ) {
            Box(
                Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(project.accent)
                    .border(3.dp, scheme.background, CircleShape),
            )
            if (!isLast) {
                Box(
                    Modifier
                        .width(2.dp)
                        .weight(1f, fill = true)
                        .background(
                            Brush.verticalGradient(
                                listOf(project.accent.copy(0.55f), scheme.outline),
                            ),
                        ),
                )
            }
        }

        Column(
            Modifier
                .weight(1f)
                .padding(start = 10.dp, bottom = if (isLast) 0.dp else 44.dp),
        ) {
            // Commit meta line
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
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
                StatusDot(project.status)
            }

            Spacer(Modifier.height(12.dp))

            // Giant title block with watermark index
            Box(Modifier.fillMaxWidth()) {
                Text(
                    text = project.index,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 72.sp,
                        fontWeight = FontWeight.Bold,
                        color = project.accent.copy(alpha = 0.08f),
                    ),
                    modifier = Modifier.align(Alignment.CenterEnd),
                )
                Column {
                    Text(
                        text = project.title,
                        style = MaterialTheme.typography.displayMedium,
                        color = scheme.onBackground,
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = project.blurb,
                        style = MaterialTheme.typography.titleLarge,
                        color = extra.muted,
                    )
                    Spacer(Modifier.height(12.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        MetaPill(project.category, project.accent)
                        MetaPill(project.year, extra.muted)
                        MetaPill(project.role, extra.muted)
                    }
                }
            }

            Spacer(Modifier.height(18.dp))

            // Story as release notes panel
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(scheme.surface)
                    .border(1.dp, scheme.outline, RoundedCornerShape(14.dp)),
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(extra.codeBg)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text("RELEASE_NOTES.md", style = MaterialTheme.typography.labelMedium, color = extra.faint)
                    Text("v${project.index}", style = MaterialTheme.typography.labelMedium, color = project.accent)
                }
                Text(
                    text = project.story,
                    style = MaterialTheme.typography.bodyLarge,
                    color = scheme.onBackground,
                    modifier = Modifier.padding(14.dp),
                )
            }

            Spacer(Modifier.height(14.dp))

            // Diff-style highlights
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(extra.codeBg)
                    .border(1.dp, scheme.outline, RoundedCornerShape(14.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
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

            Spacer(Modifier.height(14.dp))

            // dependencies block
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(scheme.surface)
                    .border(1.dp, scheme.outline, RoundedCornerShape(14.dp))
                    .padding(14.dp),
            ) {
                Text(
                    text = "\"dependencies\": {",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.faint,
                )
                Spacer(Modifier.height(10.dp))
                MonoChipRow(project.stack)
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "}",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.faint,
                )
            }

            Spacer(Modifier.height(18.dp))

            // Play Store listing art gallery
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                project.accent.copy(alpha = 0.10f),
                                extra.codeBg,
                                extra.codeBg,
                            ),
                        ),
                    )
                    .border(1.dp, scheme.outline, RoundedCornerShape(18.dp))
                    .padding(vertical = 16.dp),
            ) {
                Row(
                    Modifier
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
                Spacer(Modifier.height(14.dp))
                ListingGallery(
                    screenshots = project.screenshots,
                    title = project.title,
                    accent = project.accent,
                )
            }

            if (project.link != null) {
                Spacer(Modifier.height(14.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(scheme.surface)
                        .border(1.dp, scheme.outline, RoundedCornerShape(12.dp))
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
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                    )
                    Text(
                        text = "run ↵",
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
        Modifier
            .clip(RoundedCornerShape(999.dp))
            .border(1.dp, color.copy(alpha = 0.35f), RoundedCornerShape(999.dp))
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
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(accent.copy(alpha = 0.08f))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
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
            modifier = Modifier.weight(1f),
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
        modifier
            .fillMaxWidth()
            .horizontalScroll(scroll)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        screenshots.forEachIndexed { i, shot ->
            Column(
                Modifier
                    .width(cardWidth)
                    .height(cardHeight)
                    .clip(RoundedCornerShape(16.dp))
                    .background(scheme.surface)
                    .border(1.dp, scheme.outline, RoundedCornerShape(16.dp)),
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "asset_${(i + 1).toString().padStart(2, '0')}",
                        style = MaterialTheme.typography.labelMedium,
                        color = extra.faint,
                    )
                    Box(
                        Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(accent),
                    )
                }
                Box(Modifier.fillMaxWidth().height(1.dp).background(scheme.outline))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(shot),
                        contentDescription = "$title listing ${i + 1}",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp)),
                    )
                }
            }
        }
    }
}

@Composable
fun ConnectSection(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val uriHandler = LocalUriHandler.current

    Shell(modifier.padding(top = 24.dp, bottom = 64.dp)) {
        Panel(title = "connect.sh") {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = "$ ./connect --to \"Ari SWS\"",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.accent,
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Open to Android / KMP roles, freelance, collaboration.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = scheme.onBackground,
                )
                Spacer(Modifier.height(20.dp))
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    ConnectLine("email", PortfolioData.EMAIL) {
                        uriHandler.openUri("mailto:${PortfolioData.EMAIL}")
                    }
                    ConnectLine("github", PortfolioData.GITHUB) {
                        uriHandler.openUri(PortfolioData.GITHUB)
                    }
                    ConnectLine("linkedin", PortfolioData.LINKEDIN) {
                        uriHandler.openUri(PortfolioData.LINKEDIN)
                    }
                }
                Spacer(Modifier.height(28.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "© ${PortfolioData.NAME}",
                        style = MaterialTheme.typography.labelMedium,
                        color = extra.faint,
                    )
                    Text(
                        text = "·",
                        style = MaterialTheme.typography.labelMedium,
                        color = extra.faint,
                    )
                    Text(
                        text = "Made with Kotlin",
                        style = MaterialTheme.typography.labelMedium,
                        color = extra.faint,
                    )
                }
            }
        }
    }
}

@Composable
private fun ConnectLine(key: String, value: String, onClick: () -> Unit) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(extra.codeBg)
            .border(1.dp, scheme.outline, RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = key,
            style = MaterialTheme.typography.labelLarge,
            color = extra.faint,
            modifier = Modifier.width(80.dp),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = extra.accent,
            modifier = Modifier.weight(1f),
            maxLines = 2,
        )
    }
}

@Composable
private fun Panel(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(scheme.surface)
            .border(1.dp, scheme.outline, RoundedCornerShape(14.dp)),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(title, style = MaterialTheme.typography.labelLarge, color = extra.muted)
            StatusDot("ok")
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(scheme.outline))
        Column(
            Modifier
                .fillMaxWidth()
                .padding(18.dp),
        ) {
            content()
        }
    }
}
