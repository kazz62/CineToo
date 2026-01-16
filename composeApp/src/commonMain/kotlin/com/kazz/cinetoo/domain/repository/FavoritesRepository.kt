package com.kazz.cinetoo.domain.repository

import com.kazz.cinetoo.domain.model.FavoriteItem
import com.kazz.cinetoo.domain.model.MediaType
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAllFavorites(): Flow<List<FavoriteItem>>

    suspend fun addToFavorites(
        id: Int,
        type: MediaType,
        posterPath: String?,
        title: String
    )

    suspend fun removeFromFavorites(id: Int, type: MediaType)

    suspend fun isFavorite(id: Int, type: MediaType): Boolean

    suspend fun saveNote(id: Int, type: MediaType, note: String)

    suspend fun getNote(id: Int, type: MediaType): String?

    suspend fun deleteAllFavorites()
}
