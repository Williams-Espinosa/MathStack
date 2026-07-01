package com.williamsel.mathstack.features.profile.streak.domain.repositories

import com.williamsel.mathstack.features.profile.streak.domain.entities.Streak

interface StreakRepository {
    suspend fun getStreak(): Result<Streak>
}