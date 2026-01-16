package com.kazz.cinetoo.data.remote.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class TMDbApi(private val httpClient: HttpClient) {

    suspend fun discoverMovies(
        genreIds: List<Int>,
        platformIds: List<Int>,
        page: Int = 1
    ): HttpResponse {
        return httpClient.get("${ApiConstants.BASE_URL}discover/movie") {
            parameter("with_genres", genreIds.joinToString(","))
            if (platformIds.isNotEmpty()) {
                parameter("with_watch_providers", platformIds.joinToString(","))
            }
            parameter("sort_by", "popularity.desc")
            parameter("page", page)
        }
    }

    suspend fun discoverTVShows(
        genreIds: List<Int>,
        platformIds: List<Int>,
        page: Int = 1
    ): HttpResponse {
        return httpClient.get("${ApiConstants.BASE_URL}discover/tv") {
            parameter("with_genres", genreIds.joinToString(","))
            if (platformIds.isNotEmpty()) {
                parameter("with_watch_providers", platformIds.joinToString(","))
            }
            parameter("sort_by", "popularity.desc")
            parameter("page", page)
        }
    }

    suspend fun getMovieDetails(movieId: Int): HttpResponse {
        return httpClient.get("${ApiConstants.BASE_URL}movie/$movieId") {
            parameter("append_to_response", "credits,watch/providers")
        }
    }

    suspend fun getTVShowDetails(tvShowId: Int): HttpResponse {
        return httpClient.get("${ApiConstants.BASE_URL}tv/$tvShowId") {
            parameter("append_to_response", "credits,watch/providers")
        }
    }

    suspend fun getMovieGenres(): HttpResponse {
        return httpClient.get("${ApiConstants.BASE_URL}genre/movie/list")
    }

    suspend fun getTVGenres(): HttpResponse {
        return httpClient.get("${ApiConstants.BASE_URL}genre/tv/list")
    }
}
