package com.kazz.cinetoo.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)

@Serializable
data class GenresResponseDto(
    val genres: List<GenreDto>
)
