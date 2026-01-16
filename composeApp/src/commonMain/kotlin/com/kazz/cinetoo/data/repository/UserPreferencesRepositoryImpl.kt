package com.kazz.cinetoo.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.kazz.cinetoo.database.CineTooDatabase
import com.kazz.cinetoo.domain.model.Genre
import com.kazz.cinetoo.domain.model.StreamingPlatform
import com.kazz.cinetoo.domain.model.UserPreferences
import com.kazz.cinetoo.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

class UserPreferencesRepositoryImpl(
    private val database: CineTooDatabase
) : UserPreferencesRepository {

    private val genreQueries = database.userGenreQueries
    private val platformQueries = database.userPlatformQueries
    private val settingsQueries = database.appSettingsQueries

    private companion object {
        const val ONBOARDING_COMPLETED_KEY = "onboarding_completed"
    }

    override fun getUserPreferences(): Flow<UserPreferences?> {
        val genresFlow = genreQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default)

        val platformsFlow = platformQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default)

        return combine(genresFlow, platformsFlow) { genres, platforms ->
            if (genres.isEmpty() && platforms.isEmpty()) {
                null
            } else {
                UserPreferences(
                    favoriteGenres = genres.map { genre ->
                        Genre(
                            id = genre.id.toInt(),
                            name = genre.name,
                            emoji = genre.emoji
                        )
                    },
                    streamingPlatforms = platforms.map { platform ->
                        StreamingPlatform(
                            id = platform.id.toInt(),
                            name = platform.name,
                            logoPath = platform.logoPath
                        )
                    }
                )
            }
        }
    }

    override suspend fun saveGenres(genres: List<Genre>) {
        withContext(Dispatchers.Default) {
            database.transaction {
                genreQueries.deleteAll()
                genres.forEach { genre ->
                    genreQueries.insert(
                        id = genre.id.toLong(),
                        name = genre.name,
                        emoji = genre.emoji
                    )
                }
            }
        }
    }

    override suspend fun savePlatforms(platforms: List<StreamingPlatform>) {
        withContext(Dispatchers.Default) {
            database.transaction {
                platformQueries.deleteAll()
                platforms.forEach { platform ->
                    platformQueries.insert(
                        id = platform.id.toLong(),
                        name = platform.name,
                        logoPath = platform.logoPath
                    )
                }
            }
        }
    }

    override suspend fun addGenre(genre: Genre) {
        withContext(Dispatchers.Default) {
            genreQueries.insert(
                id = genre.id.toLong(),
                name = genre.name,
                emoji = genre.emoji
            )
        }
    }

    override suspend fun removeGenre(genreId: Int) {
        withContext(Dispatchers.Default) {
            genreQueries.delete(genreId.toLong())
        }
    }

    override suspend fun addPlatform(platform: StreamingPlatform) {
        withContext(Dispatchers.Default) {
            platformQueries.insert(
                id = platform.id.toLong(),
                name = platform.name,
                logoPath = platform.logoPath
            )
        }
    }

    override suspend fun removePlatform(platformId: Int) {
        withContext(Dispatchers.Default) {
            platformQueries.delete(platformId.toLong())
        }
    }

    override suspend fun hasCompletedOnboarding(): Boolean {
        return withContext(Dispatchers.Default) {
            settingsQueries.selectByKey(ONBOARDING_COMPLETED_KEY)
                .executeAsOneOrNull()
                ?.value == "true"
        }
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        withContext(Dispatchers.Default) {
            settingsQueries.insert(
                key = ONBOARDING_COMPLETED_KEY,
                value = completed.toString()
            )
        }
    }

    override suspend fun deleteAllData() {
        withContext(Dispatchers.Default) {
            database.transaction {
                genreQueries.deleteAll()
                platformQueries.deleteAll()
                settingsQueries.deleteAll()
            }
        }
    }
}
