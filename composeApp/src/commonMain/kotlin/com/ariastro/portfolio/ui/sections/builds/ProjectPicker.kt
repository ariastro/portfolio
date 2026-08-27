package com.ariastro.portfolio.ui.sections.builds

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.domain.model.Project
import com.ariastro.portfolio.ui.components.rememberHover
import com.ariastro.portfolio.ui.theme.PortfolioTheme

/** Desktop sidebar listing project files. */
@Composable
internal fun ProjectTree(
    projects: List<Project>,
    selectedIdx: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Column(
        modifier = modifier.padding(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(space = 4.dp),
    ) {
        Text(
            text = "src/main/projects",
            style = MaterialTheme.typography.labelLarge,
            color = scheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
        )

        projects.forEachIndexed { index, project ->
            val isSelected = index == selectedIdx
            val hover = rememberHover()
            val background = when {
                isSelected -> extra.accentSoft
                hover.isHovered -> extra.accent.copy(alpha = 0.08f)
                else -> Color.Transparent
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .hoverable(interactionSource = hover.interactionSource)
                    .clickable(
                        interactionSource = hover.interactionSource,
                        indication = null,
                        role = Role.Tab,
                        onClick = { onSelect(index) },
                    )
                    .background(color = background)
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = project.fileName,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isSelected) extra.accent else scheme.onSurface.copy(alpha = 0.8f),
                    maxLines = 1,
                )
            }
        }
    }
}

/** Narrow-screen horizontal file tabs, same selection behaviour as [ProjectTree]. */
@Composable
internal fun MobileFileTabs(
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
        projects.forEachIndexed { index, project ->
            val isSelected = index == selectedIdx
            val hover = rememberHover()
            val background = when {
                isSelected -> extra.accentSoft
                hover.isHovered -> extra.accent.copy(alpha = 0.08f)
                else -> Color.Transparent
            }
            Text(
                text = project.fileName,
                style = MaterialTheme.typography.labelLarge,
                color = if (isSelected) extra.accent else scheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = 6.dp))
                    .hoverable(interactionSource = hover.interactionSource)
                    .clickable(
                        interactionSource = hover.interactionSource,
                        indication = null,
                        role = Role.Tab,
                        onClick = { onSelect(index) },
                    )
                    .background(color = background)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
            )
        }
    }
}
