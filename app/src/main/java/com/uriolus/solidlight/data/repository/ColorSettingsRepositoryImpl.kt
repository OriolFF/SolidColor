package com.uriolus.solidlight.data.repository

import com.uriolus.solidlight.data.datasource.PreferencesDataSource
import com.uriolus.solidlight.domain.model.ColorSettings
import com.uriolus.solidlight.domain.repository.ColorSettingsRepository

/**
 * Implementation of the ColorSettingsRepository
 */
class ColorSettingsRepositoryImpl(
    private val preferencesDataSource: PreferencesDataSource
) : ColorSettingsRepository {
    
    override fun saveColorSettings(settings: ColorSettings) {
        preferencesDataSource.saveColorSettings(settings)
    }
    
    override fun getColorSettings(): ColorSettings {
        return preferencesDataSource.getColorSettings()
    }
}
