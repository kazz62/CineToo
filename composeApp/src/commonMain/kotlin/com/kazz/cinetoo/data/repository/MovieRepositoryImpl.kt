package com.kazz.cinetoo.data.repository

import com.kazz.cinetoo.data.remote.api.IMDbApi
import com.kazz.cinetoo.data.remote.dto.imdb.toDomain
import com.kazz.cinetoo.data.remote.dto.imdb.toDomainMovie
import com.kazz.cinetoo.data.remote.dto.imdb.toDomainTVShow
import com.kazz.cinetoo.domain.model.Genre
import com.kazz.cinetoo.domain.model.Movie
import com.kazz.cinetoo.domain.model.TVShow
import com.kazz.cinetoo.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: IMDbApi
) : MovieRepository {

    override suspend fun discoverMovies(
        genreIds: List<Int>,
        platformIds: List<Int>,
        page: Int
    ): List<Movie> {
        return try {
            // Convert genre IDs to genre names (from GenreData)
            val genreNames = mapGenreIdsToNames(genreIds)

            val response = api.getTitles(
                types = listOf("MOVIE", "SHORT"),
                genres = genreNames,
                minAggregateRating = 5.0,
                sortBy = "SORT_BY_POPULARITY",
                sortOrder = "DESC",
                limit = 20
            )

            response.titles
                .filter { it.type == "MOVIE" || it.type == "SHORT" }
                .mapNotNull { titleDto ->
                    try {
                        val domain = titleDto.toDomain()
                        if (domain is Movie) domain else null
                    } catch (e: Exception) {
                        null
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
            // Convert genre IDs to genre names
            val genreNames = mapGenreIdsToNames(genreIds)

            val response = api.getTitles(
                types = listOf("TV_SERIES", "TV_MINI_SERIES"),
                genres = genreNames,
                minAggregateRating = 5.0,
                sortBy = "SORT_BY_POPULARITY",
                sortOrder = "DESC",
                limit = 20
            )

            response.titles
                .filter { it.type == "TV_SERIES" || it.type == "TV_MINI_SERIES" }
                .mapNotNull { titleDto ->
                    try {
                        val domain = titleDto.toDomain()
                        if (domain is TVShow) domain else null
                    } catch (e: Exception) {
                        null
                    }
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Movie {
        // For IMDb API, we would need the IMDb ID (string like "tt1234567")
        // For now, this is a placeholder - in real implementation,
        // we should store the IMDb ID string when saving favorites
        val imdbId = "tt${movieId}" // Temporary conversion
        return api.getTitleDetails(imdbId).toDomainMovie()
    }

    override suspend fun getTVShowDetails(tvShowId: Int): TVShow {
        val imdbId = "tt${tvShowId}" // Temporary conversion
        return api.getTitleDetails(imdbId).toDomainTVShow()
    }

    override suspend fun getGenres(): List<Genre> {
        // IMDb API doesn't have a separate genres endpoint
        // We return the predefined genres from GenreData
        return emptyList()
    }

    // Helper function to map genre IDs to genre names for IMDb API
    private fun mapGenreIdsToNames(genreIds: List<Int>): List<String> {
        val genreMap = mapOf(
            28 to "Action",
            12 to "Adventure",
            16 to "Animation",
            35 to "Comedy",
            80 to "Crime",
            99 to "Documentary",
            18 to "Drama",
            10751 to "Family",
            14 to "Fantasy",
            36 to "History",
            27 to "Horror",
            10402 to "Music",
            9648 to "Mystery",
            10749 to "Romance",
            878 to "Sci-Fi",
            53 to "Thriller",
            10752 to "War",
            37 to "Western"
        )

        return genreIds.mapNotNull { genreMap[it] }
    }
}
