package com.kazz.cinetoo.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastMemberDto(
    val id: Int,
    val name: String,
    val character: String? = null,
    @SerialName("profile_path")
    val profilePath: String? = null
)

@Serializable
data class CrewMemberDto(
    val id: Int,
    val name: String,
    val job: String,
    val department: String
)

@Serializable
data class CreditsDto(
    val cast: List<CastMemberDto> = emptyList(),
    val crew: List<CrewMemberDto> = emptyList()
)
