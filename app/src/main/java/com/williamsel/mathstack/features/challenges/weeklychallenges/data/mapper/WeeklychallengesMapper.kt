package com.williamsel.mathstack.features.challenges.weeklychallenges.data.mapper

import com.williamsel.mathstack.features.challenges.weeklychallenges.data.models.ChallengeExerciseDto
import com.williamsel.mathstack.features.challenges.weeklychallenges.data.models.ExerciseOptionDto
import com.williamsel.mathstack.features.challenges.weeklychallenges.data.models.WeeklyChallengeDto
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.entities.ChallengeExercise
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.entities.ExerciseOption
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.entities.WeeklyChallenge
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.entities.ChallengeDifficulty

fun WeeklyChallengeDto.toDomain(): WeeklyChallenge = WeeklyChallenge(
    id           = id,
    title        = title,
    description  = description,
    difficulty   = difficulty.toDifficulty(),
    durationDays = durationDays,
    coins        = coins,
    xp           = xp,
    progress     = progress.coerceIn(0f, 1f),
    participants = participants,
    isJoined     = isJoined,
    exercises    = exercises.map { it.toDomain() }
)

fun ChallengeExerciseDto.toDomain(): ChallengeExercise = ChallengeExercise(
    id            = id,
    questionLatex = questionTex,
    hintLatex     = hintTex,
    options       = options.map { it.toDomain() }
)

fun ExerciseOptionDto.toDomain(): ExerciseOption = ExerciseOption(
    id        = id,
    label     = label,
    textLatex = textTex,
    isCorrect = isCorrect
)

private fun String.toDifficulty(): ChallengeDifficulty = when (lowercase()) {
    "easy", "fácil"  -> ChallengeDifficulty.EASY
    "medium", "medio" -> ChallengeDifficulty.MEDIUM
    else              -> ChallengeDifficulty.HARD
}
