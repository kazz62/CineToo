package com.kazz.cinetoo.di

import com.kazz.cinetoo.presentation.home.HomeViewModel
import com.kazz.cinetoo.presentation.onboarding.OnboardingGenresViewModel
import com.kazz.cinetoo.presentation.onboarding.OnboardingPlatformsViewModel
import com.kazz.cinetoo.presentation.splash.SplashViewModel
import org.koin.dsl.module

fun presentationModule() = module {
    // ViewModels
    factory { SplashViewModel(get()) }
    factory { OnboardingGenresViewModel(get(), get(), get()) }
    factory { OnboardingPlatformsViewModel(get(), get(), get()) }
    factory { HomeViewModel(get(), get(), get(), get(), get(), get()) }
}
