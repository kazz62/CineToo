package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.Genre
import com.kazz.cinetoo.domain.repository.UserPreferencesRepository

class SaveSelectedGenresUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(genres: List<Genre>) {
        userPreferencesRepository.saveSelectedGenres(genres)
    }
}
