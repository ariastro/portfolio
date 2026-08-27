package com.ariastro.portfolio.ui.sections.builds

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.domain.model.Project
import com.ariastro.portfolio.ui.components.screenshotResource
import com.ariastro.portfolio.ui.theme.PortfolioTheme
import com.ariastro.portfolio.ui.theme.accentColor
import org.jetbrains.compose.resources.painterResource

/** "Design" pane: a gallery of the project's screenshots framed as image files. */
@Composable
internal fun DesignViewer(
    project: Project,
    compact: Boolean = false,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val accent = project.brand.accentColor
    val shotWidth = if (compact) 180.dp else 220.dp
    val screenshots = remember(project) {
        project.screenshotIds.mapNotNull(::screenshotResource)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(space = 10.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "layout preview",
                style = MaterialTheme.typography.labelMedium,
                color = accent,
            )
            Text(
                text = "scroll →",
                style = MaterialTheme.typography.labelSmall,
                color = extra.muted,
            )
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(weight = 1f),
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = 2.dp),
        ) {
            itemsIndexed(
                items = screenshots,
                key = { index, _ -> "${project.id}-$index" },
            ) { i, shot ->
                val painter = painterResource(resource = shot)
                val intrinsic = painter.intrinsicSize
                val aspect = if (
                    intrinsic.width.isFinite() &&
                    intrinsic.height.isFinite() &&
                    intrinsic.width > 0f &&
                    intrinsic.height > 0f
                ) {
                    intrinsic.width / intrinsic.height
                } else {
                    9f / 19.5f
                }

                Column(
                    modifier = Modifier
                        .width(width = shotWidth)
                        .clip(shape = RoundedCornerShape(size = 12.dp))
                        .background(color = scheme.surface)
                        .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 12.dp)),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "screenshot_${i + 1}.webp",
                            style = MaterialTheme.typography.labelSmall,
                            color = extra.faint,
                            maxLines = 1,
                        )
                        Box(
                            modifier = Modifier
                                .size(size = 6.dp)
                                .clip(shape = CircleShape)
                                .background(color = accent),
                        )
                    }
                    Divider()
                    Image(
                        painter = painter,
                        contentDescription = "${project.title} screenshot",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(ratio = aspect)
                            .clip(shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)),
                    )
                }
            }
        }
    }
}
