package com.kazz.cinetoo.domain.model

data class UserPreferences(
    val favoriteGenres: List<Genre>,
    val streamingPlatforms: List<StreamingPlatform>
)
