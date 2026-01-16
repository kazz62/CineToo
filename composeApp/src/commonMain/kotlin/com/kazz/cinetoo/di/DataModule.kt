package com.kazz.cinetoo.di

import com.kazz.cinetoo.data.remote.api.TMDbApi
import com.kazz.cinetoo.data.remote.api.createHttpClient
import com.kazz.cinetoo.data.repository.FavoritesRepositoryImpl
import com.kazz.cinetoo.data.repository.MovieRepositoryImpl
import com.kazz.cinetoo.data.repository.UserPreferencesRepositoryImpl
import com.kazz.cinetoo.database.CineTooDatabase
import com.kazz.cinetoo.domain.repository.FavoritesRepository
import com.kazz.cinetoo.domain.repository.MovieRepository
import com.kazz.cinetoo.domain.repository.UserPreferencesRepository
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

fun dataModule() = module {
    // Database - Platform-specific driver will be provided by platformModule
    single { CineTooDatabase(get()) }

    // HTTP Client - API Key will be provided by platform-specific module
    single { createHttpClient(getProperty("TMDB_API_KEY")) }

    // API
    single { TMDbApi(get()) }

    // Repositories
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    single<FavoritesRepository> { FavoritesRepositoryImpl(get()) }
    single<UserPreferencesRepository> { UserPreferencesRepositoryImpl(get()) }
}
