package com.williamsel.mathstack.features.settings.data.models

import com.google.gson.annotations.SerializedName

data class SettingsDto(
    @SerializedName("notifications_enabled")
    val notificationsEnabled: Boolean,

    @SerializedName("dark_mode_enabled")
    val darkModeEnabled: Boolean,

    @SerializedName("practice_reminders_enabled")
    val practiceRemindersEnabled: Boolean
)