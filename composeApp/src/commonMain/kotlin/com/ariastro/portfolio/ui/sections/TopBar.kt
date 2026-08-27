package com.ariastro.portfolio.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.domain.model.Section
import com.ariastro.portfolio.ui.components.Shell
import com.ariastro.portfolio.ui.components.ThemeToggle
import com.ariastro.portfolio.ui.components.TrafficLights
import com.ariastro.portfolio.ui.theme.PortfolioTheme

/** Stateless top bar. All state comes in as parameters, all events go out as callbacks. */
@Composable
fun TopBar(
    isDark: Boolean,
    activeSection: Section?,
    progress: Float,
    onToggleTheme: () -> Unit,
    onNavigate: (Section) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = scheme.background),
    ) {
        if (progress > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = progress)
                    .height(height = 2.dp)
                    .background(color = extra.accent),
            )
        }
        Shell {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
                ) {
                    TrafficLights()
                    Text(
                        text = "portfolio.kt",
                        style = MaterialTheme.typography.labelLarge,
                        color = extra.muted,
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
                ) {
                    Section.entries.forEach { section ->
                        Text(
                            text = section.label,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (section == activeSection) extra.accent else extra.muted,
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(size = 6.dp))
                                .clickable(role = Role.Tab) { onNavigate(section) }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(width = 6.dp))
                    ThemeToggle(isDark = isDark, onToggle = onToggleTheme)
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 1.dp)
                .background(color = scheme.outline),
        )
    }
}
