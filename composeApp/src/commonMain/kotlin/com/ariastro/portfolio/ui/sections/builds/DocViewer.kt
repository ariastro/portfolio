package com.ariastro.portfolio.ui.sections.builds

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ariastro.portfolio.domain.model.Project
import com.ariastro.portfolio.ui.components.MonoChipRow
import com.ariastro.portfolio.ui.theme.PortfolioTheme
import com.ariastro.portfolio.ui.theme.accentColor

/** Markdown-style project overview: title, meta pills, story, highlights and stack. */
@Composable
internal fun DocViewer(project: Project) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val scroll = rememberScrollState()
    val accent = project.brand.accentColor

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scroll)
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
    ) {
        Text(
            text = "# ${project.title}",
            style = MaterialTheme.typography.displayMedium,
            color = scheme.onSurface,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(state = rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            MetaPill(text = project.category, color = accent)
            MetaPill(text = project.year, color = extra.muted)
            MetaPill(text = project.role, color = extra.muted)
        }

        Text(
            text = project.story,
            style = MaterialTheme.typography.bodyLarge,
            color = scheme.onBackground,
            lineHeight = 24.sp,
        )

        Text(
            text = "## Key Highlights",
            style = MaterialTheme.typography.titleLarge,
            color = extra.accent,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        ) {
            project.highlights.forEach { highlight ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "-",
                        style = MaterialTheme.typography.bodyLarge,
                        color = accent,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = highlight,
                        style = MaterialTheme.typography.bodyLarge,
                        color = scheme.onBackground,
                    )
                }
            }
        }

        Text(
            text = "## Tech Stack",
            style = MaterialTheme.typography.titleLarge,
            color = extra.accent,
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
