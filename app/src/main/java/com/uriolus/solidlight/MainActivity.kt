package com.uriolus.solidlight


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
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
fun SolidColorScreen() {
    var backgroundColor by remember { mutableStateOf(Color.Yellow) }
    var showColorDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        showColorDialog = true
                    }
                )
            }
    )

    if (showColorDialog) {
        // Use the Compose Color Picker dialog
        val controller = rememberColorPickerController()
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(10.dp),
            controller = controller,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                backgroundColor = colorEnvelope.color
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SolidColorScreenPreview() {
    SolidLightTheme {
        SolidColorScreen()
    }
}