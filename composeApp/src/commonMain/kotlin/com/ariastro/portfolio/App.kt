package com.ariastro.portfolio

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.ui.components.BackgroundPattern
import com.ariastro.portfolio.ui.sections.BuildsSection
import com.ariastro.portfolio.ui.sections.ConnectSection
import com.ariastro.portfolio.ui.sections.HeroSection
import com.ariastro.portfolio.ui.sections.ReadmeSection
import com.ariastro.portfolio.ui.sections.TopBar
import com.ariastro.portfolio.ui.theme.PortfolioTheme
import kotlinx.coroutines.launch

@Composable
fun App() {
    var isDark by remember { mutableStateOf(true) }

    PortfolioTheme(darkTheme = isDark) {
        val scroll = rememberScrollState()
        val scope = rememberCoroutineScope()
        val bg by animateColorAsState(
            MaterialTheme.colorScheme.background,
            tween(220),
            label = "bg",
        )

        fun go(fraction: Float) {
            scope.launch {
                scroll.animateScrollTo(
                    (scroll.maxValue * fraction).toInt().coerceIn(0, scroll.maxValue),
                )
            }
        }

        Box(Modifier.fillMaxSize().background(bg)) {
            BackgroundPattern(Modifier.fillMaxSize())

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scroll),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(64.dp))
                HeroSection()
                ReadmeSection()
                BuildsSection()
                ConnectSection()
            }

            TopBar(
                isDark = isDark,
                onToggleTheme = { isDark = !isDark },
                onNav = { tab ->
                    when (tab) {
                        "readme" -> go(0.22f)
                        "builds" -> go(0.38f)
                        "connect" -> go(1f)
                    }
                },
                modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth(),
            )
        }
    }
}
