package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.Movie
import com.kazz.cinetoo.domain.model.TVShow
import com.kazz.cinetoo.domain.repository.MovieRepository

/**
 * Get new releases (recent titles from current and previous year)
 */
class GetNewReleasesTitlesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<Any> {
        return try {
            // For now, consider 2024-2026 as recent
            val currentYear = 2026

            // Get recent movies
            val movies = movieRepository.discoverMovies(
                genreIds = emptyList(),
                platformIds = emptyList(),
                page = 1
            ).filter { movie ->
                val year = movie.releaseDate.take(4).toIntOrNull()
                year != null && year >= currentYear - 1
            }

            // Get recent TV shows
            val tvShows = movieRepository.discoverTVShows(
                genreIds = emptyList(),
                platformIds = emptyList(),
                page = 1
            ).filter { tvShow ->
                val year = tvShow.firstAirDate.take(4).toIntOrNull()
                year != null && year >= currentYear - 1
            }

            // Mix and sort by year descending
            (movies + tvShows)
                .sortedByDescending { title ->
                    when (title) {
                        is Movie -> title.releaseDate.take(4).toIntOrNull() ?: 0
                        is TVShow -> title.firstAirDate.take(4).toIntOrNull() ?: 0
                        else -> 0
                    }
                }
                .take(20)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
