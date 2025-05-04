package com.uriolus.solidlight.domain.repository

import com.uriolus.solidlight.domain.model.ColorSettings

/**
 * Repository interface for color settings
 */
interface ColorSettingsRepository {
    
    /**
     * Save color settings
     */
    fun saveColorSettings(settings: ColorSettings)
    
    /**
     * Get color settings
     */
    fun getColorSettings(): ColorSettings
}
