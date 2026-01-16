package com.kazz.cinetoo.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.kazz.cinetoo.data.local.database.currentTimeMillis
import com.kazz.cinetoo.database.CineTooDatabase
import com.kazz.cinetoo.domain.model.FavoriteItem
import com.kazz.cinetoo.domain.model.MediaType
import com.kazz.cinetoo.domain.repository.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class FavoritesRepositoryImpl(
    private val database: CineTooDatabase
) : FavoritesRepository {

    private val favoriteQueries = database.favoriteQueries
    private val noteQueries = database.noteQueries

    override fun getAllFavorites(): Flow<List<FavoriteItem>> {
        return favoriteQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { favorites ->
                favorites.map { favorite ->
                    FavoriteItem(
                        id = favorite.id.toInt(),
                        type = MediaType.valueOf(favorite.type),
                        posterPath = favorite.posterPath,
                        title = favorite.title,
                        note = getNote(favorite.id.toInt(), MediaType.valueOf(favorite.type))
                    )
                }
            }
    }

    override suspend fun addToFavorites(
        id: Int,
        type: MediaType,
        posterPath: String?,
        title: String
    ) {
        withContext(Dispatchers.Default) {
            favoriteQueries.insert(
                id = id.toLong(),
                type = type.name,
                posterPath = posterPath,
                title = title,
                addedAt = currentTimeMillis()
            )
        }
    }

    override suspend fun removeFromFavorites(id: Int, type: MediaType) {
        withContext(Dispatchers.Default) {
            favoriteQueries.delete(id.toLong(), type.name)
            noteQueries.delete(id.toLong(), type.name)
        }
    }

    override suspend fun isFavorite(id: Int, type: MediaType): Boolean {
        return withContext(Dispatchers.Default) {
            favoriteQueries.isFavorite(id.toLong(), type.name).executeAsOne()
        }
    }

    override suspend fun saveNote(id: Int, type: MediaType, note: String) {
        withContext(Dispatchers.Default) {
            noteQueries.insert(
                id = id.toLong(),
                type = type.name,
                content = note,
                updatedAt = currentTimeMillis()
            )
        }
    }

    override suspend fun getNote(id: Int, type: MediaType): String? {
        return withContext(Dispatchers.Default) {
            noteQueries.selectByIdAndType(id.toLong(), type.name)
                .executeAsOneOrNull()
                ?.content
        }
    }

    override suspend fun deleteAllFavorites() {
        withContext(Dispatchers.Default) {
            favoriteQueries.deleteAll()
            noteQueries.deleteAll()
        }
    }
}
