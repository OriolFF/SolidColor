package com.uriolus.solidlight.domain.usecase

import com.uriolus.solidlight.domain.model.ColorSettings
import com.uriolus.solidlight.domain.repository.ColorSettingsRepository

/**
 * Use case for getting color settings
 */
class GetColorSettingsUseCase(
    private val repository: ColorSettingsRepository
) {
    /**
     * Execute the use case
     */
    operator fun invoke(): ColorSettings {
        return repository.getColorSettings()
    }
}
