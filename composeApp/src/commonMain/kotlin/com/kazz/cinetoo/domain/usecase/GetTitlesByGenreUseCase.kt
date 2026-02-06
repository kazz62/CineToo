package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.Genre
import com.kazz.cinetoo.domain.repository.MovieRepository

/**
 * Get titles filtered by a specific genre
 */
class GetTitlesByGenreUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(genre: Genre): List<Any> {
        return try {
            // Get both movies and TV shows for the genre
            val movies = movieRepository.discoverMovies(
                genreIds = listOf(genre.id),
                platformIds = emptyList(),
                page = 1
            )

            val tvShows = movieRepository.discoverTVShows(
                genreIds = listOf(genre.id),
                platformIds = emptyList(),
                page = 1
            )

            // Mix and shuffle
            (movies + tvShows).shuffled().take(20)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
