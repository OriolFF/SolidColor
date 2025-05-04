package com.uriolus.solidlight.di

import android.content.Context
import com.uriolus.solidlight.data.datasource.PreferencesDataSource
import com.uriolus.solidlight.data.repository.ColorSettingsRepositoryImpl
import com.uriolus.solidlight.domain.repository.ColorSettingsRepository
import com.uriolus.solidlight.domain.usecase.GetColorSettingsUseCase
import com.uriolus.solidlight.domain.usecase.SaveColorSettingsUseCase

/**
 * Application module for dependency injection
 */
object AppModule {
    
    /**
     * Provides the preferences data source
     */
    fun providePreferencesDataSource(context: Context): PreferencesDataSource {
        return PreferencesDataSource(context)
    }
    
    /**
     * Provides the color settings repository
     */
    fun provideColorSettingsRepository(dataSource: PreferencesDataSource): ColorSettingsRepository {
        return ColorSettingsRepositoryImpl(dataSource)
    }
    
    /**
     * Provides the save color settings use case
     */
    fun provideSaveColorSettingsUseCase(repository: ColorSettingsRepository): SaveColorSettingsUseCase {
        return SaveColorSettingsUseCase(repository)
    }
    
    /**
     * Provides the get color settings use case
     */
    fun provideGetColorSettingsUseCase(repository: ColorSettingsRepository): GetColorSettingsUseCase {
        return GetColorSettingsUseCase(repository)
    }
}
