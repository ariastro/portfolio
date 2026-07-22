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
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.ui.components.Shell
import com.ariastro.portfolio.ui.components.ThemeToggle
import com.ariastro.portfolio.ui.theme.PortfolioTheme

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
        modifier = modifier
            .fillMaxWidth()
            .background(color = scheme.background),
    ) {
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
                    Row(horizontalArrangement = Arrangement.spacedBy(space = 6.dp)) {
                        listOf(0xFFFF5F57, 0xFFFEBC2E, 0xFF28C840).forEach { c ->
                            Box(
                                modifier = Modifier
                                    .size(size = 10.dp)
                                    .clip(shape = CircleShape)
                                    .background(color = androidx.compose.ui.graphics.Color(color = c)),
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
                    horizontalArrangement = Arrangement.spacedBy(space = 4.dp),
                ) {
                    listOf("readme", "builds", "connect").forEach { tab ->
                        Text(
                            text = tab,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (tab == "builds") extra.accent else extra.muted,
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(size = 6.dp))
                                .clickable { onNav(tab) }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                        )
                    }
                    Spacer(modifier = Modifier.width(width = 6.dp))
                    ThemeToggle(isDark = isDark, onToggle = onToggleTheme)
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth().height(height = 1.dp).background(color = scheme.outline))
    }
}
