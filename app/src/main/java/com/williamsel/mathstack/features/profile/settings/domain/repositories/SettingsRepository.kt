package com.williamsel.mathstack.features.settings.domain.repositories

import com.williamsel.mathstack.features.settings.domain.entities.Settings

interface SettingsRepository {
    suspend fun getSettings(): Result<Settings>
    suspend fun updateSettings(settings: Settings): Result<Settings>
    suspend fun logout(): Result<Unit>
}