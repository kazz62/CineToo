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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserPreferencesRepositoryImpl(
    private val database: CineTooDatabase
) : UserPreferencesRepository {

    private val settingsQueries = database.appSettingsQueries
    private val userGenreQueries = database.userGenreQueries
    private val userPlatformQueries = database.userPlatformQueries

    private companion object {
        const val ONBOARDING_COMPLETED_KEY = "onboarding_completed"
    }

    override fun getUserPreferences(): Flow<UserPreferences?> {
        val genresFlow = getSelectedGenres()
        val platformsFlow = getSelectedPlatforms()

        return combine(genresFlow, platformsFlow) { genres, platforms ->
            if (genres.isEmpty() && platforms.isEmpty()) {
                null
            } else {
                UserPreferences(
                    favoriteGenres = genres,
                    streamingPlatforms = platforms
                )
            }
        }
    }

    override suspend fun hasCompletedOnboarding(): Boolean {
        return withContext(Dispatchers.Default) {
            settingsQueries.selectByKey(ONBOARDING_COMPLETED_KEY)
                .executeAsOneOrNull()
                ?.settingValue == "true"
        }
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        withContext(Dispatchers.Default) {
            settingsQueries.insert(
                key = ONBOARDING_COMPLETED_KEY,
                settingValue = completed.toString()
            )
        }
    }

    override fun getSelectedGenres(): Flow<List<Genre>> {
        return userGenreQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { userGenres ->
                userGenres.map { Genre(id = it.id.toInt(), name = it.name, emoji = it.emoji) }
            }
    }

    override suspend fun saveSelectedGenres(genres: List<Genre>) {
        withContext(Dispatchers.Default) {
            database.transaction {
                userGenreQueries.deleteAll()
                genres.forEach { genre ->
                    userGenreQueries.insert(
                        id = genre.id.toLong(),
                        name = genre.name,
                        emoji = genre.emoji
                    )
                }
            }
        }
    }

    override suspend fun clearSelectedGenres() {
        withContext(Dispatchers.Default) {
            userGenreQueries.deleteAll()
        }
    }

    override fun getSelectedPlatforms(): Flow<List<StreamingPlatform>> {
        return userPlatformQueries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { userPlatforms ->
                userPlatforms.map {
                    StreamingPlatform(
                        id = it.id.toInt(),
                        name = it.name,
                        logoPath = it.logoPath
                    )
                }
            }
    }

    override suspend fun saveSelectedPlatforms(platforms: List<StreamingPlatform>) {
        withContext(Dispatchers.Default) {
            database.transaction {
                userPlatformQueries.deleteAll()
                platforms.forEach { platform ->
                    userPlatformQueries.insert(
                        id = platform.id.toLong(),
                        name = platform.name,
                        logoPath = platform.logoPath
                    )
                }
            }
        }
    }

    override suspend fun clearSelectedPlatforms() {
        withContext(Dispatchers.Default) {
            userPlatformQueries.deleteAll()
        }
    }

    override suspend fun deleteAllData() {
        withContext(Dispatchers.Default) {
            database.transaction {
                userGenreQueries.deleteAll()
                userPlatformQueries.deleteAll()
                settingsQueries.deleteAll()
            }
        }
    }
}
