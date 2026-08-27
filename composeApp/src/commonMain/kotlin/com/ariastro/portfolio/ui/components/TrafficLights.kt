package com.ariastro.portfolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/** macOS style window dots, shared by the top bar and the builds IDE header. */
private val TRAFFIC_LIGHT_COLORS = listOf(
    Color(0xFFFF5F57),
    Color(0xFFFEBC2E),
    Color(0xFF28C840),
)

@Composable
fun TrafficLights(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
    ) {
        TRAFFIC_LIGHT_COLORS.forEach { color ->
            Box(
                modifier = Modifier
                    .size(size = 10.dp)
                    .clip(shape = CircleShape)
                    .background(color = color),
            )
        }
    }
}
