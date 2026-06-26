package com.tuapp.exercises.data.models

import com.google.gson.annotations.SerializedName

data class LearningPathDto(
    @SerializedName("id")                val id: String,
    @SerializedName("title")             val title: String,
    @SerializedName("total_lessons")     val totalLessons: Int,
    @SerializedName("total_xp")          val totalXp: Int,
    @SerializedName("completed_lessons") val completedLessons: Int,
    @SerializedName("lessons")           val lessons: List<LessonDto>
)

data class LessonDto(
    @SerializedName("id")          val id: String,
    @SerializedName("number")      val number: Int,
    @SerializedName("title")       val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("xp_reward")   val xpReward: Int,
    @SerializedName("status")      val status: String,
    @SerializedName("exercises")   val exercises: List<ExerciseDto>
)


data class LessonTheoryDto(
    @SerializedName("lesson_id")           val lessonId: String,
    @SerializedName("estimated_minutes")   val estimatedMinutes: Int,
    @SerializedName("sections")            val sections: List<TheorySectionDto>
)

data class TheorySectionDto(
    @SerializedName("type")            val type: String,
    @SerializedName("text")            val text: String?,
    @SerializedName("title")           val title: String?,
    @SerializedName("problem_tex")     val problemTex: String?,
    @SerializedName("steps")           val steps: List<String>?,
    @SerializedName("body")            val body: String?
)

data class ExerciseDto(
    @SerializedName("id")               val id: String,
    @SerializedName("number")           val number: Int,
    @SerializedName("instruction")      val instruction: String,
    @SerializedName("question_tex")     val questionTex: String,
    @SerializedName("hint_tex")         val hintTex: String?,
    @SerializedName("answer_tex")       val answerTex: String,
    @SerializedName("accepted_answers") val acceptedAnswers: List<String>,
    @SerializedName("xp_reward")        val xpReward: Int
)

data class CheckAnswerResponseDto(
    @SerializedName("is_correct") val isCorrect: Boolean
)
