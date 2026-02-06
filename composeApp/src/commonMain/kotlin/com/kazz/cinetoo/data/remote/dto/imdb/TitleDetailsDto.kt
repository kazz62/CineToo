package com.kazz.cinetoo.data.remote.dto.imdb

import kotlinx.serialization.Serializable

@Serializable
data class TitleDetailsDto(
    val id: String,
    val type: String,
    val isAdult: Boolean = false,
    val primaryTitle: String,
    val originalTitle: String,
    val primaryImage: ImageDto? = null,
    val startYear: Int? = null,
    val endYear: Int? = null,
    val runtimeSeconds: Int? = null,
    val genres: List<String> = emptyList(),
    val rating: RatingDto? = null,
    val metacritic: MetacriticDto? = null,
    val plot: PlotDto? = null,
    val directors: List<PersonDto> = emptyList(),
    val writers: List<PersonDto> = emptyList(),
    val stars: List<PersonDto> = emptyList(),
    val originCountries: List<String> = emptyList(),
    val spokenLanguages: List<String> = emptyList()
)

@Serializable
data class PersonDto(
    val id: String,
    val name: String,
    val profileImage: ImageDto? = null
)
