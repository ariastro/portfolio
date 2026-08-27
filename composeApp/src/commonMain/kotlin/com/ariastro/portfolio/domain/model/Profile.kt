package com.ariastro.portfolio.domain.model

/** Everything that describes the portfolio owner. Pure domain data, no framework types. */
data class Profile(
    val fullName: String,
    val displayName: String,
    val handle: String,
    val title: String,
    val tagline: String,
    val email: String,
    val links: List<ProfileLink>,
    val heroCodeLines: List<String>,
    val about: String,
    val stackNote: String,
    val stack: List<String>,
    val facts: List<Fact>,
    val experiences: List<Experience> = emptyList(),
    val availability: String,
    val footerNote: String,
)

/** A contact/social entry. [value] is what the user sees, [url] is what gets opened. */
data class ProfileLink(
    val label: String,
    val value: String,
    val url: String,
)
