package com.kazz.cinetoo.domain.model

data class FavoriteItem(
    val id: Int,
    val type: MediaType,
    val posterPath: String?,
    val title: String,
    val note: String?
)
