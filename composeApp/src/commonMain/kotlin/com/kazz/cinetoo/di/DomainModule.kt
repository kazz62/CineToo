package com.kazz.cinetoo.di

import com.kazz.cinetoo.domain.usecase.*
import org.koin.dsl.module

fun domainModule() = module {
    // Movie & TV Show Use Cases
    factory { GetDiscoverMoviesUseCase(get()) }
    factory { GetDiscoverTVShowsUseCase(get()) }
    factory { GetMovieDetailsUseCase(get()) }
    factory { GetTVShowDetailsUseCase(get()) }
    factory { GetGenresUseCase(get()) }

    // Favorites Use Cases
    factory { AddToFavoritesUseCase(get()) }
    factory { RemoveFromFavoritesUseCase(get()) }
    factory { GetFavoritesUseCase(get()) }
    factory { SaveNoteUseCase(get()) }

    // User Preferences Use Cases
    factory { SaveUserPreferencesUseCase(get()) }
    factory { GetUserPreferencesUseCase(get()) }
    factory { HasCompletedOnboardingUseCase(get()) }

    // Data Management Use Cases
    factory { DeleteAllDataUseCase(get(), get()) }
}
