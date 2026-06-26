package com.tuapp.weeklychallenges.data.mapper

import com.tuapp.weeklychallenges.data.models.ChallengeExerciseDto
import com.tuapp.weeklychallenges.data.models.ExerciseOptionDto
import com.tuapp.weeklychallenges.data.models.WeeklyChallengeDto
import com.tuapp.weeklychallenges.domain.entities.ChallengeDifficulty
import com.tuapp.weeklychallenges.domain.entities.ChallengeExercise
import com.tuapp.weeklychallenges.domain.entities.ExerciseOption
import com.tuapp.weeklychallenges.domain.entities.WeeklyChallenge

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
