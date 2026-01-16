package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.TVShow
import com.kazz.cinetoo.domain.repository.MovieRepository

class GetTVShowDetailsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(tvShowId: Int): Result<TVShow> {
        return try {
            val tvShow = movieRepository.getTVShowDetails(tvShowId)
            Result.success(tvShow)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
