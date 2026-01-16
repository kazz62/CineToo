package com.kazz.cinetoo.domain.model

data class TVShow(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val firstAirDate: String,
    val voteAverage: Double,
    val genres: List<Genre>,
    val numberOfSeasons: Int,
    val cast: List<CastMember>,
    val availableOn: List<StreamingPlatform>
)
