package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.repository.UserPreferencesRepository

class HasCompletedOnboardingUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(): Boolean {
        return userPreferencesRepository.hasCompletedOnboarding()
    }
}
