package com.williamsel.mathstack.features.profile.streak.data.repositories

import com.williamsel.mathstack.features.profile.streak.data.datasource.api.StreakApi
import com.williamsel.mathstack.features.profile.streak.data.mapper.toDomain
import com.williamsel.mathstack.features.profile.streak.domain.entities.Streak
import com.williamsel.mathstack.features.profile.streak.domain.repositories.StreakRepository
import javax.inject.Inject

class StreakRepositoryImpl @Inject constructor(
    private val api: StreakApi
) : StreakRepository {

    override suspend fun getStreak(): Result<Streak> = runCatching {
        api.getStreak().toDomain()
    }
}