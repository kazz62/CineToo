package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.UserPreferences
import com.kazz.cinetoo.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

class GetUserPreferencesUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<UserPreferences?> {
        return userPreferencesRepository.getUserPreferences()
    }
}
