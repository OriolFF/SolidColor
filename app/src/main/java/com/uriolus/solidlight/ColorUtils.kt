package com.uriolus.solidlight

import androidx.compose.ui.graphics.Color

/**
 * Helper function to interpolate colors
 */
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

/**
 * Linear interpolation helper
 */
fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction
}
