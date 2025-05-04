package com.uriolus.solidlight.domain.usecase

import com.uriolus.solidlight.domain.model.ColorSettings
import com.uriolus.solidlight.domain.repository.ColorSettingsRepository

/**
 * Use case for saving color settings
 */
class SaveColorSettingsUseCase(
    private val repository: ColorSettingsRepository
) {
    /**
     * Execute the use case
     */
    operator fun invoke(settings: ColorSettings) {
        repository.saveColorSettings(settings)
    }
}
