package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.Genre
import com.kazz.cinetoo.domain.repository.MovieRepository

class GetGenresUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): Result<List<Genre>> {
        return try {
            val genres = movieRepository.getGenres()
            Result.success(genres)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
