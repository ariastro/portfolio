package com.ariastro.portfolio.ui.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.domain.model.Profile
import com.ariastro.portfolio.ui.components.MonoChipRow
import com.ariastro.portfolio.ui.components.Shell
import com.ariastro.portfolio.ui.theme.PortfolioTheme

/** Stateless readme/about section rendered from [Profile] domain data. */
@Composable
fun ReadmeSection(profile: Profile, modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra

    Shell(
        modifier = modifier.padding(vertical = 24.dp),
    ) {
        Panel(
            title = "README.md",
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "# About",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.accent,
                )
                Spacer(modifier = Modifier.height(height = 12.dp))
                profile.about.split("\n\n").forEachIndexed { i, paragraph ->
                    if (i > 0) Spacer(modifier = Modifier.height(height = 14.dp))
                    Text(
                        text = paragraph,
                        style = MaterialTheme.typography.bodyLarge,
                        color = scheme.onBackground,
                    )
                }
                Spacer(modifier = Modifier.height(height = 22.dp))
                Text(
                    text = "## stack",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.accent,
                )
                Spacer(modifier = Modifier.height(height = 4.dp))
                Text(
                    text = profile.stackNote,
                    style = MaterialTheme.typography.bodyMedium,
                    color = extra.muted,
                )
                Spacer(modifier = Modifier.height(height = 12.dp))
                MonoChipRow(items = profile.stack, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
