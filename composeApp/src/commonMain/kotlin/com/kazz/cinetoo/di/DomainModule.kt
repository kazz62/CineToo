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

    // User Preferences (legacy aggregate)
    factory { SaveUserPreferencesUseCase(get()) }
    factory { GetUserPreferencesUseCase(get()) }

    // Onboarding
    factory { HasCompletedOnboardingUseCase(get()) }
    factory { SetOnboardingCompletedUseCase(get()) }

    // Genres
    factory { GetAvailableGenresUseCase() }
    factory { GetSelectedGenresUseCase(get()) }
    factory { SaveSelectedGenresUseCase(get()) }

    // Platforms
    factory { GetAvailablePlatformsUseCase() }
    factory { SaveSelectedPlatformsUseCase(get()) }

    // Data Management Use Cases
    factory { DeleteAllDataUseCase(get(), get()) }
}
