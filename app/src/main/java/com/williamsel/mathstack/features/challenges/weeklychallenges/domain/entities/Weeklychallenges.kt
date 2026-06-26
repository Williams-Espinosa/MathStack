package com.tuapp.weeklychallenges.domain.entities

data class WeeklyChallenge(
    val id: String,
    val title: String,
    val description: String,
    val difficulty: ChallengeDifficulty,
    val durationDays: Int,
    val coins: Int,
    val xp: Int,
    val progress: Float,           // 0.0 – 1.0
    val participants: Int,
    val isJoined: Boolean,
    val exercises: List<ChallengeExercise>
)

data class ChallengeExercise(
    val id: String,
    val questionLatex: String,     // LaTeX string, e.g. "3x - 7 = 14"
    val options: List<ExerciseOption>,
    val hintLatex: String? = null
)

data class ExerciseOption(
    val id: String,
    val label: String,             // "A", "B", "C", "D"
    val textLatex: String,         // LaTeX, e.g. "x = 7"
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
    val answers: Map<String, String> = emptyMap(),  // exerciseId -> optionId
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
