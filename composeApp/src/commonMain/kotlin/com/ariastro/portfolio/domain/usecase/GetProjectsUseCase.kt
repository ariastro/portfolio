package com.ariastro.portfolio.domain.usecase

import com.ariastro.portfolio.domain.model.Project
import com.ariastro.portfolio.domain.repository.PortfolioRepository

/** Returns the shipped projects shown in the builds section. */
class GetProjectsUseCase(
    private val repository: PortfolioRepository,
) {
    operator fun invoke(): List<Project> = repository.getProjects()
}
