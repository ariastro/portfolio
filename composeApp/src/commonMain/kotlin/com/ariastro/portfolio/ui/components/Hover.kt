package com.ariastro.portfolio.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember

/**
 * Hover affordance for the IDE mock on the web.
 *
 * Pairs a [MutableInteractionSource] with a live [isHovered] flag so interactive elements can
 * react to the pointer before it clicks. Attach the source with both `Modifier.hoverable(...)`
 * and `Modifier.clickable(interactionSource = ..., ...)` on the same element.
 */
internal class HoverState(
    val interactionSource: MutableInteractionSource,
    private val hovered: State<Boolean>,
) {
    val isHovered: Boolean get() = hovered.value
}

@Composable
internal fun rememberHover(): HoverState {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered = interactionSource.collectIsHoveredAsState()
    return remember(interactionSource) { HoverState(interactionSource, hovered) }
}
