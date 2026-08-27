package com.ariastro.portfolio.domain.model

/**
 * A shipped project. Intentionally framework free:
 * - accent color is referenced by [Brand], resolved to a concrete color in the UI layer
 * - screenshots carry an asset id, resolved to drawable resources in the UI layer
 */
data class Project(
    val id: String,
    val index: String,
    val title: String,
    val role: String,
    val blurb: String,
    val story: String,
    val highlights: List<String>,
    val year: String,
    val category: String,
    val link: String?,
    val linkType: LinkType,
    val stack: List<String>,
    val brand: Brand,
    val screenshots: List<Screenshot>,
    val status: String = DEFAULT_STATUS,
) {
    companion object {
        const val DEFAULT_STATUS = "SHIPPED"
    }
}
