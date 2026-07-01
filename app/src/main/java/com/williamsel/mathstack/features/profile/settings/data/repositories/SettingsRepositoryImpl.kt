package com.williamsel.mathstack.features.settings.data.repositories

import com.williamsel.mathstack.features.settings.data.datasource.api.SettingsApi
import com.williamsel.mathstack.features.settings.data.mapper.toDomain
import com.williamsel.mathstack.features.settings.data.mapper.toDto
import com.williamsel.mathstack.features.settings.domain.entities.Settings
import com.williamsel.mathstack.features.settings.domain.repositories.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val api: SettingsApi
) : SettingsRepository {

    override suspend fun getSettings(): Result<Settings> = runCatching {
        api.getSettings().toDomain()
    }

    override suspend fun updateSettings(settings: Settings): Result<Settings> = runCatching {
        api.updateSettings(settings.toDto()).toDomain()
    }

    override suspend fun logout(): Result<Unit> = runCatching {
        api.logout()
    }
}