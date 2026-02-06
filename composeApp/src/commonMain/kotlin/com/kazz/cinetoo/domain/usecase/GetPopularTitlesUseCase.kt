package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.repository.MovieRepository

/**
 * Get most popular titles (movies and TV shows)
 */
class GetPopularTitlesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(includeMovies: Boolean = true, includeTVShows: Boolean = true): List<Any> {
        return try {
            val results = mutableListOf<Any>()

            if (includeMovies) {
                val movies = movieRepository.discoverMovies(
                    genreIds = emptyList(),
                    platformIds = emptyList(),
                    page = 1
                )
                results.addAll(movies)
            }

            if (includeTVShows) {
                val tvShows = movieRepository.discoverTVShows(
                    genreIds = emptyList(),
                    platformIds = emptyList(),
                    page = 1
                )
                results.addAll(tvShows)
            }

            // Already sorted by popularity from API
            results.take(20)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
