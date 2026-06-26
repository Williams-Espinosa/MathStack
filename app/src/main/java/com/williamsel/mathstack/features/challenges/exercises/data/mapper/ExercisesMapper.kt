package com.tuapp.exercises.data.mapper

import com.tuapp.exercises.data.models.ExerciseDto
import com.tuapp.exercises.data.models.LearningPathDto
import com.tuapp.exercises.data.models.LessonDto
import com.tuapp.exercises.data.models.LessonTheoryDto
import com.tuapp.exercises.data.models.TheorySectionDto
import com.tuapp.exercises.domain.entities.Exercise
import com.tuapp.exercises.domain.entities.LearningPath
import com.tuapp.exercises.domain.entities.Lesson
import com.tuapp.exercises.domain.entities.LessonStatus
import com.tuapp.exercises.domain.entities.LessonTheory
import com.tuapp.exercises.domain.entities.TheorySection

fun LearningPathDto.toDomain(): LearningPath = LearningPath(
    id               = id,
    title            = title,
    totalLessons     = totalLessons,
    totalXp          = totalXp,
    completedLessons = completedLessons,
    lessons          = lessons.map { it.toDomain() }
)

fun LessonDto.toDomain(): Lesson = Lesson(
    id          = id,
    number      = number,
    title       = title,
    description = description,
    xpReward    = xpReward,
    status      = status.toStatus(),
    theory      = null,
    exercises   = exercises.map { it.toDomain() }
)

fun LessonTheoryDto.toDomain(): LessonTheory = LessonTheory(
    lessonId           = lessonId,
    estimatedMinutes   = estimatedMinutes,
    sections           = sections.mapNotNull { it.toDomain() }
)

fun TheorySectionDto.toDomain(): TheorySection? = when (type) {
    "introduction" -> text?.let { TheorySection.Introduction(it) }
    "example"      -> if (title != null && problemTex != null)
        TheorySection.WorkedExample(title, problemTex, steps ?: emptyList())
    else null
    "property"     -> if (title != null && body != null)
        TheorySection.PropertyBox(title, body)
    else null
    else -> null
}

fun ExerciseDto.toDomain(): Exercise = Exercise(
    id              = id,
    number          = number,
    instructionText = instruction,
    questionLatex   = questionTex,
    hintLatex       = hintTex,
    answerLatex     = answerTex,
    acceptedAnswers = acceptedAnswers,
    xpReward        = xpReward
)

private fun String.toStatus(): LessonStatus = when (lowercase()) {
    "completed" -> LessonStatus.COMPLETED
    "available" -> LessonStatus.AVAILABLE
    else        -> LessonStatus.LOCKED
}
