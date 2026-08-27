package com.ariastro.portfolio.presentation

/**
 * Which document the builds-section editor is showing.
 *
 * Previously this also encoded a SPLIT mode, which overlapped with the editor's own inner
 * file tabs — "Code mode, README tab" was a reachable contradiction. Now one switcher owns
 * the choice, and wide layouts show the design pane alongside whatever is selected.
 */
enum class EditorMode(val label: String) {
    DOC("Doc"),
    CODE("Code"),
    DESIGN("Design"),
}
