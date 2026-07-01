package com.williamsel.mathstack.features.profile.streak.presentacion.screens

import com.williamsel.mathstack.features.profile.streak.domain.entities.ActivityDay
import com.williamsel.mathstack.features.profile.streak.domain.entities.StreakDay

data class StreakUiState(
    val currentStreak: Int = 0,
    val weekDays: List<StreakDay> = emptyList(),
    val activityHistory: List<ActivityDay> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
) {
    val completedThisWeek: Int
        get() = weekDays.count { it.isCompleted }

    val totalThisWeek: Int
        get() = weekDays.size
}