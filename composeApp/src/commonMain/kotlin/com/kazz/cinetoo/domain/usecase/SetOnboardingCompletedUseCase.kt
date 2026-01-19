package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.repository.UserPreferencesRepository

class SetOnboardingCompletedUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(completed: Boolean = true) {
        userPreferencesRepository.setOnboardingCompleted(completed)
    }
}
