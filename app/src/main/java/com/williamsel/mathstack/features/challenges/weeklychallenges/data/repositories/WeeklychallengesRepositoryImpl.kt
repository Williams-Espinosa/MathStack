package com.williamsel.mathstack.features.challenges.weeklychallenges.data.repositories

import com.williamsel.mathstack.features.challenges.weeklychallenges.data.datasource.api.SaveProgressRequestBody
import com.williamsel.mathstack.features.challenges.weeklychallenges.data.datasource.api.SubmitAnswerRequestBody
import com.williamsel.mathstack.features.challenges.weeklychallenges.data.datasource.api.WeeklychallengesApi
import com.williamsel.mathstack.features.challenges.weeklychallenges.data.mapper.toDomain
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.entities.WeeklyChallenge
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.repositories.WeeklychallengesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeeklychallengesRepositoryImpl @Inject constructor(
    private val api: WeeklychallengesApi
) : WeeklychallengesRepository {

    override fun getChallenges(): Flow<List<WeeklyChallenge>> = flow {
        val challenges = api.getChallenges().map { it.toDomain() }
        val sorted = challenges.sortedWith(
            compareByDescending<WeeklyChallenge> { it.isJoined }
                .thenBy { it.difficulty.ordinal }
        )
        emit(sorted)
    }

    override suspend fun joinChallenge(challengeId: String): Result<WeeklyChallenge> =
        runCatching {
            api.joinChallenge(challengeId).challenge.toDomain()
        }

    override suspend fun submitAnswer(
        challengeId: String,
        exerciseId: String,
        optionId: String
    ): Result<Int> = runCatching {
        api.submitAnswer(
            challengeId,
            exerciseId,
            SubmitAnswerRequestBody(optionId)
        ).earnedXp
    }

    override suspend fun saveSessionProgress(
        challengeId: String,
        progressFraction: Float,
        earnedXp: Int,
        earnedCoins: Int
    ): Result<Unit> = runCatching {
        api.saveProgress(
            challengeId,
            SaveProgressRequestBody(progressFraction, earnedXp, earnedCoins)
        )
    }
}
