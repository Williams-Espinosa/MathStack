package com.williamsel.mathstack.features.challenges.weeklychallenges.presentacion.screens

import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.entities.ChallengeSession
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.entities.WeeklyChallenge


sealed interface WeeklychallengesUiState {
    data object Loading : WeeklychallengesUiState
    data class Success(
        val joinedChallenges: List<WeeklyChallenge>,
        val availableChallenges: List<WeeklyChallenge>,
        val joinToast: String? = null
    ) : WeeklychallengesUiState
    data class Error(val message: String) : WeeklychallengesUiState
}

sealed interface ExerciseUiState {
    data object Idle : ExerciseUiState
    data object Loading : ExerciseUiState

    data class Question(
        val session: ChallengeSession,
        val selectedOptionId: String? = null,
        val hintVisible: Boolean = false,
        val answerResult: AnswerResult? = null
    ) : ExerciseUiState

    data class SessionFinished(
        val session: ChallengeSession
    ) : ExerciseUiState

    data class Error(val message: String) : ExerciseUiState
}

data class AnswerResult(
    val isCorrect: Boolean,
    val earnedXp: Int,
    val correctOptionId: String
)
