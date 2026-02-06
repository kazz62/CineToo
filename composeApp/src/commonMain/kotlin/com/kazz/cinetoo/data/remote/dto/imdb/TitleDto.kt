package com.kazz.cinetoo.data.remote.dto.imdb

import kotlinx.serialization.Serializable

@Serializable
data class TitlesResponseDto(
    val titles: List<TitleDto> = emptyList(),
    val nextPageToken: String? = null
)

@Serializable
data class TitleDto(
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
    val plot: PlotDto? = null
)

@Serializable
data class ImageDto(
    val url: String,
    val width: Int? = null,
    val height: Int? = null
)

@Serializable
data class RatingDto(
    val averageRating: Double? = null,
    val numVotes: Int? = null
)

@Serializable
data class MetacriticDto(
    val metaScore: Int? = null
)

@Serializable
data class PlotDto(
    val text: String? = null,
    val language: String? = null
)
