package com.uriolus.solidlight.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

/**
 * Domain model representing color settings
 */
data class ColorSettings(
    val backgroundColor: Int,
    val candleMode: Boolean
) {
    /**
     * Convert to Compose Color
     */
    fun toComposeColor(): Color {
        return Color(backgroundColor)
    }
    
    companion object {
        /**
         * Create from Compose Color
         */
        fun fromComposeColor(color: Color, candleMode: Boolean): ColorSettings {
            return ColorSettings(
                backgroundColor = color.toArgb(),
                candleMode = candleMode
            )
        }
    }
}
