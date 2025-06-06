package com.uriolus.solidlight

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Color picker composable
 */
@Composable
fun CustomColorPicker(
    modifier: Modifier = Modifier,
    onColorSelected: (Color) -> Unit
) {
    Column(
        modifier = modifier
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

@Preview(showBackground = true)
@Composable
fun CustomColorPickerPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        CustomColorPicker(
            onColorSelected = {}
        )
    }
}
