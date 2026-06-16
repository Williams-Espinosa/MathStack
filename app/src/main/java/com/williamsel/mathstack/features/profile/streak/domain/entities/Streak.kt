package com.williamsel.mathstack.features.private.streak.domain.entities

import java.time.LocalDate

data class Streak(
    val currentStreak: Int,
    val weekDays: List<StreakDay>,
    val activityHistory: List<ActivityDay>
) {
    val completedThisWeek: Int
        get() = weekDays.count { it.isCompleted }

    val totalThisWeek: Int
        get() = weekDays.size
}
data class StreakDay(
    val date: LocalDate,
    val label: String,
    val isCompleted: Boolean,
    val isToday: Boolean = false
)

data class ActivityDay(
    val date: LocalDate,
    val intensity: Int
)