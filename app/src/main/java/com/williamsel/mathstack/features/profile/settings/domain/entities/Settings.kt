package com.williamsel.mathstack.features.settings.domain.entities

data class Settings(
    val notificationsEnabled: Boolean,
    val darkModeEnabled: Boolean,
    val practiceRemindersEnabled: Boolean
)