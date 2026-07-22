package com.ariastro.portfolio.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.ui.components.StatusDot
import com.ariastro.portfolio.ui.theme.PortfolioTheme

@Composable
fun Panel(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 14.dp))
            .background(color = scheme.surface)
            .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 14.dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = title, style = MaterialTheme.typography.labelLarge, color = extra.muted)
            StatusDot(label = "ok")
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 1.dp)
                .background(color = scheme.outline)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 18.dp),
        ) {
            content()
        }
    }
}
