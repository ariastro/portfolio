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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.ariastro.portfolio.data.PortfolioRepositoryImpl
import com.ariastro.portfolio.domain.model.Section
import com.ariastro.portfolio.domain.repository.PortfolioRepository
import com.ariastro.portfolio.domain.usecase.GetProfileUseCase
import com.ariastro.portfolio.domain.usecase.GetProjectsUseCase
import com.ariastro.portfolio.presentation.PortfolioEffect
import com.ariastro.portfolio.presentation.PortfolioIntent
import com.ariastro.portfolio.presentation.PortfolioStore
import com.ariastro.portfolio.ui.components.BackgroundPattern
import com.ariastro.portfolio.ui.sections.ConnectSection
import com.ariastro.portfolio.ui.sections.ExperienceSection
import com.ariastro.portfolio.ui.sections.HeroSection
import com.ariastro.portfolio.ui.sections.ReadmeSection
import com.ariastro.portfolio.ui.sections.TopBar
import com.ariastro.portfolio.ui.sections.builds.BuildsSection
import com.ariastro.portfolio.ui.theme.PortfolioTheme
import kotlin.math.roundToInt
import kotlinx.coroutines.flow.collectLatest

/**
 * Composition root: builds the dependency graph (data -> domain <- presentation)
 * and connects the MVI [PortfolioStore] to the stateless UI.
 * The composable itself holds no business state — everything lives in the store.
 */
@Composable
fun App() {
    val store = remember {
        val repository: PortfolioRepository = PortfolioRepositoryImpl()
        PortfolioStore(
            getProfile = GetProfileUseCase(repository),
            getProjects = GetProjectsUseCase(repository),
        )
    }
    val state by store.state.collectAsState()

    PortfolioTheme(darkTheme = state.isDark) {
        val scroll = rememberScrollState()
        val uriHandler = LocalUriHandler.current

        // Report viewport changes to the store (single source of truth for active section + progress).
        LaunchedEffect(scroll) {
            snapshotFlow { scroll.value to scroll.maxValue }
                .collectLatest { (position, max) ->
                    store.dispatch(PortfolioIntent.ViewportChanged(position = position, max = max))
                }
        }

        // Execute one-shot side effects emitted by the store.
        LaunchedEffect(store) {
            store.effects.collectLatest { effect ->
                when (effect) {
                    is PortfolioEffect.ScrollTo ->
                        scroll.animateScrollTo(effect.y.coerceIn(0, scroll.maxValue))

                    is PortfolioEffect.OpenLink ->
                        uriHandler.openUri(effect.url)
                }
            }
        }

        val bg by animateColorAsState(
            targetValue = MaterialTheme.colorScheme.background,
            animationSpec = tween(durationMillis = 220),
            label = "bg",
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = bg),
        ) {
            BackgroundPattern(modifier = Modifier.fillMaxSize())

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(state = scroll),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(height = 64.dp))

                HeroSection(profile = state.profile)

                SectionAnchor(section = Section.README, store = store) {
                    ReadmeSection(profile = state.profile)
                }
                SectionAnchor(section = Section.EXPERIENCE, store = store) {
                    ExperienceSection(
                        profile = state.profile,
                        showAll = state.showAllExperience,
                        onToggleShowAll = { store.dispatch(PortfolioIntent.ToggleAllExperience) },
                    )
                }
                SectionAnchor(section = Section.BUILDS, store = store) {
                    BuildsSection(
                        projects = state.projects,
                        selectedProjectIndex = state.selectedProjectIndex,
                        editorMode = state.editorMode,
                        onSelectProject = { store.dispatch(PortfolioIntent.SelectProject(it)) },
                        onEditorModeChange = { store.dispatch(PortfolioIntent.ChangeEditorMode(it)) },
                        onOpenLink = { store.dispatch(PortfolioIntent.OpenLink(it)) },
                    )
                }
                SectionAnchor(section = Section.CONNECT, store = store) {
                    ConnectSection(
                        profile = state.profile,
                        onOpenLink = { store.dispatch(PortfolioIntent.OpenLink(it)) },
                    )
                }
            }

            TopBar(
                isDark = state.isDark,
                activeSection = state.activeSection,
                progress = state.scrollProgress,
                onToggleTheme = { store.dispatch(PortfolioIntent.ToggleTheme) },
                onNavigate = { store.dispatch(PortfolioIntent.NavigateToSection(it)) },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth(),
            )
        }
    }
}

/**
 * Wraps a section and reports its measured position inside the scrollable column to the store.
 * Layout knowledge stays in the view layer; the store only receives plain numbers.
 */
@Composable
private fun SectionAnchor(
    section: Section,
    store: PortfolioStore,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.onGloballyPositioned { coords ->
            store.dispatch(
                PortfolioIntent.SectionPositionChanged(
                    section = section,
                    y = coords.positionInParent().y.roundToInt(),
                ),
            )
        },
    ) {
        content()
    }
}
