package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.MediaType
import com.kazz.cinetoo.domain.repository.FavoritesRepository

class SaveNoteUseCase(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(
        id: Int,
        type: MediaType,
        note: String
    ): Result<Unit> {
        return try {
            favoritesRepository.saveNote(id, type, note)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
