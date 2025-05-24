package com.uriolus.solidlight

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.uriolus.solidlight.domain.model.ColorSettings
import com.uriolus.solidlight.domain.usecase.GetColorSettingsUseCase
import com.uriolus.solidlight.domain.usecase.SaveColorSettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * State for the solid color screen
 */
data class SolidColorState(
    val backgroundColor: Color = Color(0xFF3700B3), // Default purple
    val showColorDialog: Boolean = false,
    val candleMode: Boolean = false
)

/**
 * Actions that can be dispatched to the ViewModel
 */
sealed class SolidColorAction {
    data class ColorChanged(val color: Color) : SolidColorAction()
    data object ToggleDialog : SolidColorAction()
    data object CandleTapped : SolidColorAction()
    data object CloseDialog : SolidColorAction()
}

/**
 * ViewModel for the solid color screen
 */
class SolidColorViewModel(
    private val getColorSettingsUseCase: GetColorSettingsUseCase,
    private val saveColorSettingsUseCase: SaveColorSettingsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SolidColorState())
    val state: StateFlow<SolidColorState> = _state.asStateFlow()

    init {
        // Load saved settings when ViewModel is created
        loadSavedSettings()
    }

    /**
     * Load saved settings from preferences
     */
    private fun loadSavedSettings() {
        viewModelScope.launch {
            val settings = getColorSettingsUseCase()
            _state.value = SolidColorState(
                backgroundColor = settings.toComposeColor(),
                candleMode = settings.candleMode
            )
        }
    }

    /**
     * Save current settings to preferences
     */
    private fun saveCurrentSettings() {
        viewModelScope.launch {
            val settings = ColorSettings.fromComposeColor(
                color = _state.value.backgroundColor,
                candleMode = _state.value.candleMode
            )
            saveColorSettingsUseCase(settings)
        }
    }

    /**
     * Dispatch an action to the ViewModel
     */
    fun dispatch(action: SolidColorAction) {
        when (action) {
            is SolidColorAction.ColorChanged -> {
                _state.value = _state.value.copy(
                    backgroundColor = action.color,
                    showColorDialog = false  // Close dialog after color selection
                )
                saveCurrentSettings()
            }

            is SolidColorAction.ToggleDialog -> {
                _state.value = _state.value.copy(showColorDialog = !_state.value.showColorDialog)
            }

            is SolidColorAction.CandleTapped -> {
                _state.value = _state.value.copy(candleMode = !_state.value.candleMode)
                saveCurrentSettings()
            }

            is SolidColorAction.CloseDialog -> {
                _state.value = _state.value.copy(showColorDialog = false)
            }
        }
    }

    /**
     * Factory for creating the ViewModel with dependencies
     */
    class Factory(
        private val getColorSettingsUseCase: GetColorSettingsUseCase,
        private val saveColorSettingsUseCase: SaveColorSettingsUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SolidColorViewModel::class.java)) {
                return SolidColorViewModel(
                    getColorSettingsUseCase,
                    saveColorSettingsUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
