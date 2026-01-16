package com.kazz.cinetoo.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WatchProviderDto(
    @SerialName("provider_id")
    val providerId: Int,
    @SerialName("provider_name")
    val providerName: String,
    @SerialName("logo_path")
    val logoPath: String? = null
)

@Serializable
data class WatchProviderResultDto(
    val flatrate: List<WatchProviderDto> = emptyList(),
    val buy: List<WatchProviderDto> = emptyList(),
    val rent: List<WatchProviderDto> = emptyList()
)

@Serializable
data class WatchProvidersDto(
    val results: Map<String, WatchProviderResultDto> = emptyMap()
)
