package com.uriolus.solidlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uriolus.solidlight.ui.theme.SolidLightTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SolidLightTheme {
                SolidColorScreen()
            }
        }
    }
}

@Composable
fun SolidColorScreen(viewModel: SolidColorViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    // Main background with double-tap gesture
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(state.backgroundColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        viewModel.dispatch(SolidColorAction.DoubleTap)
                    }
                )
            }
    )

    // Custom color picker dialog
    if (state.showColorDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CustomColorPicker(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                onColorSelected = { color ->
                    viewModel.dispatch(SolidColorAction.ColorChanged(color))
                }
            )
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