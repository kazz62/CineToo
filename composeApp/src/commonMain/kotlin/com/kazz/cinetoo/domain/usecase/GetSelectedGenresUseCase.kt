package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.Genre
import com.kazz.cinetoo.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetSelectedGenresUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<List<Genre>> {
        return userPreferencesRepository.getSelectedGenres()
    }
}
