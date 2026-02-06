package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.repository.MovieRepository
import com.kazz.cinetoo.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.first

/**
 * Get recommended titles (movies and TV shows) based on user's selected genres
 */
class GetRecommendedTitlesUseCase(
    private val movieRepository: MovieRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(): List<Any> {
        return try {
            // Get user's selected genres
            val selectedGenres = userPreferencesRepository.getSelectedGenres().first()
            val genreIds = selectedGenres.map { it.id }

            if (genreIds.isEmpty()) {
                // Fallback to popular if no genres selected
                emptyList()
            } else {
                // Mix movies and TV shows
                val movies = movieRepository.discoverMovies(
                    genreIds = genreIds,
                    platformIds = emptyList(),
                    page = 1
                )
                val tvShows = movieRepository.discoverTVShows(
                    genreIds = genreIds,
                    platformIds = emptyList(),
                    page = 1
                )

                // Mix and shuffle
                (movies + tvShows).shuffled().take(20)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
