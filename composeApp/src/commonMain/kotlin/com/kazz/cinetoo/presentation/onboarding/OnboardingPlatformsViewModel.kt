package com.kazz.cinetoo.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazz.cinetoo.domain.model.StreamingPlatform
import com.kazz.cinetoo.domain.usecase.GetAvailablePlatformsUseCase
import com.kazz.cinetoo.domain.usecase.SaveSelectedPlatformsUseCase
import com.kazz.cinetoo.domain.usecase.SetOnboardingCompletedUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OnboardingPlatformsState(
    val availablePlatforms: List<StreamingPlatform> = emptyList(),
    val selectedPlatforms: Set<Int> = emptySet(),
    val isLoading: Boolean = false
) {
    val canValidate: Boolean get() = selectedPlatforms.isNotEmpty()
}

sealed interface OnboardingPlatformsEvent {
    data object NavigateToHome : OnboardingPlatformsEvent
}

class OnboardingPlatformsViewModel(
    private val getAvailablePlatformsUseCase: GetAvailablePlatformsUseCase,
    private val saveSelectedPlatformsUseCase: SaveSelectedPlatformsUseCase,
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingPlatformsState())
    val state: StateFlow<OnboardingPlatformsState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<OnboardingPlatformsEvent>()
    val events: SharedFlow<OnboardingPlatformsEvent> = _events.asSharedFlow()

    init {
        loadPlatforms()
    }

    private fun loadPlatforms() {
        val platforms = getAvailablePlatformsUseCase()
        _state.update { it.copy(availablePlatforms = platforms) }
    }

    fun togglePlatformSelection(platformId: Int) {
        _state.update { currentState ->
            val newSelection = if (platformId in currentState.selectedPlatforms) {
                currentState.selectedPlatforms - platformId
            } else {
                currentState.selectedPlatforms + platformId
            }
            currentState.copy(selectedPlatforms = newSelection)
        }
    }

    fun validateSelection() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val selectedPlatformsList = _state.value.availablePlatforms
                .filter { it.id in _state.value.selectedPlatforms }

            saveSelectedPlatformsUseCase(selectedPlatformsList)
            setOnboardingCompletedUseCase(true)

            _state.update { it.copy(isLoading = false) }
            _events.emit(OnboardingPlatformsEvent.NavigateToHome)
        }
    }
}
