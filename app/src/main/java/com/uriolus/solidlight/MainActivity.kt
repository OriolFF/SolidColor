package com.uriolus.solidlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.graphics.Brush
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

@Composable
fun CustomColorPicker(
    modifier: Modifier = Modifier,
    onColorSelected: (Color) -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        // Horizontal rainbow gradient
        ColorGradient(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
                .clip(RoundedCornerShape(8.dp)),
            gradientColors = listOf(
                Color.Red, Color.Yellow, Color.Green, 
                Color.Cyan, Color.Blue, Color.Magenta, Color.Red
            ),
            onColorSelected = onColorSelected
        )
        
        // Additional rows of gradient colors
        ColorGradient(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f)
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(8.dp)),
            gradientColors = listOf(
                Color.Black, Color.DarkGray, Color.Gray, Color.LightGray, Color.White
            ),
            onColorSelected = onColorSelected
        )
        
        // Pastel colors
        ColorGradient(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f)
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(8.dp)),
            gradientColors = listOf(
                Color(0xFFFFB6C1), // Light pink
                Color(0xFFFFDAB9), // Peach
                Color(0xFFFFE4B5), // Moccasin
                Color(0xFFFFFFE0), // Light yellow
                Color(0xFFF0FFF0), // Honeydew
                Color(0xFFE0FFFF), // Light cyan
                Color(0xFFB0E0E6), // Powder blue
                Color(0xFFD8BFD8)  // Thistle
            ),
            onColorSelected = onColorSelected
        )
    }
}

@Composable
fun ColorGradient(
    modifier: Modifier = Modifier,
    gradientColors: List<Color>,
    onColorSelected: (Color) -> Unit
) {
    Box(
        modifier = modifier
            .clickable { }  // Empty clickable to prevent clicks passing through
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        // Calculate the color at the tapped position
                        val width = size.width.toFloat()
                        val x = offset.x.coerceIn(0f, width)
                        val ratio = x / width
                        
                        // Interpolate between the gradient colors
                        val selectedColor = interpolateColor(gradientColors, ratio)
                        onColorSelected(selectedColor)
                    }
                }
        ) {
            // Draw the horizontal gradient
            drawRect(
                brush = Brush.horizontalGradient(gradientColors),
                size = size
            )
        }
    }
}

// Helper function to interpolate colors
fun interpolateColor(colors: List<Color>, ratio: Float): Color {
    if (colors.isEmpty()) return Color.Black
    if (colors.size == 1) return colors.first()
    
    val segmentCount = colors.size - 1
    val segmentSize = 1f / segmentCount
    
    val segmentIndex = (ratio / segmentSize).toInt().coerceAtMost(segmentCount - 1)
    val segmentRatio = (ratio - segmentIndex * segmentSize) / segmentSize
    
    val startColor = colors[segmentIndex]
    val endColor = colors[segmentIndex + 1]
    
    return Color(
        alpha = lerp(startColor.alpha, endColor.alpha, segmentRatio),
        red = lerp(startColor.red, endColor.red, segmentRatio),
        green = lerp(startColor.green, endColor.green, segmentRatio),
        blue = lerp(startColor.blue, endColor.blue, segmentRatio)
    )
}

// Linear interpolation helper
fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction
}

@Preview(showBackground = true)
@Composable
fun SolidColorScreenPreview() {
    SolidLightTheme {
        SolidColorScreen()
    }
}