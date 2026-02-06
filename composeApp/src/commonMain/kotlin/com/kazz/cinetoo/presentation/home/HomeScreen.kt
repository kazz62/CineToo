package com.kazz.cinetoo.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kazz.cinetoo.presentation.home.components.HeroCarousel
import com.kazz.cinetoo.presentation.home.components.HorizontalTitleSection
import com.kazz.cinetoo.presentation.home.components.SearchBarWithFilters
import org.koin.compose.koinInject

@Composable
fun HomeScreen(
    onNavigateToDetails: (titleId: Int, isMovie: Boolean) -> Unit,
    onNavigateToDiscover: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: HomeViewModel = koinInject()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is HomeEvent.NavigateToDetails -> {
                    onNavigateToDetails(event.titleId, event.isMovie)
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        // Search Bar with Filters
        item {
            SearchBarWithFilters(
                selectedFilter = state.selectedFilter,
                onFilterSelected = { viewModel.onFilterSelected(it) },
                onSearchClick = { /* TODO: Implement search */ }
            )
        }

        // Hero Carousel
        if (state.heroItems.isNotEmpty()) {
            item {
                HeroCarousel(
                    items = state.heroItems,
                    onItemClick = { viewModel.onTitleClick(it) },
                    onFavoriteClick = { viewModel.onAddToFavorites(it) },
                    isLoading = state.isLoadingHero
                )
            }
        }

        // Recommended For You Section
        if (state.recommendedTitles.isNotEmpty()) {
            item {
                HorizontalTitleSection(
                    title = "Recommended for You",
                    titles = state.recommendedTitles,
                    onTitleClick = { viewModel.onTitleClick(it) },
                    isLoading = state.isLoadingRecommended
                )
            }
        }

        // Most Popular Section
        if (state.popularTitles.isNotEmpty()) {
            item {
                HorizontalTitleSection(
                    title = "Most Popular",
                    titles = state.popularTitles,
                    onTitleClick = { viewModel.onTitleClick(it) },
                    isLoading = state.isLoadingPopular
                )
            }
        }

        // New Releases Section
        if (state.newReleases.isNotEmpty()) {
            item {
                HorizontalTitleSection(
                    title = "New Releases",
                    titles = state.newReleases,
                    onTitleClick = { viewModel.onTitleClick(it) },
                    isLoading = state.isLoadingNewReleases
                )
            }
        }

        // Genre-specific Sections
        state.genreSections.forEach { (genre, titles) ->
            if (titles.isNotEmpty()) {
                item(key = genre.id) {
                    HorizontalTitleSection(
                        title = "Genre: ${genre.name} ${genre.emoji}",
                        titles = titles,
                        onTitleClick = { viewModel.onTitleClick(it) },
                        isLoading = state.isLoadingGenres
                    )
                }
            }
        }
    }
}
