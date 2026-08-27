package com.ariastro.portfolio.ui.sections.builds

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.domain.model.Project
import com.ariastro.portfolio.presentation.EditorMode
import com.ariastro.portfolio.ui.components.rememberHover
import com.ariastro.portfolio.ui.theme.PortfolioTheme

/** Inner file tab of the editor pane. Purely ephemeral view state, hence kept local. */
private enum class EditorTab {
    DOC,
    CODE,
}

/** Editor workspace: file tabs plus Doc/Code panes, and the Design pane per [editorMode]. */
@Composable
internal fun EditorArea(
    project: Project,
    editorMode: EditorMode,
    compact: Boolean,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    var activeTab by remember(project) { mutableStateOf(EditorTab.DOC) }
    val panelPad = if (compact) 10.dp else 14.dp

    Row(modifier = modifier) {
        if (editorMode == EditorMode.CODE || editorMode == EditorMode.SPLIT) {
            Column(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxHeight(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(state = rememberScrollState())
                        .background(color = scheme.surface.copy(alpha = 0.3f))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                ) {
                    EditorFileTab(
                        title = "README.md",
                        isActive = activeTab == EditorTab.DOC,
                        onClick = { activeTab = EditorTab.DOC },
                    )
                    EditorFileTab(
                        title = project.fileName,
                        isActive = activeTab == EditorTab.CODE,
                        onClick = { activeTab = EditorTab.CODE },
                    )
                }

                Divider()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1f)
                        .padding(all = panelPad),
                ) {
                    when (activeTab) {
                        EditorTab.DOC -> DocViewer(project = project)
                        EditorTab.CODE -> CodeViewer(project = project)
                    }
                }
            }
        }

        if (editorMode == EditorMode.SPLIT) {
            VerticalDivider()
        }

        if (editorMode == EditorMode.DESIGN || editorMode == EditorMode.SPLIT) {
            Box(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxHeight()
                    .padding(all = panelPad),
                contentAlignment = Alignment.Center,
            ) {
                DesignViewer(project = project, compact = compact)
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
    val hover = rememberHover()
    val background = when {
        isActive -> extra.codeBg
        hover.isHovered -> extra.accent.copy(alpha = 0.08f)
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 4.dp))
            .hoverable(interactionSource = hover.interactionSource)
            .clickable(
                interactionSource = hover.interactionSource,
                indication = null,
                role = Role.Tab,
                onClick = onClick,
            )
            .background(color = background)
            .border(
                width = 1.dp,
                color = if (isActive) scheme.outline else Color.Transparent,
                shape = RoundedCornerShape(size = 4.dp),
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 6.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = if (isActive) extra.accent else scheme.onSurface.copy(alpha = 0.6f),
        )
    }
}
