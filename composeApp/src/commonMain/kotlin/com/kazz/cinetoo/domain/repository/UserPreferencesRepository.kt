package com.kazz.cinetoo.domain.repository

import com.kazz.cinetoo.domain.model.Genre
import com.kazz.cinetoo.domain.model.StreamingPlatform
import com.kazz.cinetoo.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun hasCompletedOnboarding(): Boolean
    suspend fun setOnboardingCompleted(completed: Boolean)

    // Aggregate preferences
    fun getUserPreferences(): Flow<UserPreferences?>

    // Genres
    fun getSelectedGenres(): Flow<List<Genre>>
    suspend fun saveSelectedGenres(genres: List<Genre>)
    suspend fun clearSelectedGenres()

    // Legacy API (kept for compatibility with existing use cases)
    suspend fun saveGenres(genres: List<Genre>) = saveSelectedGenres(genres)

    // Platforms
    fun getSelectedPlatforms(): Flow<List<StreamingPlatform>>
    suspend fun saveSelectedPlatforms(platforms: List<StreamingPlatform>)
    suspend fun clearSelectedPlatforms()

    // Legacy API (kept for compatibility with existing use cases)
    suspend fun savePlatforms(platforms: List<StreamingPlatform>) = saveSelectedPlatforms(platforms)

    suspend fun deleteAllData()
}
