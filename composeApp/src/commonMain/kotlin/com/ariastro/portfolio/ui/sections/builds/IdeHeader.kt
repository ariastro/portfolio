package com.ariastro.portfolio.ui.sections.builds

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.ariastro.portfolio.presentation.EditorMode
import com.ariastro.portfolio.ui.components.TrafficLights
import com.ariastro.portfolio.ui.components.rememberHover
import com.ariastro.portfolio.ui.theme.PortfolioTheme

/** IDE window chrome: traffic lights, title and the Doc/Code/Design switcher. */
@Composable
internal fun IdeHeader(
    editorMode: EditorMode,
    onModeChange: (EditorMode) -> Unit,
    compact: Boolean,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val modes = EditorMode.entries

    @Composable
    fun ModeSwitcher() {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 6.dp))
                .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 6.dp))
                .background(color = scheme.surface),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            modes.forEach { mode ->
                val hover = rememberHover()
                val isSelected = editorMode == mode
                val background = when {
                    isSelected -> extra.accentSoft
                    hover.isHovered -> extra.accent.copy(alpha = 0.08f)
                    else -> Color.Transparent
                }
                Text(
                    text = mode.label,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isSelected) extra.accent else extra.muted,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(size = 4.dp))
                        .hoverable(interactionSource = hover.interactionSource)
                        .clickable(
                            interactionSource = hover.interactionSource,
                            indication = null,
                            role = Role.Tab,
                            onClick = { onModeChange(mode) },
                        )
                        .background(color = background)
                        .padding(
                            horizontal = if (compact) 8.dp else 10.dp,
                            vertical = 6.dp,
                        ),
                )
            }
        }
    }

    if (compact) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(space = 10.dp),
        ) {
            TrafficLights()
            ModeSwitcher()
        }
    } else {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
            ) {
                TrafficLights()
                Spacer(modifier = Modifier.width(width = 6.dp))
                Text(
                    text = "android-project-lab",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.muted,
                    maxLines = 1,
                )
            }
            ModeSwitcher()
        }
    }
}
