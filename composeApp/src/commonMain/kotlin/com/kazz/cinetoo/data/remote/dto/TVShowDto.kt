package com.kazz.cinetoo.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TVShowDto(
    val id: Int,
    val name: String,
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("first_air_date")
    val firstAirDate: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList()
)

@Serializable
data class DiscoverTVShowsResponseDto(
    val page: Int,
    val results: List<TVShowDto>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)
