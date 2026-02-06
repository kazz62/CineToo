package com.kazz.cinetoo.data.remote.dto.imdb

import kotlinx.serialization.Serializable

@Serializable
data class CreditsResponseDto(
    val cast: List<CastMemberDto> = emptyList(),
    val crew: List<CrewMemberDto> = emptyList()
)

@Serializable
data class CastMemberDto(
    val id: String,
    val name: String,
    val characters: List<String> = emptyList(),
    val profileImage: ImageDto? = null,
    val order: Int? = null
)

@Serializable
data class CrewMemberDto(
    val id: String,
    val name: String,
    val job: String,
    val department: String,
    val profileImage: ImageDto? = null
)
