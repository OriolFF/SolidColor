package com.uriolus.solidlight

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Parent composable that contains both the candle and color picker
 */
@Composable
fun ColorPickerWithCandle(
    modifier: Modifier = Modifier,
    onColorSelected: (Color) -> Unit,
    onCandleTapped: () -> Unit,
    candleActive: Boolean = false
) {
    var showColorPicker by remember { mutableStateOf(true) }
    
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Candle at the top
        CandleButton(
            active = candleActive,
            onTap = onCandleTapped,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Color picker below
        if (showColorPicker) {
            CustomColorPicker(
                onColorSelected = { color ->
                    onColorSelected(color)
                    showColorPicker = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorPickerWithCandlePreview() {
    var candleActive by remember { mutableStateOf(false) }
    
    ColorPickerWithCandle(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White),
        onColorSelected = { },
        onCandleTapped = { candleActive = !candleActive },
        candleActive = candleActive
    )
}

@Preview(showBackground = true)
@Composable
fun ColorPickerWithCandleActivePreview() {
    ColorPickerWithCandle(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White),
        onColorSelected = { },
        onCandleTapped = { },
        candleActive = true
    )
}
