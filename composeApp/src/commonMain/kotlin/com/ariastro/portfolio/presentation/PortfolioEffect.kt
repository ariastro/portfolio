package com.ariastro.portfolio.presentation

/** One-shot side effects the view must execute (MVI). Never kept in state. */
sealed interface PortfolioEffect {

    /** Animate the viewport to the given vertical offset. */
    data class ScrollTo(val y: Int) : PortfolioEffect

    /** Open the URL with the platform uri handler. */
    data class OpenLink(val url: String) : PortfolioEffect
}
