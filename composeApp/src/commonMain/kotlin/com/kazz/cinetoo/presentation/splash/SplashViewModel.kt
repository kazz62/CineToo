package com.kazz.cinetoo.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazz.cinetoo.domain.usecase.HasCompletedOnboardingUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface SplashDestination {
    data object Onboarding : SplashDestination
    data object Home : SplashDestination
}

class SplashViewModel(
    private val hasCompletedOnboardingUseCase: HasCompletedOnboardingUseCase
) : ViewModel() {

    private val _destination = MutableStateFlow<SplashDestination?>(null)
    val destination: StateFlow<SplashDestination?> = _destination.asStateFlow()

    init {
        checkOnboardingStatus()
    }

    private fun checkOnboardingStatus() {
        viewModelScope.launch {
            // Wait for splash animation
            delay(2500)

            // Check if onboarding is completed
            val hasCompletedOnboarding = hasCompletedOnboardingUseCase()

            _destination.value = if (hasCompletedOnboarding) {
                SplashDestination.Home
            } else {
                SplashDestination.Onboarding
            }
        }
    }
}
