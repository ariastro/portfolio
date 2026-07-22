package com.ariastro.portfolio.ui.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.data.PortfolioData
import com.ariastro.portfolio.ui.components.Shell
import com.ariastro.portfolio.ui.theme.PortfolioTheme

@Composable
fun ConnectSection(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    val uriHandler = LocalUriHandler.current

    Shell(
        modifier = modifier.padding(top = 24.dp, bottom = 64.dp)
    ) {
        Panel(
            title = "connect.sh"
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$ ./connect --to \"Ari SWS\"",
                    style = MaterialTheme.typography.labelLarge,
                    color = extra.accent,
                )
                Spacer(modifier = Modifier.height(height = 16.dp))
                Text(
                    text = "Open to Android / KMP roles, freelance, collaboration.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = scheme.onBackground,
                )
                Spacer(modifier = Modifier.height(height = 20.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(space = 8.dp),
                ) {
                    ConnectLine(key = "email", value = PortfolioData.EMAIL) {
                        uriHandler.openUri("mailto:${PortfolioData.EMAIL}")
                    }
                    ConnectLine(key = "github", value = PortfolioData.GITHUB) {
                        uriHandler.openUri(PortfolioData.GITHUB)
                    }
                    ConnectLine(key = "linkedin", value = PortfolioData.LINKEDIN) {
                        uriHandler.openUri(PortfolioData.LINKEDIN)
                    }
                }
                Spacer(modifier = Modifier.height(height = 28.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "© ${PortfolioData.NAME}",
                        style = MaterialTheme.typography.labelMedium,
                        color = extra.faint,
                    )
                    Text(
                        text = "·",
                        style = MaterialTheme.typography.labelMedium,
                        color = extra.faint,
                    )
                    Text(
                        text = "Made with Kotlin",
                        style = MaterialTheme.typography.labelMedium,
                        color = extra.faint,
                    )
                }
            }
        }
    }
}

@Composable
private fun ConnectLine(key: String, value: String, onClick: () -> Unit) {
    val scheme = MaterialTheme.colorScheme
    val extra = PortfolioTheme.extra
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .background(color = extra.codeBg)
            .border(width = 1.dp, color = scheme.outline, shape = RoundedCornerShape(size = 10.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = key,
            style = MaterialTheme.typography.labelLarge,
            color = extra.faint,
            modifier = Modifier.width(width = 80.dp),
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            color = extra.accent,
            modifier = Modifier.weight(weight = 1f),
            maxLines = 2,
        )
    }
}
