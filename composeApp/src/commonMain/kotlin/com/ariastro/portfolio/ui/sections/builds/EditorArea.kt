package com.ariastro.portfolio.ui.sections.builds

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.domain.model.Project
import com.ariastro.portfolio.presentation.EditorMode

/**
 * Editor workspace: the document chosen by [editorMode], plus the design pane.
 *
 * On wide layouts ([designAlongside]) the design pane is always visible next to the document,
 * which is what the old SPLIT mode used to provide — except it is now a property of the layout
 * rather than a fourth thing for the visitor to choose.
 */
@Composable
internal fun EditorArea(
    project: Project,
    editorMode: EditorMode,
    compact: Boolean,
    designAlongside: Boolean,
    onOpenScreenshot: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val panelPad = if (compact) 10.dp else 14.dp
    val showDocument = editorMode != EditorMode.DESIGN
    val showDesign = editorMode == EditorMode.DESIGN || designAlongside

    Row(modifier = modifier) {
        if (showDocument) {
            Column(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxHeight()
                    .padding(all = panelPad),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 1f),
                ) {
                    when (editorMode) {
                        EditorMode.DOC -> DocViewer(project = project)
                        EditorMode.CODE -> CodeViewer(project = project)
                        EditorMode.DESIGN -> Unit
                    }
                }
            }
        }

        if (showDocument && showDesign) {
            VerticalDivider()
        }

        if (showDesign) {
            Box(
                modifier = Modifier
                    .weight(weight = 1f)
                    .fillMaxHeight()
                    .padding(all = panelPad),
            ) {
                DesignViewer(
                    project = project,
                    compact = compact,
                    onOpenScreenshot = onOpenScreenshot,
                )
            }
        }
    }
}
