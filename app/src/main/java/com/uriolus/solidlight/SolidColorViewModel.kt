package com.uriolus.solidlight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// --- State ---
data class SolidColorState(
    val backgroundColor: Color = Color.Yellow,
    val showColorDialog: Boolean = false,
    val candleMode: Boolean = false
)

// --- Actions ---
sealed class SolidColorAction {
    data object ToggleDialog : SolidColorAction()
    data class ColorChanged(val color: Color) : SolidColorAction()
    data object CandleTapped : SolidColorAction()
    data object CloseDialog : SolidColorAction()
}

// --- ViewModel ---
class SolidColorViewModel : ViewModel() {
    private val _state = MutableStateFlow(SolidColorState())
    val state: StateFlow<SolidColorState> = _state.asStateFlow()

    fun dispatch(action: SolidColorAction) {
        when (action) {
            is SolidColorAction.ToggleDialog -> {
                _state.value = _state.value.copy(showColorDialog = !_state.value.showColorDialog)
            }
            is SolidColorAction.CloseDialog -> {
                _state.value = _state.value.copy(showColorDialog = false)
            }
            is SolidColorAction.ColorChanged -> {
                _state.value = _state.value.copy(
                    backgroundColor = action.color,
                    showColorDialog = false
                )
            }
            is SolidColorAction.CandleTapped -> {
                _state.value = _state.value.copy(
                    candleMode = !_state.value.candleMode,
                    showColorDialog = false
                )
            }
        }
    }
}
