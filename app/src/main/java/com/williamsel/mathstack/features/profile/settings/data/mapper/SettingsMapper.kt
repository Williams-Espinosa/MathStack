package com.williamsel.mathstack.features.settings.data.mapper

import com.williamsel.mathstack.features.settings.data.models.SettingsDto
import com.williamsel.mathstack.features.settings.domain.entities.Settings

fun SettingsDto.toDomain(): Settings = Settings(
    notificationsEnabled     = notificationsEnabled,
    darkModeEnabled          = darkModeEnabled,
    practiceRemindersEnabled = practiceRemindersEnabled
)

fun Settings.toDto(): SettingsDto = SettingsDto(
    notificationsEnabled     = notificationsEnabled,
    darkModeEnabled          = darkModeEnabled,
    practiceRemindersEnabled = practiceRemindersEnabled
)