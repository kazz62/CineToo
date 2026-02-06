package com.kazz.cinetoo

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kazz.cinetoo.presentation.main.MainScreen
import com.kazz.cinetoo.presentation.navigation.Home
import com.kazz.cinetoo.presentation.navigation.OnboardingGenres
import com.kazz.cinetoo.presentation.navigation.Splash
import com.kazz.cinetoo.presentation.onboarding.OnboardingGenresScreen
import com.kazz.cinetoo.presentation.splash.SplashScreen
import com.kazz.cinetoo.presentation.theme.CineTooTheme

@Composable
fun App() {
    CineTooTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Splash
        ) {
            composable<Splash> {
                SplashScreen(
                    onNavigateToOnboarding = {
                        navController.navigate(OnboardingGenres) {
                            popUpTo(Splash) { inclusive = true }
                        }
                    },
                    onNavigateToHome = {
                        navController.navigate(Home) {
                            popUpTo(Splash) { inclusive = true }
                        }
                    }
                )
            }

            composable<OnboardingGenres> {
                OnboardingGenresScreen(
                    onNavigateToHome = {
                        navController.navigate(Home) {
                            popUpTo(OnboardingGenres) { inclusive = true }
                        }
                    }
                )
            }

            composable<Home> {
                MainScreen(
                    onNavigateToDetails = { titleId, isMovie ->
                        // TODO: Navigate to details screen
                    }
                )
            }
        }
    }
}