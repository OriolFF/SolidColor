package com.uriolus.solidlight

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.uriolus.solidlight.domain.model.ColorSettings
import com.uriolus.solidlight.domain.usecase.GetColorSettingsUseCase
import com.uriolus.solidlight.domain.usecase.SaveColorSettingsUseCase
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SolidColorViewModelTest {

    // Test dispatcher for coroutines
    private val testDispatcher = StandardTestDispatcher()
    
    // Mocks
    private lateinit var getColorSettingsUseCase: GetColorSettingsUseCase
    private lateinit var saveColorSettingsUseCase: SaveColorSettingsUseCase
    
    // System under test
    private lateinit var viewModel: SolidColorViewModel
    
    // Test data
    private val defaultColor = Color(0xFF3700B3) // Default purple
    private val testColor = Color.Red
    private val defaultSettings = ColorSettings(defaultColor.toArgb(), false)
    
    @BeforeEach
    fun setup() {
        // Set the main dispatcher for coroutines
        Dispatchers.setMain(testDispatcher)
        
        // Initialize mocks
        getColorSettingsUseCase = mockk()
        saveColorSettingsUseCase = mockk()
        
        // Default behavior
        every { getColorSettingsUseCase() } returns defaultSettings
        every { saveColorSettingsUseCase(any()) } just runs
    }
    
    @AfterEach
    fun tearDown() {
        // Reset the main dispatcher
        Dispatchers.resetMain()
    }
    
    @Test
    @DisplayName("When ViewModel is initialized, it should load saved settings")
    fun initLoadsSettings() = runTest {
        // Given
        val testSettings = ColorSettings(testColor.toArgb(), true)
        every { getColorSettingsUseCase() } returns testSettings
        
        // When
        viewModel = SolidColorViewModel(getColorSettingsUseCase, saveColorSettingsUseCase)
        testDispatcher.scheduler.advanceUntilIdle() // Process all coroutines
        
        // Then
        verify { getColorSettingsUseCase() }
        assertEquals(testColor, viewModel.state.value.backgroundColor)
        assertTrue(viewModel.state.value.candleMode)
    }
    
    @Nested
    @DisplayName("Action handling tests")
    inner class ActionTests {
        
        @BeforeEach
        fun setupViewModel() {
            viewModel = SolidColorViewModel(getColorSettingsUseCase, saveColorSettingsUseCase)
            testDispatcher.scheduler.advanceUntilIdle() // Process all coroutines
        }
        
        @Test
        @DisplayName("ColorChanged action should update color and save settings")
        fun colorChangedAction() = runTest {
            // When
            viewModel.dispatch(SolidColorAction.ColorChanged(testColor))
            testDispatcher.scheduler.advanceUntilIdle() // Process all coroutines
            
            // Then
            assertEquals(testColor, viewModel.state.value.backgroundColor)
            assertFalse(viewModel.state.value.showColorDialog) // Dialog should close
            verify { saveColorSettingsUseCase(any()) }
        }
        
        @Test
        @DisplayName("ToggleDialog action should toggle dialog visibility")
        fun toggleDialogAction() = runTest {
            // Given
            assertFalse(viewModel.state.value.showColorDialog) // Initially false
            
            // When - toggle on
            viewModel.dispatch(SolidColorAction.ToggleDialog)
            
            // Then
            assertTrue(viewModel.state.value.showColorDialog)
            
            // When - toggle off
            viewModel.dispatch(SolidColorAction.ToggleDialog)
            
            // Then
            assertFalse(viewModel.state.value.showColorDialog)
        }
        
        @Test
        @DisplayName("CandleTapped action should toggle candle mode and save settings")
        fun candleTappedAction() = runTest {
            // Given
            assertFalse(viewModel.state.value.candleMode) // Initially false
            
            // When
            viewModel.dispatch(SolidColorAction.CandleTapped)
            testDispatcher.scheduler.advanceUntilIdle() // Process all coroutines
            
            // Then
            assertTrue(viewModel.state.value.candleMode)
            verify { saveColorSettingsUseCase(any()) }
            
            // When - toggle off
            viewModel.dispatch(SolidColorAction.CandleTapped)
            testDispatcher.scheduler.advanceUntilIdle() // Process all coroutines
            
            // Then
            assertFalse(viewModel.state.value.candleMode)
            verify(exactly = 2) { saveColorSettingsUseCase(any()) }
        }
        
        @Test
        @DisplayName("CloseDialog action should close the dialog")
        fun closeDialogAction() = runTest {
            // Given
            viewModel.dispatch(SolidColorAction.ToggleDialog) // Open dialog
            assertTrue(viewModel.state.value.showColorDialog)
            
            // When
            viewModel.dispatch(SolidColorAction.CloseDialog)
            
            // Then
            assertFalse(viewModel.state.value.showColorDialog)
        }
    }
    
    @Test
    @DisplayName("Saved settings should have correct values")
    fun saveSettingsValues() = runTest {
        // Given
        viewModel = SolidColorViewModel(getColorSettingsUseCase, saveColorSettingsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // When - change color and enable candle mode
        viewModel.dispatch(SolidColorAction.ColorChanged(testColor))
        viewModel.dispatch(SolidColorAction.CandleTapped) // Enable candle mode
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then - verify the settings saved have the correct values
        verify {
            saveColorSettingsUseCase(match { settings ->
                settings.backgroundColor == testColor.toArgb() &&
                settings.candleMode == true
            })
        }
    }
}
