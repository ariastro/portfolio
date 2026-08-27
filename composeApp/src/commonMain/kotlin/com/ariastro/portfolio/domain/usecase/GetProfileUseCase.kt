package com.ariastro.portfolio.domain.usecase

import com.ariastro.portfolio.domain.model.Profile
import com.ariastro.portfolio.domain.repository.PortfolioRepository

/** Returns the profile content shown in the hero, readme and connect sections. */
class GetProfileUseCase(
    private val repository: PortfolioRepository,
) {
    operator fun invoke(): Profile = repository.getProfile()
}
