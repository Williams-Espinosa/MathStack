package com.williamsel.mathstack.features.profile.streak.data.models

import com.google.gson.annotations.SerializedName

data class StreakDto(
    @SerializedName("current_streak")
    val currentStreak: Int,

    @SerializedName("week_days")
    val weekDays: List<StreakDayDto>,

    @SerializedName("activity_history")
    val activityHistory: List<ActivityDayDto>
)
data class StreakDayDto(
    @SerializedName("date")
    val date: String,

    @SerializedName("label")
    val label: String,

    @SerializedName("is_completed")
    val isCompleted: Boolean,

    @SerializedName("is_today")
    val isToday: Boolean = false
)

data class ActivityDayDto(
    @SerializedName("date")
    val date: String,

    @SerializedName("intensity")
    val intensity: Int
)