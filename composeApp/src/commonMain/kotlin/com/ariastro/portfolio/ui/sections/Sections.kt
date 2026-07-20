package com.ariastro.portfolio.ui.sections

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
                Text(
                    text = PortfolioData.ABOUT,
                    style = MaterialTheme.typography.bodyLarge,
                    color = scheme.onBackground,
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "## stack",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.accent,
                )
                Spacer(Modifier.height(14.dp))
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    PortfolioData.stackGroups.forEach { (group, items) ->
                        Column(
                            Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = group,
                                style = MaterialTheme.typography.labelMedium,
                                color = extra.faint,
                            )
                            MonoChipRow(items, Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BuildsSection(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Shell(modifier.padding(vertical = 24.dp)) {
        Row(
            Modifier.fillMaxWidth().padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            Column {
                Text(
                    text = "builds/",
                    style = MaterialTheme.typography.labelMedium,
                    color = extra.accent,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Selected ships",
                    style = MaterialTheme.typography.displayMedium,
                    color = scheme.onBackground,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Case notes from production apps — not demos.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = extra.muted,
                )
            }
            Text(
                text = "${PortfolioData.projects.size} entries",
                style = MaterialTheme.typography.labelLarge,
                color = extra.faint,
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(28.dp)) {
            PortfolioData.projects.forEachIndexed { index, project ->
                ProjectCase(
                    project = project,
                    reverse = index % 2 == 1,
                )
            }
        }
    }
}

@Composable
private fun ProjectCase(
    project: Project,
    reverse: Boolean,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val uriHandler = LocalUriHandler.current

    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(scheme.surface)
            .border(1.dp, scheme.outline, RoundedCornerShape(18.dp)),
    ) {
        // Case header
        Row(
            Modifier
                .fillMaxWidth()
                .background(extra.codeBg)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "builds/${project.id}.md",
                style = MaterialTheme.typography.labelLarge,
                color = extra.muted,
            )
            StatusDot(project.status)
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(scheme.outline))

        BoxWithConstraints(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            val wide = maxWidth >= 720.dp

            if (wide) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.Top,
                ) {
                    val shot: @Composable (Modifier) -> Unit = { mod ->
                        ProjectShot(project, mod.weight(1.1f))
                    }
                    val body: @Composable (Modifier) -> Unit = { mod ->
                        ProjectBody(project, mod.weight(1f))
                    }
                    if (reverse) {
                        body(Modifier)
                        shot(Modifier)
                    } else {
                        shot(Modifier)
                        body(Modifier)
                    }
                }
            } else {
                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    ProjectShot(project, Modifier.fillMaxWidth())
                    ProjectBody(project, Modifier.fillMaxWidth())
                }
            }
        }

        if (project.link != null) {
            Box(Modifier.fillMaxWidth().height(1.dp).background(scheme.outline))
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { uriHandler.openUri(project.link) }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = when (project.linkType) {
                        LinkType.PlayStore -> "Open on Play Store"
                        LinkType.GitHub -> "Open on GitHub"
                        else -> "Open project"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.accent,
                )
                Text(
                    text = "→",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.accent,
                )
            }
        }
    }
}

@Composable
private fun ProjectShot(
    project: Project,
    modifier: Modifier = Modifier,
) {
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
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "screenshots",
                style = MaterialTheme.typography.labelMedium,
                color = extra.faint,
            )
            Text(
                text = "${project.screenshots.size} images",
                style = MaterialTheme.typography.labelMedium,
                color = extra.accent,
            )
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(scheme.outline))
        
        // Grid of screenshots
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            project.screenshots.chunked(2).forEach { rowScreenshots ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    rowScreenshots.forEach { screenshot ->
                        Image(
                            painter = painterResource(screenshot),
                            contentDescription = "${project.title} screenshot",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(9f / 16f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(scheme.surface),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProjectBody(
    project: Project,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MonoChip(project.category)
            MonoChip(project.year)
            MonoChip(project.role)
        }

        Text(
            text = project.title,
            style = MaterialTheme.typography.displayMedium,
            color = scheme.onBackground,
        )

        Text(
            text = project.blurb,
            style = MaterialTheme.typography.titleLarge,
            color = extra.accent,
        )

        Text(
            text = project.story,
            style = MaterialTheme.typography.bodyLarge,
            color = extra.muted,
        )

        Spacer(Modifier.height(4.dp))
        Text(
            text = "## highlights",
            style = MaterialTheme.typography.labelLarge,
            color = extra.accent,
        )
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            project.highlights.forEach { item ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "›",
                        style = MaterialTheme.typography.labelLarge,
                        color = extra.accent,
                    )
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyMedium,
                        color = scheme.onBackground,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }

        Spacer(Modifier.height(4.dp))
        Text(
            text = "## stack",
            style = MaterialTheme.typography.labelLarge,
            color = extra.accent,
        )
        MonoChipRow(project.stack)
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
                Text(
                    text = "© ${PortfolioData.NAME} · Compose Multiplatform",
                    style = MaterialTheme.typography.labelMedium,
                    color = extra.faint,
                )
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
