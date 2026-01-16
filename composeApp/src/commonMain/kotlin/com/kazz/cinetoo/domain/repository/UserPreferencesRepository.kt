package com.kazz.cinetoo.domain.repository

import com.kazz.cinetoo.domain.model.Genre
import com.kazz.cinetoo.domain.model.StreamingPlatform
import com.kazz.cinetoo.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    fun getUserPreferences(): Flow<UserPreferences?>

    suspend fun saveGenres(genres: List<Genre>)

    suspend fun savePlatforms(platforms: List<StreamingPlatform>)

    suspend fun addGenre(genre: Genre)

    suspend fun removeGenre(genreId: Int)

    suspend fun addPlatform(platform: StreamingPlatform)

    suspend fun removePlatform(platformId: Int)

    suspend fun hasCompletedOnboarding(): Boolean

    suspend fun setOnboardingCompleted(completed: Boolean)

    suspend fun deleteAllData()
}
