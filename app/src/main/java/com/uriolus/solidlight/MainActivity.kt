package com.uriolus.solidlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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
    // State for the background color, default to white
    val backgroundColor by remember { mutableStateOf(Color.Yellow) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    )
}

@Preview(showBackground = true)
@Composable
fun SolidColorScreenPreview() {
    SolidLightTheme {
        SolidColorScreen()
    }
}