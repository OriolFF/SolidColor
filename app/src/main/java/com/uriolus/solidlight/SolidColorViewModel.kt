package com.uriolus.solidlight

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// --- State ---
data class SolidColorState(
    val backgroundColor: Color = Color.Yellow,
    val showColorDialog: Boolean = false
)

// --- Actions (Intents) ---
sealed class SolidColorAction {
    data object DoubleTap : SolidColorAction()
    data class ColorChanged(val color: Color) : SolidColorAction()

}

// --- ViewModel ---
class SolidColorViewModel : ViewModel() {
    private val _state = MutableStateFlow(SolidColorState())
    val state: StateFlow<SolidColorState> = _state.asStateFlow()

    fun dispatch(action: SolidColorAction) {
        when (action) {
            is SolidColorAction.DoubleTap -> {
                _state.value = _state.value.copy(showColorDialog = true)
            }
            is SolidColorAction.ColorChanged -> {
                _state.value = _state.value.copy(
                    backgroundColor = action.color,
                    showColorDialog = false
                )
            }

        }
    }
}
