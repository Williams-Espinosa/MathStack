package com.williamsel.mathstack.features.settings.domain.usecases

import com.williamsel.mathstack.features.settings.domain.entities.Settings
import com.williamsel.mathstack.features.settings.domain.repositories.SettingsRepository
import javax.inject.Inject
class GetSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(): Result<Settings> = repository.getSettings()
}

class UpdateSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(settings: Settings): Result<Settings> =
        repository.updateSettings(settings)
}

class LogoutUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.logout()
}
data class SettingsUseCases(
    val getSettings: GetSettingsUseCase,
    val updateSettings: UpdateSettingsUseCase,
    val logout: LogoutUseCase
)