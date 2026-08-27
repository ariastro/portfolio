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
                    // Drop any open overlay: its screenshot belongs to the previous project.
                    current.copy(selectedProjectIndex = intent.index, expandedScreenshotId = null)
                } else {
                    current
                }
            }

            is PortfolioIntent.ChangeEditorMode -> _state.update { it.copy(editorMode = intent.mode) }

            is PortfolioIntent.OpenScreenshot ->
                _state.update { it.copy(expandedScreenshotId = intent.assetId) }

            is PortfolioIntent.CloseScreenshot ->
                _state.update { it.copy(expandedScreenshotId = null) }

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
            if (isAtBottom()) {
                lastSectionByPosition()
            } else {
                sectionPositions.entries
                    .filter { it.value - ACTIVE_SECTION_OFFSET <= lastScrollPosition }
                    .maxByOrNull { it.value }
                    ?.key
            }
        _state.update { it.copy(activeSection = activeSection) }
    }

    /**
     * Whether the viewport has effectively reached the end of the content.
     *
     * The tolerance is proportional to the scroll range rather than a fixed pixel count:
     * browsers settle on fractional scroll offsets and can come to rest a few pixels short of
     * max, which an absolute window of a few pixels misses outright — leaving the final section
     * permanently un-highlighted on exactly the page position where it fills the screen.
     */
    private fun isAtBottom(): Boolean {
        if (lastScrollMax <= 0) return false
        val tolerance = (lastScrollMax * BOTTOM_SNAP_FRACTION)
            .toInt()
            .coerceAtLeast(minimumValue = BOTTOM_SNAP_MIN_PX)
        return lastScrollPosition >= lastScrollMax - tolerance
    }

    /**
     * The section furthest down the page, derived from measured positions rather than named
     * outright, so reordering [Section] cannot leave this pointing at the wrong one.
     */
    private fun lastSectionByPosition(): Section? =
        sectionPositions.maxByOrNull { it.value }?.key

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

        /** Share of the scroll range treated as "at the bottom". */
        const val BOTTOM_SNAP_FRACTION = 0.02f

        /** Floor for the bottom tolerance, so short pages still have a usable window. */
        const val BOTTOM_SNAP_MIN_PX = 24
    }
}
