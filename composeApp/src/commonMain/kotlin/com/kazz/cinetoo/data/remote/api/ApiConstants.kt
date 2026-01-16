package com.kazz.cinetoo.data.remote.api

object ApiConstants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    const val POSTER_SIZE = "w500"
    const val BACKDROP_SIZE = "original"

    fun getPosterUrl(path: String?): String? {
        return path?.let { "$IMAGE_BASE_URL$POSTER_SIZE$it" }
    }

    fun getBackdropUrl(path: String?): String? {
        return path?.let { "$IMAGE_BASE_URL$BACKDROP_SIZE$it" }
    }
}
