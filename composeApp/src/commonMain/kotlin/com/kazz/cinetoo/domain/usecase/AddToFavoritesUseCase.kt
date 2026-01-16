package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.MediaType
import com.kazz.cinetoo.domain.repository.FavoritesRepository

class AddToFavoritesUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(
        id: Int,
        type: MediaType,
        posterPath: String?,
        title: String
    ): Result<Unit> {
        return try {
            favoritesRepository.addToFavorites(id, type, posterPath, title)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
