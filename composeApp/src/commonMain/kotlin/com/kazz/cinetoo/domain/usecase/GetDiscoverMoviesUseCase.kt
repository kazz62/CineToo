package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.Movie
import com.kazz.cinetoo.domain.repository.MovieRepository

class GetDiscoverMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        genres: List<Int>,
        platforms: List<Int>,
        page: Int = 1
    ): Result<List<Movie>> {
        return try {
            val movies = movieRepository.discoverMovies(
                genreIds = genres,
                platformIds = platforms,
                page = page
            )
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
