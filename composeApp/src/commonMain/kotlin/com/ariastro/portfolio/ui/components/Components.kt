package com.ariastro.portfolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.ui.theme.PortfolioTheme

@Composable
fun MonoChip(text: String, modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(extra.codeBg)
            .border(1.dp, scheme.outline, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = extra.muted,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MonoChipRow(items: List<String>, modifier: Modifier = Modifier) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items.forEach { MonoChip(it) }
    }
}

@Composable
fun StatusDot(label: String, modifier: Modifier = Modifier) {
    val extra = PortfolioTheme.extra
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(extra.accent),
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = extra.accent,
        )
    }
}

@Composable
fun ThemeToggle(
    isDark: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scheme = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .size(36.dp)
            .clip(CircleShape)
            .border(1.dp, scheme.outline, CircleShape)
            .clickable(onClick = onToggle),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = if (isDark) Icons.Rounded.LightMode else Icons.Rounded.DarkMode,
            contentDescription = "Toggle theme",
            tint = scheme.onSurface,
            modifier = Modifier.size(16.dp),
        )
    }
}

@Composable
fun LinkText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val extra = PortfolioTheme.extra
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = extra.accent,
        modifier = modifier.clickable(onClick = onClick),
    )
}
