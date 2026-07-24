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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ariastro.portfolio.data.PortfolioData
import com.ariastro.portfolio.data.Project
import com.ariastro.portfolio.ui.components.MonoChipRow
import com.ariastro.portfolio.ui.components.Shell
import com.ariastro.portfolio.ui.theme.BrandTagTag
import com.ariastro.portfolio.ui.theme.PortfolioTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun BuildsSection(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    var selectedIdx by remember { mutableStateOf(0) }
    var editorMode by remember { mutableStateOf("Split") } // Code, Split, Design
    val projects = PortfolioData.projects
    val project = projects.getOrNull(selectedIdx) ?: projects[0]

    Shell(
        modifier = modifier.padding(vertical = 24.dp)
    ) {
        // IDE Workspace Container
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 14.dp))
                .background(color = extra.codeBg)
                .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 14.dp))
        ) {
            // IDE Header / Window Bar
            IdeHeader(
                projectName = project.title,
                editorMode = editorMode,
                onModeChange = { editorMode = it }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 1.dp)
                    .background(color = scheme.outline)
            )

            // Split Sidebar + Editor Workspace
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val wide = maxWidth >= 760.dp

                // Set a sensible workspace height
                val workspaceHeight = 580.dp

                if (wide) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = workspaceHeight)
                    ) {
                        // Project Tree Sidebar
                        ProjectTree(
                            projects = projects,
                            selectedIdx = selectedIdx,
                            onSelect = { selectedIdx = it },
                            modifier = Modifier
                                .width(width = 200.dp)
                                .fillMaxHeight()
                                .background(color = scheme.surface.copy(alpha = 0.5f))
                        )

                        // Split Line
                        Box(
                            modifier = Modifier
                                .width(width = 1.dp)
                                .fillMaxHeight()
                                .background(color = scheme.outline)
                        )

                        // Editor Area
                        EditorArea(
                            project = project,
                            editorMode = editorMode,
                            modifier = Modifier
                                .weight(weight = 1f)
                                .fillMaxHeight()
                        )
                    }
                } else {
                    // Mobile Layout: Sidebar collapsed to selector, stack editor below
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Horizontal file scroll selector (replaces Tree on mobile)
                        MobileFileTabs(
                            projects = projects,
                            selectedIdx = selectedIdx,
                            onSelect = { selectedIdx = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = scheme.surface.copy(alpha = 0.5f))
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height = 1.dp)
                                .background(color = scheme.outline)
                        )

                        // Editor Area
                        EditorArea(
                            project = project,
                            editorMode = if (editorMode == "Split") "Code" else editorMode, // Force clean view on mobile
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height = workspaceHeight)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 1.dp)
                    .background(color = scheme.outline)
            )

            // Run Config Console
            ConsoleBar(project = project)
        }
    }
}

@Composable
private fun IdeHeader(
    projectName: String,
    editorMode: String,
    onModeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // macOS Dots
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            listOf(0xFFFF5F57, 0xFFFEBC2E, 0xFF28C840).forEach { c ->
                Box(
                    modifier = Modifier
                        .size(size = 10.dp)
                        .clip(shape = CircleShape)
                        .background(color = Color(color = c)),
                )
            }
            Spacer(modifier = Modifier.width(width = 6.dp))
            Text(
                text = "android-project-lab",
                style = MaterialTheme.typography.labelLarge,
                color = extra.muted,
            )
        }

        // Layout Mode Switcher
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 6.dp))
                .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 6.dp))
                .background(color = scheme.surface),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            listOf("Code", "Split", "Design").forEach { mode ->
                Text(
                    text = mode,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (editorMode == mode) extra.accent else extra.muted,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(size = 4.dp))
                        .clickable { onModeChange(mode) }
                        .background(color = if (editorMode == mode) extra.accentSoft else Color.Transparent)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                )
            }
        }
    }
}

@Composable
private fun ProjectTree(
    projects: List<Project>,
    selectedIdx: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Column(
        modifier = modifier.padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(space = 4.dp)
    ) {
        Text(
            text = "src/main/projects",
            style = MaterialTheme.typography.labelLarge,
            color = scheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
        )

        projects.forEachIndexed { index, proj ->
            val isSelected = index == selectedIdx
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(index) }
                    .background(color = if (isSelected) extra.accentSoft else Color.Transparent)
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${proj.title.replace(" ", "")}.kt",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isSelected) extra.accent else scheme.onSurface.copy(alpha = 0.8f),
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun MobileFileTabs(
    projects: List<Project>,
    selectedIdx: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(state = scrollState)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        projects.forEachIndexed { index, proj ->
            val isSelected = index == selectedIdx
            Text(
                text = "${proj.title.replace(" ", "")}.kt",
                style = MaterialTheme.typography.labelLarge,
                color = if (isSelected) extra.accent else scheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = 6.dp))
                    .clickable { onSelect(index) }
                    .background(color = if (isSelected) extra.accentSoft else Color.Transparent)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
            )
        }
    }
}

