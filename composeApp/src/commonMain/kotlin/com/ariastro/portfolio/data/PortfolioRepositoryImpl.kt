package com.ariastro.portfolio.data

import com.ariastro.portfolio.domain.model.Profile
import com.ariastro.portfolio.domain.model.Project
import com.ariastro.portfolio.domain.repository.PortfolioRepository

/**
 * Repository backed by the bundled static [PortfolioContent].
 * Swap this implementation for a remote/local data source without touching
 * the domain or presentation layers.
 */
class PortfolioRepositoryImpl : PortfolioRepository {

    override fun getProfile(): Profile = PortfolioContent.profile

    override fun getProjects(): List<Project> = PortfolioContent.projects
}
