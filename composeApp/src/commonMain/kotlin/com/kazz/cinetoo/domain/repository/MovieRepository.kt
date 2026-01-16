package com.kazz.cinetoo.domain.repository

import com.kazz.cinetoo.domain.model.Movie
import com.kazz.cinetoo.domain.model.TVShow

interface MovieRepository {
    suspend fun discoverMovies(
        genreIds: List<Int>,
        platformIds: List<Int>,
        page: Int = 1
    ): List<Movie>

    suspend fun discoverTVShows(
        genreIds: List<Int>,
        platformIds: List<Int>,
        page: Int = 1
    ): List<TVShow>

    suspend fun getMovieDetails(movieId: Int): Movie

    suspend fun getTVShowDetails(tvShowId: Int): TVShow

    suspend fun getGenres(): List<com.kazz.cinetoo.domain.model.Genre>
}
