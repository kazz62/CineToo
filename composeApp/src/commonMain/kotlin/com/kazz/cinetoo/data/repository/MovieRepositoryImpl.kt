package com.kazz.cinetoo.data.repository

import com.kazz.cinetoo.data.remote.api.TMDbApi
import com.kazz.cinetoo.data.remote.dto.toDomain
import com.kazz.cinetoo.domain.model.Genre
import com.kazz.cinetoo.domain.model.Movie
import com.kazz.cinetoo.domain.model.TVShow
import com.kazz.cinetoo.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: TMDbApi
) : MovieRepository {

    override suspend fun discoverMovies(
        genreIds: List<Int>,
        platformIds: List<Int>,
        page: Int
    ): List<Movie> {
        return try {
            val response = api.discoverMovies(genreIds, platformIds, page)
            response.results.mapNotNull { movieDto ->
                try {
                    // For discover endpoint, we need to fetch full details to get complete info
                    api.getMovieDetails(movieDto.id).toDomain()
                } catch (e: Exception) {
                    null // Skip movies that fail to load details
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun discoverTVShows(
        genreIds: List<Int>,
        platformIds: List<Int>,
        page: Int
    ): List<TVShow> {
        return try {
            val response = api.discoverTVShows(genreIds, platformIds, page)
            response.results.mapNotNull { tvShowDto ->
                try {
                    // For discover endpoint, we need to fetch full details to get complete info
                    api.getTVShowDetails(tvShowDto.id).toDomain()
                } catch (e: Exception) {
                    null // Skip TV shows that fail to load details
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return api.getMovieDetails(movieId).toDomain()
    }

    override suspend fun getTVShowDetails(tvShowId: Int): TVShow {
        return api.getTVShowDetails(tvShowId).toDomain()
    }

    override suspend fun getGenres(): List<Genre> {
        return try {
            val movieGenres = api.getMovieGenres().genres
            val tvGenres = api.getTVGenres().genres

            // Combine and deduplicate genres
            (movieGenres + tvGenres)
                .distinctBy { it.id }
                .map { it.toDomain() }
                .sortedBy { it.name }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
