package com.uriolus.solidlight

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlin.math.sin
import kotlin.random.Random

/**
 * Utility class to create candle flame-like animations
 */
object CandleAnimation {
    
    /**
     * Creates a candle flame animation effect for a color
     * @param baseColor The base color to animate
     * @param intensity The intensity of the animation (0.0-1.0)
     * @return An animated color that trembles like a candle flame
     */
    @Composable
    fun rememberCandleFlameColor(baseColor: Color, intensity: Float = 0.1f): Color {
        // Create multiple animation values with different durations for a more natural effect
        val infiniteTransition = rememberInfiniteTransition(label = "candleFlame")
        
        // Primary slow flicker
        val primaryFlicker by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(800, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "primaryFlicker"
        )
        
        // Secondary faster flicker
        val secondaryFlicker by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(400, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "secondaryFlicker"
        )
        
        // Random jitter
        val randomJitter by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(200, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "randomJitter"
        )
        
        // Use a more direct approach to color shifting for better visibility
        val redShift = (sin(primaryFlicker * Math.PI * 2) * intensity).toFloat()
        val greenShift = (sin(secondaryFlicker * Math.PI * 2) * intensity * 0.8f).toFloat()
        val blueShift = (sin(randomJitter * Math.PI * 2) * intensity * 0.5f).toFloat()
        
        // Apply the shifts to the base color with more dramatic effect
        return Color(
            red = (baseColor.red + redShift).coerceIn(0f, 1f),
            green = (baseColor.green + greenShift).coerceIn(0f, 1f),
            blue = (baseColor.blue + blueShift).coerceIn(0f, 1f),
            alpha = baseColor.alpha
        )
    }
}
