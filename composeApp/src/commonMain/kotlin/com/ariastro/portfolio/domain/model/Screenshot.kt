package com.ariastro.portfolio.domain.model

/**
 * One showcase image for a [Project].
 *
 * [assetId] is resolved to a drawable in the UI layer, keeping this model framework free.
 * [caption] states what the screen actually shows — without it the gallery is a row of
 * unlabelled phones, and a visitor cannot tell a dashboard from a checkout.
 */
data class Screenshot(
    val assetId: String,
    val caption: String,
)
