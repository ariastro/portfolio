package com.ariastro.portfolio.domain.repository

import com.ariastro.portfolio.domain.model.Profile
import com.ariastro.portfolio.domain.model.Project

/**
 * Contract for accessing portfolio content. The presentation layer only ever sees this
 * abstraction; the concrete implementation lives in the data layer.
 */
interface PortfolioRepository {
    fun getProfile(): Profile
    fun getProjects(): List<Project>
}
