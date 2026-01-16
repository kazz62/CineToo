package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.TVShow
import com.kazz.cinetoo.domain.repository.MovieRepository

class GetDiscoverTVShowsUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(
        genres: List<Int>,
        platforms: List<Int>,
        page: Int = 1
    ): Result<List<TVShow>> {
        return try {
            val tvShows = movieRepository.discoverTVShows(
                genreIds = genres,
                platformIds = platforms,
                page = page
            )
            Result.success(tvShows)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
