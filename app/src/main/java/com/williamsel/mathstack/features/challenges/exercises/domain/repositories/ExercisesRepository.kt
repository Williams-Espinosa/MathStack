package com.williamsel.mathstack.features.challenges.exercises.domain.repositories
import  com.williamsel.mathstack.features.challenges.exercises.domain.entities.LearningPath
import  com.williamsel.mathstack.features.challenges.exercises.domain.entities.LessonTheory
import kotlinx.coroutines.flow.Flow

interface ExercisesRepository {
    fun getLearningPath(pathId: String): Flow<LearningPath>

    suspend fun getLessonTheory(lessonId: String): Result<LessonTheory>
    suspend fun checkAnswer(
        exerciseId: String,
        userAnswer: String
    ): Result<Boolean>
    suspend fun completeLesson(lessonId: String, earnedXp: Int): Result<Unit>
}
