package com.kazz.cinetoo.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazz.cinetoo.domain.model.Genre
import com.kazz.cinetoo.domain.model.Movie
import com.kazz.cinetoo.domain.model.TVShow
import com.kazz.cinetoo.domain.usecase.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class ContentFilter {
    ALL, MOVIES, SERIES
}

data class HomeState(
    val heroItems: List<Any> = emptyList(),
    val recommendedTitles: List<Any> = emptyList(),
    val popularTitles: List<Any> = emptyList(),
    val newReleases: List<Any> = emptyList(),
    val genreSections: Map<Genre, List<Any>> = emptyMap(),
    val selectedFilter: ContentFilter = ContentFilter.ALL,
    val isLoadingHero: Boolean = false,
    val isLoadingRecommended: Boolean = false,
    val isLoadingPopular: Boolean = false,
    val isLoadingNewReleases: Boolean = false,
    val isLoadingGenres: Boolean = false,
    val error: String? = null
)

sealed interface HomeEvent {
    data class NavigateToDetails(val titleId: Int, val isMovie: Boolean) : HomeEvent
}

class HomeViewModel(
    private val getRecommendedTitlesUseCase: GetRecommendedTitlesUseCase,
    private val getPopularTitlesUseCase: GetPopularTitlesUseCase,
    private val getNewReleasesTitlesUseCase: GetNewReleasesTitlesUseCase,
    private val getTitlesByGenreUseCase: GetTitlesByGenreUseCase,
    private val getSelectedGenresUseCase: GetSelectedGenresUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<HomeEvent>()
    val events: SharedFlow<HomeEvent> = _events.asSharedFlow()

    init {
        loadAllContent()
    }

    fun loadAllContent() {
        loadHeroCarousel()
        loadRecommendedTitles()
        loadPopularTitles()
        loadNewReleases()
        loadGenreSections()
    }

    private fun loadHeroCarousel() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingHero = true) }
            try {
                // Use recommended titles for hero carousel
                val titles = getRecommendedTitlesUseCase()
                _state.update { currentState ->
                    currentState.copy(
                        heroItems = titles.take(5),
                        isLoadingHero = false
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingHero = false, error = e.message) }
            }
        }
    }

    private fun loadRecommendedTitles() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingRecommended = true) }
            try {
                val titles = getRecommendedTitlesUseCase()
                _state.update { currentState ->
                    currentState.copy(
                        recommendedTitles = applyFilter(titles, currentState.selectedFilter),
                        isLoadingRecommended = false
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingRecommended = false, error = e.message) }
            }
        }
    }

    private fun loadPopularTitles() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingPopular = true) }
            try {
                val titles = getPopularTitlesUseCase()
                _state.update { currentState ->
                    currentState.copy(
                        popularTitles = applyFilter(titles, currentState.selectedFilter),
                        isLoadingPopular = false
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingPopular = false, error = e.message) }
            }
        }
    }

    private fun loadNewReleases() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingNewReleases = true) }
            try {
                val titles = getNewReleasesTitlesUseCase()
                _state.update { currentState ->
                    currentState.copy(
                        newReleases = applyFilter(titles, currentState.selectedFilter),
                        isLoadingNewReleases = false
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingNewReleases = false, error = e.message) }
            }
        }
    }

    private fun loadGenreSections() {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingGenres = true) }
            try {
                // Get user's selected genres
                val genres = getSelectedGenresUseCase().first()

                // Load titles for each genre (limit to 2-3 genres for performance)
                val genreSections = genres.take(3).associate { genre ->
                    val titles = getTitlesByGenreUseCase(genre)
                    genre to applyFilter(titles, _state.value.selectedFilter)
                }

                _state.update { currentState ->
                    currentState.copy(
                        genreSections = genreSections,
                        isLoadingGenres = false
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoadingGenres = false, error = e.message) }
            }
        }
    }

    fun onFilterSelected(filter: ContentFilter) {
        _state.update { it.copy(selectedFilter = filter) }
        // Reload all content with new filter
        loadAllContent()
    }

    fun onTitleClick(title: Any) {
        viewModelScope.launch {
            when (title) {
                is Movie -> _events.emit(HomeEvent.NavigateToDetails(title.id, true))
                is TVShow -> _events.emit(HomeEvent.NavigateToDetails(title.id, false))
            }
        }
    }

    fun onAddToFavorites(title: Any) {
        viewModelScope.launch {
            try {
                when (title) {
                    is Movie -> addToFavoritesUseCase(
                        id = title.id,
                        type = com.kazz.cinetoo.domain.model.MediaType.MOVIE,
                        title = title.title,
                        posterPath = title.posterPath
                    )
                    is TVShow -> addToFavoritesUseCase(
                        id = title.id,
                        type = com.kazz.cinetoo.domain.model.MediaType.TV_SHOW,
                        title = title.name,
                        posterPath = title.posterPath
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private fun applyFilter(titles: List<Any>, filter: ContentFilter): List<Any> {
        return when (filter) {
            ContentFilter.ALL -> titles
            ContentFilter.MOVIES -> titles.filterIsInstance<Movie>()
            ContentFilter.SERIES -> titles.filterIsInstance<TVShow>()
        }
    }
}
