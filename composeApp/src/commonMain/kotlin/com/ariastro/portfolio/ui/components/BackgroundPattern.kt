package com.ariastro.portfolio.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.ui.theme.PortfolioTheme

@Composable
fun BackgroundPattern(modifier: Modifier = Modifier) {
    val extra = PortfolioTheme.extra
    val dot = if (extra.isDark) {
        Color.White.copy(alpha = 0.08f)
    } else {
        Color.Black.copy(alpha = 0.07f)
    }
    val glowA = extra.accent.copy(alpha = if (extra.isDark) 0.05f else 0.035f)
    val glowB = extra.accent.copy(alpha = if (extra.isDark) 0.03f else 0.02f)

    Canvas(modifier = modifier.fillMaxSize()) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(glowA, Color.Transparent),
                center = Offset(size.width * 0.88f, size.height * 0.08f),
                radius = size.minDimension * 0.55f,
            ),
            radius = size.minDimension * 0.55f,
            center = Offset(size.width * 0.88f, size.height * 0.08f),
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(glowB, Color.Transparent),
                center = Offset(size.width * 0.12f, size.height * 0.88f),
                radius = size.minDimension * 0.5f,
            ),
            radius = size.minDimension * 0.5f,
            center = Offset(size.width * 0.12f, size.height * 0.88f),
        )

        val step = 28.dp.toPx()
        val dotRadius = 1.1.dp.toPx()
        val canvasWidth = this@Canvas.size.width
        val canvasHeight = this@Canvas.size.height
        val points = mutableListOf<Offset>()
        var y = step
        while (y < canvasHeight) {
            var x = step
            while (x < canvasWidth) {
                points.add(Offset(x, y))
                x += step
            }
            y += step
        }
        if (points.isNotEmpty()) {
            drawPoints(
                points = points,
                pointMode = PointMode.Points,
                color = dot,
                strokeWidth = dotRadius * 2f,
                cap = StrokeCap.Round,
            )
        }
    }
}
