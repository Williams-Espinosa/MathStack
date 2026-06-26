package com.tuapp.weeklychallenges.data.models

import com.google.gson.annotations.SerializedName

data class WeeklyChallengeDto(
    @SerializedName("id")           val id: String,
    @SerializedName("title")        val title: String,
    @SerializedName("description")  val description: String,
    @SerializedName("difficulty")   val difficulty: String,
    @SerializedName("duration_days") val durationDays: Int,
    @SerializedName("coins")        val coins: Int,
    @SerializedName("xp")           val xp: Int,
    @SerializedName("progress")     val progress: Float,
    @SerializedName("participants") val participants: Int,
    @SerializedName("is_joined")    val isJoined: Boolean,
    @SerializedName("exercises")    val exercises: List<ChallengeExerciseDto>
)

data class ChallengeExerciseDto(
    @SerializedName("id")           val id: String,
    @SerializedName("question_tex") val questionTex: String,
    @SerializedName("hint_tex")     val hintTex: String?,
    @SerializedName("options")      val options: List<ExerciseOptionDto>
)

data class ExerciseOptionDto(
    @SerializedName("id")         val id: String,
    @SerializedName("label")      val label: String,
    @SerializedName("text_tex")   val textTex: String,
    @SerializedName("is_correct") val isCorrect: Boolean
)

data class JoinChallengeResponseDto(
    @SerializedName("challenge") val challenge: WeeklyChallengeDto
)

data class SubmitAnswerResponseDto(
    @SerializedName("earned_xp")     val earnedXp: Int,
    @SerializedName("is_correct")    val isCorrect: Boolean
)
