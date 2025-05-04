package com.uriolus.solidlight

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uriolus.solidlight.ui.theme.SolidLightTheme

class MainActivity : ComponentActivity() {
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
                    SolidColorScreen()
                }
            }
        }
    }
}

@Composable
fun SolidColorScreen(viewModel: SolidColorViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    
    // Debug effect to monitor candle mode changes
    LaunchedEffect(state.candleMode) {
        Toast.makeText(
            context, 
            "Candle mode: ${if (state.candleMode) "ON" else "OFF"}", 
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

    Box(modifier = Modifier.fillMaxSize()) {
        // Main background with double-tap gesture
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
        )
        
        // Floating action button
        FloatingActionButton(
            onClick = {
                viewModel.dispatch(SolidColorAction.CandleTapped)
            },
            containerColor = if (state.candleMode) Color(0xFFFF9800) else Color(0xFF607D8B),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_candle),
                contentDescription = "Toggle Candle Mode",
                tint = Color.White
            )
        }

        // Show color picker dialog with candle
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
    }
}

@Preview(showBackground = true)
@Composable
fun SolidColorScreenPreview() {
    SolidLightTheme {
        SolidColorScreen()
    }
}