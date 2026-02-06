package com.kazz.cinetoo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                // TODO: Implement Home screen with bottom navigation
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Home (Coming Soon)",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}