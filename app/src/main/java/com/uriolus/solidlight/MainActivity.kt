package com.uriolus.solidlight

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.uriolus.solidlight.data.datasource.PreferencesDataSource
import com.uriolus.solidlight.data.repository.ColorSettingsRepositoryImpl
import com.uriolus.solidlight.domain.repository.ColorSettingsRepository
import com.uriolus.solidlight.domain.usecase.GetColorSettingsUseCase
import com.uriolus.solidlight.domain.usecase.SaveColorSettingsUseCase
import com.uriolus.solidlight.ui.theme.SolidLightTheme

class MainActivity : ComponentActivity() {

    // Initialize dependencies
    private val preferencesDataSource by lazy { PreferencesDataSource(applicationContext) }
    private val colorSettingsRepository: ColorSettingsRepository by lazy {
        ColorSettingsRepositoryImpl(preferencesDataSource)
    }
    private val getColorSettingsUseCase by lazy { GetColorSettingsUseCase(colorSettingsRepository) }
    private val saveColorSettingsUseCase by lazy { SaveColorSettingsUseCase(colorSettingsRepository) }

    // Initialize ViewModel with factory
    private val viewModel: SolidColorViewModel by viewModels {
        SolidColorViewModel.Factory(getColorSettingsUseCase, saveColorSettingsUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make the app fullscreen
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SolidLightTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Transparent
                ) {
                    SolidColorScreen(viewModel)
                }
            }
        }
    }
}


@Composable
fun SolidColorScreen(viewModel: SolidColorViewModel) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    // Debug effect to monitor candle mode changes
    LaunchedEffect(state.candleMode) {
        Toast.makeText(
            context,
            context.getString(
                R.string.candle_mode_status,
                if (state.candleMode) 
                    context.getString(R.string.candle_mode_on) 
                else 
                    context.getString(R.string.candle_mode_off)
            ),
            Toast.LENGTH_SHORT
        ).show()
    }

    // Get the background color - apply candle effect if active
    val backgroundColor = if (state.candleMode) {
        // Use the candle animation when candle is active
        CandleAnimation.rememberCandleFlameColor(
            baseColor = state.backgroundColor,
            intensity = 0.10f  // Increased intensity to make it more noticeable
        )
    } else {
        // Use the solid color when candle is not active
        state.backgroundColor
    }

    // Main content
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        viewModel.dispatch(SolidColorAction.ToggleDialog)
                    }
                )
            }
    ) {
        // Color picker dialog
        if (state.showColorDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                ColorPickerWithCandle(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF8F8F8)),
                    onColorSelected = { color ->
                        viewModel.dispatch(SolidColorAction.ColorChanged(color))
                    },
                    onCandleTapped = {
                        viewModel.dispatch(SolidColorAction.CandleTapped)
                    },
                    candleActive = state.candleMode
                )
            }
        }

        // Floating action button - positioned higher to avoid bottom navigation bar
        FloatingActionButton(
            onClick = {
                viewModel.dispatch(SolidColorAction.CandleTapped)
            },
            containerColor = if (state.candleMode) Color(0xFFFF9800) else Color(0xFF607D8B),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .padding(bottom = 48.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_candle),
                contentDescription = stringResource(R.string.toggle_candle_mode),
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SolidColorScreenPreview() {
    SolidLightTheme {
        SolidColorScreen(
            viewModel = SolidColorViewModel(
                GetColorSettingsUseCase(
                    ColorSettingsRepositoryImpl(
                        PreferencesDataSource(LocalContext.current)
                    )
                ),
                SaveColorSettingsUseCase(
                    ColorSettingsRepositoryImpl(
                        PreferencesDataSource(LocalContext.current)
                    )
                )
            )
        )
    }
}