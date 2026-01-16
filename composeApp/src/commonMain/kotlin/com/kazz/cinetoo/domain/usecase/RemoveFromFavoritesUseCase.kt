package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.MediaType
import com.kazz.cinetoo.domain.repository.FavoritesRepository

class RemoveFromFavoritesUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(id: Int, type: MediaType): Result<Unit> {
        return try {
            favoritesRepository.removeFromFavorites(id, type)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
