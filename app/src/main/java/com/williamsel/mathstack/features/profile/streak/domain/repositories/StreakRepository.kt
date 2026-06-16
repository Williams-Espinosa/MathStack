package com.williamsel.mathstack.features.private.streak.domain.repositories

import com.williamsel.mathstack.features.private.streak.domain.entities.Streak

interface StreakRepository {
    suspend fun getStreak(): Result<Streak>
}