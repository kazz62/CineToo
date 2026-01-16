package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.Genre
import com.kazz.cinetoo.domain.model.StreamingPlatform
import com.kazz.cinetoo.domain.repository.UserPreferencesRepository

class SaveUserPreferencesUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(
        genres: List<Genre>,
        platforms: List<StreamingPlatform>
    ): Result<Unit> {
        return try {
            userPreferencesRepository.saveGenres(genres)
            userPreferencesRepository.savePlatforms(platforms)
            userPreferencesRepository.setOnboardingCompleted(true)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
