package com.kazz.cinetoo.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val genres: List<Genre>,
    val director: String?,
    val duration: Int?,
    val cast: List<CastMember>,
    val availableOn: List<StreamingPlatform>
)
