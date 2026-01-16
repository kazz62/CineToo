package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.repository.FavoritesRepository
import com.kazz.cinetoo.domain.repository.UserPreferencesRepository

class DeleteAllDataUseCase(
    private val favoritesRepository: FavoritesRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            favoritesRepository.deleteAllFavorites()
            userPreferencesRepository.deleteAllData()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
