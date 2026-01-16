package com.kazz.cinetoo.data.remote.api

import com.kazz.cinetoo.data.remote.dto.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class TMDbApi(private val httpClient: HttpClient) {

    suspend fun discoverMovies(
        genreIds: List<Int>,
        platformIds: List<Int>,
        page: Int = 1
    ): DiscoverMoviesResponseDto {
        return httpClient.get("${ApiConstants.BASE_URL}discover/movie") {
            parameter("with_genres", genreIds.joinToString(","))
            if (platformIds.isNotEmpty()) {
                parameter("with_watch_providers", platformIds.joinToString(","))
            }
            parameter("sort_by", "popularity.desc")
            parameter("page", page)
        }.body()
    }

    suspend fun discoverTVShows(
        genreIds: List<Int>,
        platformIds: List<Int>,
        page: Int = 1
    ): DiscoverTVShowsResponseDto {
        return httpClient.get("${ApiConstants.BASE_URL}discover/tv") {
            parameter("with_genres", genreIds.joinToString(","))
            if (platformIds.isNotEmpty()) {
                parameter("with_watch_providers", platformIds.joinToString(","))
            }
            parameter("sort_by", "popularity.desc")
            parameter("page", page)
        }.body()
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetailsDto {
        return httpClient.get("${ApiConstants.BASE_URL}movie/$movieId") {
            parameter("append_to_response", "credits,watch/providers")
        }.body()
    }

    suspend fun getTVShowDetails(tvShowId: Int): TVShowDetailsDto {
        return httpClient.get("${ApiConstants.BASE_URL}tv/$tvShowId") {
            parameter("append_to_response", "credits,watch/providers")
        }.body()
    }

    suspend fun getMovieGenres(): GenresResponseDto {
        return httpClient.get("${ApiConstants.BASE_URL}genre/movie/list").body()
    }

    suspend fun getTVGenres(): GenresResponseDto {
        return httpClient.get("${ApiConstants.BASE_URL}genre/tv/list").body()
    }
}
