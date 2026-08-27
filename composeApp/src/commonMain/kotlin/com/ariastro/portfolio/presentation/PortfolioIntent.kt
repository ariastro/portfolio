package com.ariastro.portfolio.presentation

import com.ariastro.portfolio.domain.model.Section

/** Everything the user (or the view on the user's behalf) can ask the store to do. */
sealed interface PortfolioIntent {

    /** Toggle between dark and light theme. */
    data object ToggleTheme : PortfolioIntent

    /** Select a project in the builds section by index. */
    data class SelectProject(val index: Int) : PortfolioIntent

    /** Change the IDE editor view mode. */
    data class ChangeEditorMode(val mode: EditorMode) : PortfolioIntent

    /** Open a project screenshot full-screen, by asset id. */
    data class OpenScreenshot(val assetId: String) : PortfolioIntent

    /** Dismiss the full-screen screenshot overlay. */
    data object CloseScreenshot : PortfolioIntent

    /** Navigate (scroll) to a page section. */
    data class NavigateToSection(val section: Section) : PortfolioIntent

    /** Reveal or collapse the older entries in the experience log. */
    data object ToggleAllExperience : PortfolioIntent

    /** Open an external link (mail, GitHub, Play Store, ...). */
    data class OpenLink(val url: String) : PortfolioIntent

    /** Reported by the view when a section's measured position changes. */
    data class SectionPositionChanged(val section: Section, val y: Int) : PortfolioIntent

    /** Reported by the view when the scroll viewport changes. */
    data class ViewportChanged(val position: Int, val max: Int) : PortfolioIntent
}
