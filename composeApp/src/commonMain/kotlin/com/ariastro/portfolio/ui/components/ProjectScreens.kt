package com.ariastro.portfolio.ui.components

import com.ariastro.portfolio.resources.Res
import com.ariastro.portfolio.resources.project_myxl_1
import com.ariastro.portfolio.resources.project_myxl_2
import com.ariastro.portfolio.resources.project_schoolryde_1
import com.ariastro.portfolio.resources.project_schoolryde_2
import com.ariastro.portfolio.resources.project_schoolryde_3
import com.ariastro.portfolio.resources.project_schoolryde_4
import com.ariastro.portfolio.resources.project_superagree_1
import com.ariastro.portfolio.resources.project_superagree_2
import com.ariastro.portfolio.resources.project_tagtag_1
import com.ariastro.portfolio.resources.project_tagtag_2
import com.ariastro.portfolio.resources.project_tagtag_3
import com.ariastro.portfolio.resources.project_trackfit_1
import com.ariastro.portfolio.resources.project_trackfit_2
import com.ariastro.portfolio.resources.project_trackfit_3
import org.jetbrains.compose.resources.DrawableResource

/**
 * Resolves domain screenshot ids to drawable resources. This is the only place that knows both
 * worlds, keeping domain models framework free.
 */
private val SCREENSHOT_ASSETS: Map<String, DrawableResource> = mapOf(
    "myxl_1" to Res.drawable.project_myxl_1,
    "myxl_2" to Res.drawable.project_myxl_2,
    "schoolryde_1" to Res.drawable.project_schoolryde_1,
    "schoolryde_2" to Res.drawable.project_schoolryde_2,
    "schoolryde_3" to Res.drawable.project_schoolryde_3,
    "schoolryde_4" to Res.drawable.project_schoolryde_4,
    "tagtag_1" to Res.drawable.project_tagtag_1,
    "tagtag_2" to Res.drawable.project_tagtag_2,
    "tagtag_3" to Res.drawable.project_tagtag_3,
    "superagree_1" to Res.drawable.project_superagree_1,
    "superagree_2" to Res.drawable.project_superagree_2,
    "trackfit_1" to Res.drawable.project_trackfit_1,
    "trackfit_2" to Res.drawable.project_trackfit_2,
    "trackfit_3" to Res.drawable.project_trackfit_3,
)

fun screenshotResource(id: String): DrawableResource? = SCREENSHOT_ASSETS[id]
