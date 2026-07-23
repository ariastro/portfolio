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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.derivedStateOf
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
        val sectionPositions = remember { mutableStateMapOf<String, Int>() }
        val activeTab by remember {
            derivedStateOf {
                val scrollY = scroll.value
                if (scroll.maxValue > 0 && scrollY >= scroll.maxValue - 10) {
                    "connect"
                } else {
                    val offset = 150
                    sectionPositions.entries
                        .filter { it.value - offset <= scrollY }
                        .maxByOrNull { it.value }
                        ?.key ?: ""
                }
            }
        }
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
                            val newY = coords.positionInParent().y.roundToInt()
                            if (sectionPositions["readme"] != newY) {
                                sectionPositions["readme"] = newY
                            }
                        }
                ) {
                    ReadmeSection()
                }
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { coords ->
                            val newY = coords.positionInParent().y.roundToInt()
                            if (sectionPositions["builds"] != newY) {
                                sectionPositions["builds"] = newY
                            }
                        }
                ) {
                    BuildsSection()
                }
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { coords ->
                            val newY = coords.positionInParent().y.roundToInt()
                            if (sectionPositions["connect"] != newY) {
                                sectionPositions["connect"] = newY
                            }
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
                activeTab = activeTab,
                modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth(),
            )
        }
    }
}
