package com.kazz.cinetoo.data.remote.api

import com.kazz.cinetoo.data.remote.dto.imdb.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class IMDbApi(private val httpClient: HttpClient) {

    suspend fun getTitles(
        types: List<String>? = null,
        genres: List<String>? = null,
        minAggregateRating: Double? = null,
        maxAggregateRating: Double? = null,
        startYear: Int? = null,
        endYear: Int? = null,
        sortBy: String? = "SORT_BY_POPULARITY",
        sortOrder: String? = "DESC",
        pageToken: String? = null,
        limit: Int = 20
    ): TitlesResponseDto {
        return httpClient.get("${ApiConstants.BASE_URL}titles") {
            types?.let { parameter("types", it.joinToString(",")) }
            genres?.let { parameter("genres", it.joinToString(",")) }
            minAggregateRating?.let { parameter("minAggregateRating", it) }
            maxAggregateRating?.let { parameter("maxAggregateRating", it) }
            startYear?.let { parameter("startYear", it) }
            endYear?.let { parameter("endYear", it) }
            sortBy?.let { parameter("sortBy", it) }
            sortOrder?.let { parameter("sortOrder", it) }
            pageToken?.let { parameter("pageToken", it) }
            parameter("limit", limit)
        }.body()
    }

    suspend fun getTitleDetails(titleId: String): TitleDetailsDto {
        return httpClient.get("${ApiConstants.BASE_URL}titles/$titleId").body()
    }

    suspend fun getTitleCredits(titleId: String): CreditsResponseDto {
        return httpClient.get("${ApiConstants.BASE_URL}titles/$titleId/credits").body()
    }

    suspend fun searchTitles(
        query: String,
        types: List<String>? = null,
        limit: Int = 20,
        pageToken: String? = null
    ): TitlesResponseDto {
        return httpClient.get("${ApiConstants.BASE_URL}search/titles") {
            parameter("query", query)
            types?.let { parameter("types", it.joinToString(",")) }
            parameter("limit", limit)
            pageToken?.let { parameter("pageToken", it) }
        }.body()
    }
}
