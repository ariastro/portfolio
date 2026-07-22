package com.ariastro.portfolio.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.sp
import com.ariastro.portfolio.data.PortfolioData
import com.ariastro.portfolio.ui.components.Shell
import com.ariastro.portfolio.ui.components.StatusDot
import com.ariastro.portfolio.ui.theme.PortfolioTheme

@Composable
fun HeroSection(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Shell(
        modifier = modifier.padding(top = 40.dp, bottom = 48.dp)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val wide = maxWidth >= 760.dp

            if (wide) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(space = 28.dp),
                ) {
                    CodeWindow(modifier = Modifier.weight(weight = 1.15f))
                    HeroMeta(modifier = Modifier.weight(weight = 0.85f))
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(space = 24.dp)) {
                    CodeWindow(modifier = Modifier.fillMaxWidth())
                    HeroMeta(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
private fun CodeWindow(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 14.dp))
            .background(color = extra.codeBg)
            .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 14.dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = scheme.surface.copy(alpha = 0.5f))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Main.kt", style = MaterialTheme.typography.labelLarge, color = extra.muted)
            StatusDot(label = "BUILD SUCCESSFUL")
        }
        Box(modifier = Modifier.fillMaxWidth().height(height = 1.dp).background(color = scheme.outline))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 2.dp),
        ) {
            PortfolioData.heroLines.forEachIndexed { i, line ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
                ) {
                    Text(
                        text = (i + 1).toString().padStart(length = 2, padChar = ' '),
                        style = MaterialTheme.typography.labelSmall,
                        color = extra.faint,
                    )
                    Text(
                        text = line.ifEmpty { " " },
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 13.sp,
                            lineHeight = 22.sp,
                        ),
                        color = when {
                            line.startsWith(prefix = "package") -> extra.muted
                            line.startsWith(prefix = "/**") || line.startsWith(prefix = " *") || line.startsWith(prefix = " */") ->
                                extra.faint
                            line.contains(other = "fun ") || line == "}" -> scheme.onBackground
                            line.trim().endsWith(suffix = "()") -> extra.accent
                            else -> scheme.onBackground
                        },
                        modifier = Modifier.weight(weight = 1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun HeroMeta(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(space = 16.dp)) {
        Text(
            text = PortfolioData.HANDLE,
            style = MaterialTheme.typography.labelMedium,
            color = extra.accent,
        )
        Text(
            text = PortfolioData.FULL_NAME,
            style = MaterialTheme.typography.displayLarge,
            color = scheme.onBackground,
        )
        Text(
            text = PortfolioData.TITLE,
            style = MaterialTheme.typography.headlineLarge,
            color = extra.muted,
        )
        Text(
            text = "I ship production Android systems — not demos. " +
                "From telecom scale to personal side builds.",
            style = MaterialTheme.typography.bodyLarge,
            color = extra.muted,
        )
        Spacer(modifier = Modifier.height(height = 4.dp))
        FlowFacts()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FlowFacts() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(space = 10.dp),
        verticalArrangement = Arrangement.spacedBy(space = 10.dp),
    ) {
        PortfolioData.facts.forEach { (value, label) ->
            FactCell(value = value, label = label)
        }
    }
}

@Composable
private fun FactCell(value: String, label: String) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(color = scheme.surface)
            .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 12.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
    ) {
        Text(text = value, style = MaterialTheme.typography.titleLarge, color = extra.accent)
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = extra.faint)
    }
}
