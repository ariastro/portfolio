package com.ariastro.portfolio.presentation

import com.ariastro.portfolio.domain.model.Section
import com.ariastro.portfolio.domain.usecase.GetProfileUseCase
import com.ariastro.portfolio.domain.usecase.GetProjectsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

/**
 * MVI store: the single state holder of the portfolio screen.
 *
 * - State flows down: [state] is the only source of truth the UI renders.
 * - Events flow up: the UI only calls [dispatch] with [PortfolioIntent]s.
 * - One-shot actions surface as [effects] (scrolling, opening links).
 *
 * The store depends only on the domain layer (use cases), never on data or UI.
 */
class PortfolioStore(
    getProfile: GetProfileUseCase,
    getProjects: GetProjectsUseCase,
) {

    private val _state = MutableStateFlow(
        PortfolioUiState(
            profile = getProfile(),
            projects = getProjects(),
        ),
    )
    val state: StateFlow<PortfolioUiState> = _state.asStateFlow()

    private val _effects = Channel<PortfolioEffect>(Channel.BUFFERED)
    val effects: Flow<PortfolioEffect> = _effects.receiveAsFlow()

    /** Layout knowledge reported by the view; used to resolve navigation targets and the active section. */
    private val sectionPositions = mutableMapOf<Section, Int>()
    private var lastScrollPosition = 0
    private var lastScrollMax = 0

    fun dispatch(intent: PortfolioIntent) {
        when (intent) {
            is PortfolioIntent.ToggleTheme -> _state.update { it.copy(isDark = !it.isDark) }

            is PortfolioIntent.SelectProject -> _state.update { current ->
                if (intent.index in current.projects.indices) {
                    current.copy(selectedProjectIndex = intent.index)
                } else {
                    current
                }
            }

            is PortfolioIntent.ChangeEditorMode -> _state.update { it.copy(editorMode = intent.mode) }

            is PortfolioIntent.ToggleAllExperience ->
                _state.update { it.copy(showAllExperience = !it.showAllExperience) }

            is PortfolioIntent.NavigateToSection -> navigateToSection(intent.section)

            is PortfolioIntent.OpenLink -> emit(PortfolioEffect.OpenLink(intent.url))

            is PortfolioIntent.SectionPositionChanged -> {
                if (sectionPositions[intent.section] != intent.y) {
                    sectionPositions[intent.section] = intent.y
                    updateActiveSection()
                }
            }

            is PortfolioIntent.ViewportChanged -> {
                lastScrollPosition = intent.position
                lastScrollMax = intent.max
                updateScrollProgress()
                updateActiveSection()
            }
        }
    }

    private fun navigateToSection(section: Section) {
        val y = sectionPositions[section] ?: return
        emit(PortfolioEffect.ScrollTo(y = (y - SECTION_TOP_OFFSET).coerceAtLeast(0)))
    }

    private fun updateActiveSection() {
        val activeSection =
            if (lastScrollMax > 0 && lastScrollPosition >= lastScrollMax - BOTTOM_SNAP_THRESHOLD) {
                // Snap to the last section when the viewport reached the bottom.
                Section.CONNECT
            } else {
                sectionPositions.entries
                    .filter { it.value - ACTIVE_SECTION_OFFSET <= lastScrollPosition }
                    .maxByOrNull { it.value }
                    ?.key
            }
        _state.update { it.copy(activeSection = activeSection) }
    }

    private fun updateScrollProgress() {
        val progress =
            if (lastScrollMax > 0) {
                (lastScrollPosition.toFloat() / lastScrollMax.toFloat()).coerceIn(0f, 1f)
            } else {
                0f
            }
        _state.update { it.copy(scrollProgress = progress) }
    }

    private fun emit(effect: PortfolioEffect) {
        _effects.trySend(effect)
    }

    private companion object {
        /** Space kept above a section when scrolling to it (top bar height + spacing). */
        const val SECTION_TOP_OFFSET = 100

        /** How far below the top edge a section counts as "active". */
        const val ACTIVE_SECTION_OFFSET = 150

        /** Distance from the bottom at which the last section becomes active. */
        const val BOTTOM_SNAP_THRESHOLD = 10
    }
}
