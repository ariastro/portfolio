package com.ariastro.portfolio.presentation

import com.ariastro.portfolio.domain.model.Profile
import com.ariastro.portfolio.domain.model.Project
import com.ariastro.portfolio.domain.model.Section

/**
 * Single immutable UI state for the whole portfolio screen (MVI).
 * The UI renders exactly this and nothing else; every mutation goes through [PortfolioStore].
 */
data class PortfolioUiState(
    val profile: Profile,
    val projects: List<Project>,
    val isDark: Boolean = true,
    val selectedProjectIndex: Int = 0,
    // Split by default: visitors see the doc AND the screenshots immediately on wide screens.
    // Narrow layouts fall back to Code automatically (see BuildsSection).
    val editorMode: EditorMode = EditorMode.SPLIT,
    // Collapsed by default: only the recent roles show until the visitor asks for the full log.
    val showAllExperience: Boolean = false,
    val activeSection: Section? = null,
    val scrollProgress: Float = 0f,
) {
    val selectedProject: Project?
        get() = projects.getOrNull(selectedProjectIndex)
}
