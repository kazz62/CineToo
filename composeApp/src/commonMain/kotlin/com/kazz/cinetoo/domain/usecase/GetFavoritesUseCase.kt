package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.FavoriteItem
import com.kazz.cinetoo.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    operator fun invoke(): Flow<List<FavoriteItem>> {
        return favoritesRepository.getAllFavorites()
    }
}
