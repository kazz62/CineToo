package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.StreamingPlatform
import com.kazz.cinetoo.domain.repository.UserPreferencesRepository

class SaveSelectedPlatformsUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(platforms: List<StreamingPlatform>) {
        userPreferencesRepository.saveSelectedPlatforms(platforms)
    }
}
