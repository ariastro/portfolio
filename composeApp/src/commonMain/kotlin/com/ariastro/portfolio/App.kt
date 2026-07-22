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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import kotlin.math.roundToInt
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
        var sectionPositions by remember { mutableStateOf(mapOf<String, Int>()) }
        val bg by animateColorAsState(
            targetValue = MaterialTheme.colorScheme.background,
            animationSpec = tween(durationMillis = 220),
            label = "bg",
        )

        fun go(sectionId: String) {
            val yPos = sectionPositions[sectionId] ?: return
            scope.launch {
                val max = scroll.maxValue
                if (max > 0) {
                    // Offset by TopBar height + spacing (e.g., 80dp)
                    val offset = 100 // Adjust this value if TopBar is larger/smaller
                    scroll.animateScrollTo((yPos - offset).coerceIn(0, max))
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = bg)
        ) {
            BackgroundPattern(modifier = Modifier.fillMaxSize())

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scroll),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(height = 64.dp))
                HeroSection()
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { coords ->
                            sectionPositions = sectionPositions + ("readme" to coords.positionInParent().y.roundToInt())
                        }
                ) {
                    ReadmeSection()
                }
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { coords ->
                            sectionPositions = sectionPositions + ("builds" to coords.positionInParent().y.roundToInt())
                        }
                ) {
                    BuildsSection()
                }
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { coords ->
                            sectionPositions = sectionPositions + ("connect" to coords.positionInParent().y.roundToInt())
                        }
                ) {
                    ConnectSection()
                }
            }

            TopBar(
                isDark = isDark,
                onToggleTheme = { isDark = !isDark },
                onNav = { tab ->
                    when (tab) {
                        "readme" -> go("readme")
                        "builds" -> go("builds")
                        "connect" -> go("connect")
                    }
                },
                modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth(),
            )
        }
    }
}