@Composable
private fun EditorArea(
    project: Project,
    editorMode: String,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    var activeTab by remember(project) { mutableStateOf("Doc") } // Default to Doc (README.md) for non-tech users

    Row(modifier = modifier) {
        if (editorMode == "Code" || editorMode == "Split") {
            // Source Code Panel
            Column(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxHeight()
            ) {
                // Internal File Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = scheme.surface.copy(alpha = 0.3f))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
                ) {
                    EditorFileTab(
                        title = "README.md",
                        isActive = activeTab == "Doc",
                        onClick = { activeTab = "Doc" }
                    )
                    EditorFileTab(
                        title = "${project.title.replace(" ", "")}.kt",
                        isActive = activeTab == "Code",
                        onClick = { activeTab = "Code" }
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 1.dp)
                        .background(color = scheme.outline)
                )

                // Panel content based on activeTab
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1f)
                        .padding(all = 14.dp)
                ) {
                    if (activeTab == "Doc") {
                        DocViewer(project = project)
                    } else {
                        CodeViewer(project = project)
                    }
                }
            }
        }

        if (editorMode == "Split") {
            // Split Line
            Box(
                modifier = Modifier
                    .width(width = 1.dp)
                    .fillMaxHeight()
                    .background(color = scheme.outline)
            )
        }

        if (editorMode == "Design" || editorMode == "Split") {
            // Design Preview Panel
            Box(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxHeight()
                    .padding(all = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                DesignViewer(project = project)
            }
        }
    }
}

@Composable
private fun EditorFileTab(
    title: String,
    isActive: Boolean,
    onClick: () -> Unit,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 4.dp))
            .clickable(onClick = onClick)
            .background(color = if (isActive) extra.codeBg else Color.Transparent)
            .border(
                width = 1.dp,
                color = if (isActive) scheme.outline else Color.Transparent,
                shape = RoundedCornerShape(size = 4.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 6.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = if (isActive) extra.accent else scheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun DocViewer(project: Project) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scroll)
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp)
    ) {
        // Title
        Text(
            text = "# ${project.title}",
            style = MaterialTheme.typography.displayMedium,
            color = scheme.onSurface
        )

        // Meta badges
        Row(
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            MetaPill(text = project.category, color = project.accent)
            MetaPill(text = project.year, color = extra.muted)
            MetaPill(text = project.role, color = extra.muted)
        }

        // Story
        Text(
            text = project.story,
            style = MaterialTheme.typography.bodyLarge,
            color = scheme.onBackground,
            lineHeight = 24.sp
        )

        // Highlights section
        Text(
            text = "## Key Highlights",
            style = MaterialTheme.typography.titleLarge,
            color = extra.accent
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            project.highlights.forEach { highlight ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "-",
                        style = MaterialTheme.typography.bodyLarge,
                        color = project.accent,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = highlight,
                        style = MaterialTheme.typography.bodyLarge,
                        color = scheme.onBackground
                    )
                }
            }
        }

        // Dependencies/Stack
        Text(
            text = "## Tech Stack",
            style = MaterialTheme.typography.titleLarge,
            color = extra.accent
        )

        MonoChipRow(items = project.stack)
    }
}

