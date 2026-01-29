package com.kazz.cinetoo.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazz.cinetoo.domain.model.Genre
import com.kazz.cinetoo.domain.usecase.GetAvailableGenresUseCase
import com.kazz.cinetoo.domain.usecase.SaveSelectedGenresUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OnboardingGenresState(
    val availableGenres: List<Genre> = emptyList(),
    val selectedGenres: Set<Int> = emptySet(),
    val isLoading: Boolean = false
) {
    val canValidate: Boolean get() = selectedGenres.isNotEmpty()
}

sealed interface OnboardingGenresEvent {
    data object NavigateToNextStep : OnboardingGenresEvent
}

class OnboardingGenresViewModel(
    private val getAvailableGenresUseCase: GetAvailableGenresUseCase,
    private val saveSelectedGenresUseCase: SaveSelectedGenresUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingGenresState())
    val state: StateFlow<OnboardingGenresState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<OnboardingGenresEvent>()
    val events: SharedFlow<OnboardingGenresEvent> = _events.asSharedFlow()

    init {
        loadGenres()
    }

    private fun loadGenres() {
        val genres = getAvailableGenresUseCase()
        _state.update { it.copy(availableGenres = genres) }
    }

    fun toggleGenreSelection(genreId: Int) {
        _state.update { currentState ->
            val newSelection = if (genreId in currentState.selectedGenres) {
                currentState.selectedGenres - genreId
            } else {
                currentState.selectedGenres + genreId
            }
            currentState.copy(selectedGenres = newSelection)
        }
    }

    fun validateSelection() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val selectedGenresList = _state.value.availableGenres
                .filter { it.id in _state.value.selectedGenres }

            saveSelectedGenresUseCase(selectedGenresList)

            _state.update { it.copy(isLoading = false) }
            _events.emit(OnboardingGenresEvent.NavigateToNextStep)
        }
    }
}
