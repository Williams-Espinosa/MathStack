package com.tuapp.weeklychallenges.domain.usecases

import com.tuapp.weeklychallenges.domain.entities.WeeklyChallenge
import com.tuapp.weeklychallenges.domain.repositories.WeeklychallengesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeeklyChallengesUseCase @Inject constructor(
    private val repository: WeeklychallengesRepository
) {
    operator fun invoke(): Flow<List<WeeklyChallenge>> = repository.getChallenges()
}

class JoinChallengeUseCase @Inject constructor(
    private val repository: WeeklychallengesRepository
) {
    suspend operator fun invoke(challengeId: String): Result<WeeklyChallenge> =
        repository.joinChallenge(challengeId)
}

class SubmitAnswerUseCase @Inject constructor(
    private val repository: WeeklychallengesRepository
) {
    suspend operator fun invoke(
        challengeId: String,
        exerciseId: String,
        optionId: String
    ): Result<Int> = repository.submitAnswer(challengeId, exerciseId, optionId)
}

class SaveSessionProgressUseCase @Inject constructor(
    private val repository: WeeklychallengesRepository
) {
    suspend operator fun invoke(
        challengeId: String,
        progressFraction: Float,
        earnedXp: Int,
        earnedCoins: Int
    ): Result<Unit> = repository.saveSessionProgress(challengeId, progressFraction, earnedXp, earnedCoins)
}

/** Convenience wrapper injected into the ViewModel */
data class WeeklychallengesUseCases(
    val getChallenges: GetWeeklyChallengesUseCase,
    val joinChallenge: JoinChallengeUseCase,
    val submitAnswer: SubmitAnswerUseCase,
    val saveProgress: SaveSessionProgressUseCase
)
