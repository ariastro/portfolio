package com.ariastro.portfolio.ui.sections.builds

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.domain.model.Project
import com.ariastro.portfolio.presentation.EditorMode
import com.ariastro.portfolio.ui.components.Shell
import com.ariastro.portfolio.ui.theme.PortfolioTheme

/** File name shown in the IDE mock for a project, e.g. "MyXL.kt". */
internal val Project.fileName: String
    get() = "${title.replace(oldValue = " ", newValue = "")}.kt"

/**
 * Stateless builds section. Selection, editor mode and link handling are owned by the MVI store;
 * the composable only renders state and forwards events.
 *
 * This file is only the layout entry point; each sub-component lives in its own file
 * inside this package (IdeHeader, ProjectPicker, EditorArea, viewers, ConsoleBar).
 */
@Composable
fun BuildsSection(
    projects: List<Project>,
    selectedProjectIndex: Int,
    editorMode: EditorMode,
    onSelectProject: (Int) -> Unit,
    onEditorModeChange: (EditorMode) -> Unit,
    onOpenLink: (String) -> Unit,
    onOpenScreenshot: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val project = projects.getOrNull(selectedProjectIndex) ?: return

    Shell(
        modifier = modifier.padding(vertical = 24.dp),
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val wide = maxWidth >= 760.dp
            val compact = maxWidth < 480.dp
            // A taller cage means the story needs inner-scrolling less often.
            val workspaceHeight = if (compact) 540.dp else 660.dp
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(size = 14.dp))
                    .background(color = extra.codeBg)
                    .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 14.dp)),
            ) {
                IdeHeader(
                    editorMode = editorMode,
                    onModeChange = onEditorModeChange,
                    compact = compact,
                )

                Divider()

                if (wide) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = workspaceHeight),
                    ) {
                        ProjectTree(
                            projects = projects,
                            selectedIdx = selectedProjectIndex,
                            onSelect = onSelectProject,
                            modifier = Modifier
                                .width(width = 200.dp)
                                .fillMaxHeight()
                                .background(color = scheme.surface.copy(alpha = 0.5f)),
                        )

                        VerticalDivider()

                        EditorArea(
                            project = project,
                            editorMode = editorMode,
                            compact = false,
                            designAlongside = true,
                            onOpenScreenshot = onOpenScreenshot,
                            modifier = Modifier
                                .weight(weight = 1f)
                                .fillMaxHeight(),
                        )
                    }
                } else {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        MobileFileTabs(
                            projects = projects,
                            selectedIdx = selectedProjectIndex,
                            onSelect = onSelectProject,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = scheme.surface.copy(alpha = 0.5f)),
                        )

                        Divider()

                        EditorArea(
                            project = project,
                            editorMode = editorMode,
                            compact = compact,
                            designAlongside = false,
                            onOpenScreenshot = onOpenScreenshot,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height = workspaceHeight),
                        )
                    }
                }

                Divider()

                ConsoleBar(
                    project = project,
                    selectedIndex = selectedProjectIndex,
                    projectCount = projects.size,
                    onSelectProject = onSelectProject,
                    onOpenLink = onOpenLink,
                    compact = compact,
                )
            }
        }
    }
}

/** Thin horizontal hairline used across the builds IDE mock. */
@Composable
internal fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 1.dp)
            .background(color = MaterialTheme.colorScheme.outline),
    )
}

/** Thin vertical hairline used across the builds IDE mock. */
@Composable
internal fun VerticalDivider() {
    Box(
        modifier = Modifier
            .width(width = 1.dp)
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.outline),
    )
}
