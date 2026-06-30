package com.williamsel.mathstack.features.challenges.weeklychallenges.domain.repositories

import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.entities.WeeklyChallenge
import kotlinx.coroutines.flow.Flow

interface WeeklychallengesRepository {

    fun getChallenges(): Flow<List<WeeklyChallenge>>

    suspend fun joinChallenge(challengeId: String): Result<WeeklyChallenge>

    suspend fun submitAnswer(
        challengeId: String,
        exerciseId: String,
        optionId: String
    ): Result<Int>

    suspend fun saveSessionProgress(
        challengeId: String,
        progressFraction: Float,
        earnedXp: Int,
        earnedCoins: Int
    ): Result<Unit>
}
