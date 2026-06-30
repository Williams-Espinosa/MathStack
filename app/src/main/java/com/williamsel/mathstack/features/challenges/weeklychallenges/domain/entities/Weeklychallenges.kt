package com.williamsel.mathstack.features.challenges.weeklychallenges.domain.entities

data class WeeklyChallenge(
    val id: String,
    val title: String,
    val description: String,
    val difficulty: ChallengeDifficulty,
    val durationDays: Int,
    val coins: Int,
    val xp: Int,
    val progress: Float,
    val participants: Int,
    val isJoined: Boolean,
    val exercises: List<ChallengeExercise>
)

data class ChallengeExercise(
    val id: String,
    val questionLatex: String,
    val options: List<ExerciseOption>,
    val hintLatex: String? = null
)

data class ExerciseOption(
    val id: String,
    val label: String,
    val textLatex: String,
    val isCorrect: Boolean
)

enum class ChallengeDifficulty(val label: String) {
    EASY("Fácil"),
    MEDIUM("Medio"),
    HARD("Difícil")
}

data class ChallengeSession(
    val challenge: WeeklyChallenge,
    val currentExerciseIndex: Int = 0,
    val answers: Map<String, String> = emptyMap(),
    val earnedXp: Int = 0,
    val earnedCoins: Int = 0,
    val isFinished: Boolean = false
) {
    val correctCount: Int get() = challenge.exercises.count { exercise ->
        val answeredOptionId = answers[exercise.id]
        exercise.options.find { it.id == answeredOptionId }?.isCorrect == true
    }

    val totalExercises: Int get() = challenge.exercises.size

    val sessionProgress: Float get() =
        if (totalExercises == 0) 0f else currentExerciseIndex.toFloat() / totalExercises
}
