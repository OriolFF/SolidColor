package com.uriolus.solidlight.data.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.uriolus.solidlight.domain.model.ColorSettings

/**
 * Data source for accessing and modifying app preferences
 */
class PreferencesDataSource(context: Context) {
    
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )
    
    /**
     * Save color settings to preferences
     */
    fun saveColorSettings(settings: ColorSettings) {
        prefs.edit {
            putString(KEY_BACKGROUND_COLOR, String.format("#%08X", settings.backgroundColor))
            putBoolean(KEY_CANDLE_MODE, settings.candleMode)
        }
    }
    
    /**
     * Get color settings from preferences
     */
    fun getColorSettings(): ColorSettings {
        val colorString = prefs.getString(KEY_BACKGROUND_COLOR, DEFAULT_COLOR) ?: DEFAULT_COLOR
        val candleMode = prefs.getBoolean(KEY_CANDLE_MODE, false)
        
        return try {
            ColorSettings(
                backgroundColor = android.graphics.Color.parseColor(colorString),
                candleMode = candleMode
            )
        } catch (e: Exception) {
            // If color parsing fails, return default
            ColorSettings(
                backgroundColor = android.graphics.Color.parseColor(DEFAULT_COLOR),
                candleMode = false
            )
        }
    }
    
    companion object {
        private const val PREFS_NAME = "solid_light_prefs"
        private const val KEY_BACKGROUND_COLOR = "background_color"
        private const val KEY_CANDLE_MODE = "candle_mode"
        private const val DEFAULT_COLOR = "#FF3700B3" // Default purple
    }
}
