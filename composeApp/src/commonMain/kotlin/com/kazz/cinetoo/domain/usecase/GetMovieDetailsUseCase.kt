package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.Movie
import com.kazz.cinetoo.domain.repository.MovieRepository

class GetMovieDetailsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int): Result<Movie> {
        return try {
            val movie = movieRepository.getMovieDetails(movieId)
            Result.success(movie)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
