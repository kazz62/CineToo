package com.kazz.cinetoo.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TVShowDetailsDto(
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
    val genres: List<GenreDto> = emptyList(),
    @SerialName("number_of_seasons")
    val numberOfSeasons: Int,
    val credits: CreditsDto? = null,
    @SerialName("watch/providers")
    val watchProviders: WatchProvidersDto? = null
)
