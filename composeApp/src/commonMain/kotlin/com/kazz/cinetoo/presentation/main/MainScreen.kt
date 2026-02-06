package com.kazz.cinetoo.presentation.main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.kazz.cinetoo.presentation.discover.DiscoverScreen
import com.kazz.cinetoo.presentation.favorites.FavoritesScreen
import com.kazz.cinetoo.presentation.home.HomeScreen
import com.kazz.cinetoo.presentation.home.components.BottomNavigationBar
import com.kazz.cinetoo.presentation.home.components.BottomNavTab
import com.kazz.cinetoo.presentation.settings.SettingsScreen

@Composable
fun MainScreen(
    onNavigateToDetails: (titleId: Int, isMovie: Boolean) -> Unit
) {
    var selectedTab by remember { mutableStateOf(BottomNavTab.HOME) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = selectedTab,
                onTabSelected = { tab -> selectedTab = tab }
            )
        }
    ) { paddingValues ->
        Crossfade(
            targetState = selectedTab,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) { tab ->
            when (tab) {
                BottomNavTab.HOME -> {
                    HomeScreen(
                        onNavigateToDetails = onNavigateToDetails,
                        onNavigateToDiscover = { selectedTab = BottomNavTab.DISCOVER },
                        onNavigateToFavorites = { selectedTab = BottomNavTab.FAVORITES },
                        onNavigateToSettings = { selectedTab = BottomNavTab.SETTINGS }
                    )
                }
                BottomNavTab.DISCOVER -> {
                    DiscoverScreen(
                        onNavigateToHome = { selectedTab = BottomNavTab.HOME },
                        onNavigateToFavorites = { selectedTab = BottomNavTab.FAVORITES },
                        onNavigateToSettings = { selectedTab = BottomNavTab.SETTINGS }
                    )
                }
                BottomNavTab.FAVORITES -> {
                    FavoritesScreen(
                        onNavigateToHome = { selectedTab = BottomNavTab.HOME },
                        onNavigateToDiscover = { selectedTab = BottomNavTab.DISCOVER },
                        onNavigateToSettings = { selectedTab = BottomNavTab.SETTINGS }
                    )
                }
                BottomNavTab.SETTINGS -> {
                    SettingsScreen(
                        onNavigateToHome = { selectedTab = BottomNavTab.HOME },
                        onNavigateToFavorites = { selectedTab = BottomNavTab.FAVORITES },
                        onNavigateToDiscover = { selectedTab = BottomNavTab.DISCOVER }
                    )
                }
            }
        }
    }
}
