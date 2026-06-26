package com.tuapp.exercises.domain.repositories

import com.tuapp.exercises.domain.entities.LearningPath
import com.tuapp.exercises.domain.entities.LessonTheory
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
