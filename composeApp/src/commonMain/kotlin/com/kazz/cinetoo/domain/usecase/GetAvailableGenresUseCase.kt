package com.kazz.cinetoo.domain.usecase

import com.kazz.cinetoo.domain.model.Genre
import com.kazz.cinetoo.domain.model.GenreData

class GetAvailableGenresUseCase {
    operator fun invoke(): List<Genre> {
        return GenreData.availableGenres
    }
}