@Composable
private fun MetaPill(
    text: String,
    color: Color,
) {
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
private fun CodeViewer(project: Project) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val scroll = rememberScrollState()

    val codeText = remember(project) {
        val stackList = project.stack.joinToString(", ") { "\"$it\"" }
        val highlightsList = project.highlights.joinToString("\n            ") { "+ \"$it\"" }
        """
package portfolio.projects

import id.ariastro.portfolio.data.*

object ${project.title.replace(" ", "")} : Project {
    val role = "${project.role}"
    val category = "${project.category}"
    val year = "${project.year}"
    val status = "${project.status}"

    val blurb = "${project.blurb}"

    val stack = listOf(
        $stackList
    )

    fun getReleaseNotes() = buildString {
        // ${project.story}
        
        highlights {
            $highlightsList
        }
    }
}
        """.trimIndent()
    }

    val commentColor = if (extra.isDark) Color(0xFF8B93A5) else Color(0xFF6E7485)
    val plainColor = scheme.onSurface
    val highlightedCode = remember(codeText, extra.isDark, commentColor, extra.accent, plainColor) {
        highlightKotlin(
            code = codeText,
            isDark = extra.isDark,
            commentColor = commentColor,
            accentColor = extra.accent,
            plainColor = plainColor
        )
    }

    val lines = codeText.lines()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scroll)
    ) {
        // Line Numbers
        Column(
            modifier = Modifier.padding(end = 12.dp),
            horizontalAlignment = Alignment.End
        ) {
            lines.forEachIndexed { i, _ ->
                Text(
                    text = (i + 1).toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = extra.muted,
                )
            }
        }

        // Code Content
        Text(
            text = highlightedCode,
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 12.sp,
                lineHeight = 18.sp,
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun highlightKotlin(
    code: String,
    isDark: Boolean,
    commentColor: Color,
    accentColor: Color,
    plainColor: Color
): AnnotatedString {
    return buildAnnotatedString {
        withStyle(SpanStyle(color = plainColor)) {
            val lines = code.lines()
            val keywordColor = if (isDark) Color(0xFFF43F5E) else Color(0xFFBE123C)
            val stringColor = if (isDark) Color(0xFF34D399) else Color(0xFF047857)

            lines.forEachIndexed { index, line ->
                var i = 0
                while (i < line.length) {
                    // Comments check
                    if (line.substring(i).startsWith("//") || line.substring(i).startsWith("/*") || line.substring(i).startsWith(" *") || line.substring(i).startsWith(" */")) {
                        withStyle(SpanStyle(color = commentColor)) {
                            append(line.substring(i))
                        }
                        break
                    }
                    // Strings check
                    if (line[i] == '"') {
                        val end = line.indexOf('"', i + 1)
                        if (end != -1) {
                            withStyle(SpanStyle(color = stringColor)) {
                                append(line.substring(i, end + 1))
                            }
                            i = end + 1
                            continue
                        }
                    }
                    // Word parsing
                    val wordMatch = Regex("^[a-zA-Z_][a-zA-Z0-9_]*").find(line.substring(i))
                    if (wordMatch != null) {
                        val word = wordMatch.value
                        val isKeyword = word in listOf("package", "import", "object", "val", "fun", "class", "listOf", "override", "interface", "enum", "return")
                        if (isKeyword) {
                            withStyle(SpanStyle(color = keywordColor, fontWeight = FontWeight.Bold)) {
                                append(word)
                            }
                        } else {
                            append(word)
                        }
                        i += word.length
                    } else {
                        append(line[i].toString())
                        i++
                    }
                }
                if (index < lines.lastIndex) {
                    append("\n")
                }
            }
        }
    }
}

@Composable
private fun DesignViewer(project: Project) {
    val scroll = rememberScrollState()
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "layout preview",
                style = MaterialTheme.typography.labelMedium,
                color = project.accent
            )
            Text(
                text = "scroll →",
                style = MaterialTheme.typography.labelSmall,
                color = extra.muted
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1f)
                .horizontalScroll(state = scroll),
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            project.screenshots.forEachIndexed { i, shot ->
                Column(
                    modifier = Modifier
                        .width(width = 200.dp)
                        .fillMaxHeight()
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .background(color = scheme.surface)
                        .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 12.dp))
                ) {
                    // Header frame
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "screenshot_${i + 1}.png",
                            style = MaterialTheme.typography.labelSmall,
                            color = extra.faint
                        )
                        Box(
                            modifier = Modifier
                                .size(size = 6.dp)
                                .clip(shape = CircleShape)
                                .background(color = project.accent)
                        )
                    }
                    Box(modifier = Modifier.fillMaxWidth().height(height = 1.dp).background(color = scheme.outline))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(weight = 1f)
                            .padding(all = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(resource = shot),
                            contentDescription = "${project.title} screenshot",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(shape = RoundedCornerShape(size = 6.dp))
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ConsoleBar(
    project: Project,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val uriHandler = LocalUriHandler.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
        ) {
            Text(
                text = "> Ready",
                style = MaterialTheme.typography.labelLarge,
                color = extra.accent
            )
        }

        if (project.link != null) {
            Row(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = 6.dp))
                    .clickable { uriHandler.openUri(project.link) }
                    .background(color = extra.accentSoft)
                    .border(width = 1.dp, color = BrandTagTag.copy(alpha = 0.5f), shape = RoundedCornerShape(size = 6.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 6.dp)
            ) {
                Text(
                    text = "Run",
                    style = MaterialTheme.typography.labelSmall,
                    color = BrandTagTag,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}