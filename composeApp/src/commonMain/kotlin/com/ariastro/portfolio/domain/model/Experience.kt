package com.ariastro.portfolio.domain.model

/**
 * A single work-experience entry, rendered as a commit in the `experience.log` section.
 * Kept framework free; the decorative commit hash is derived in the UI layer.
 */
data class Experience(
    val role: String,
    val company: String,
    val period: String,
    val summary: String,
    val highlights: List<String> = emptyList(),
)
