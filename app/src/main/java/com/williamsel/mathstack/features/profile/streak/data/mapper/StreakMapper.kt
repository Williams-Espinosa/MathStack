package com.williamsel.mathstack.features.profile.streak.data.mapper

import com.williamsel.mathstack.features.profile.streak.data.models.ActivityDayDto
import com.williamsel.mathstack.features.profile.streak.data.models.StreakDayDto
import com.williamsel.mathstack.features.profile.streak.data.models.StreakDto
import com.williamsel.mathstack.features.profile.streak.domain.entities.ActivityDay
import com.williamsel.mathstack.features.profile.streak.domain.entities.Streak
import com.williamsel.mathstack.features.profile.streak.domain.entities.StreakDay
import java.time.LocalDate

fun StreakDto.toDomain(): Streak = Streak(
    currentStreak    = currentStreak,
    weekDays         = weekDays.map { it.toDomain() },
    activityHistory  = activityHistory.map { it.toDomain() }
)

fun StreakDayDto.toDomain(): StreakDay = StreakDay(
    date        = LocalDate.parse(date),
    label       = label,
    isCompleted = isCompleted,
    isToday     = isToday
)

fun ActivityDayDto.toDomain(): ActivityDay = ActivityDay(
    date      = LocalDate.parse(date),
    intensity = intensity.coerceIn(0, 4)
)