package com.kazz.cinetoo.di

import com.kazz.cinetoo.presentation.onboarding.OnboardingGenresViewModel
import com.kazz.cinetoo.presentation.onboarding.OnboardingPlatformsViewModel
import com.kazz.cinetoo.presentation.splash.SplashViewModel
import org.koin.dsl.module

fun presentationModule() = module {
    // ViewModels
    factory { SplashViewModel(get()) }
    factory { OnboardingGenresViewModel(get(), get()) }
    factory { OnboardingPlatformsViewModel(get(), get(), get()) }
}
