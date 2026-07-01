package com.williamsel.mathstack.features.settings.presentacion.screens

data class SettingsUiState(
    val notificationsEnabled: Boolean     = true,
    val darkModeEnabled: Boolean          = false,
    val practiceRemindersEnabled: Boolean = true,
    val isLoading: Boolean                = true,
    val isSaving: Boolean                 = false,
    val isLoggingOut: Boolean             = false,
    val errorMessage: String?             = null,
    val logoutSuccess: Boolean            = false
)