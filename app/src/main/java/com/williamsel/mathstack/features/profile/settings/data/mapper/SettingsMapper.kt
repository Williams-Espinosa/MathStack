package com.williamsel.mathstack.features.private.settings.data.mapper

import com.williamsel.mathstack.features.private.settings.data.models.SettingsDto
import com.williamsel.mathstack.features.private.settings.domain.entities.Settings

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