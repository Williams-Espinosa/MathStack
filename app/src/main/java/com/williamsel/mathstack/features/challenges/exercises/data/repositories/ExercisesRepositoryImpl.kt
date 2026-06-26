package com.tuapp.exercises.data.repositories

import com.tuapp.exercises.data.datasource.api.CheckAnswerRequestBody
import com.tuapp.exercises.data.datasource.api.CompleteLessonRequestBody
import com.tuapp.exercises.data.datasource.api.ExercisesApi
import com.tuapp.exercises.data.mapper.toDomain
import com.tuapp.exercises.domain.entities.LearningPath
import com.tuapp.exercises.domain.entities.LessonTheory
import com.tuapp.exercises.domain.repositories.ExercisesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExercisesRepositoryImpl @Inject constructor(
    private val api: ExercisesApi
) : ExercisesRepository {

    override fun getLearningPath(pathId: String): Flow<LearningPath> = flow {
        emit(api.getLearningPath(pathId).toDomain())
    }

    override suspend fun getLessonTheory(lessonId: String): Result<LessonTheory> =
        runCatching { api.getLessonTheory(lessonId).toDomain() }

    override suspend fun checkAnswer(
        exerciseId: String,
        userAnswer: String
    ): Result<Boolean> = runCatching {
        api.checkAnswer(exerciseId, CheckAnswerRequestBody(userAnswer.trim())).isCorrect
    }

    override suspend fun completeLesson(lessonId: String, earnedXp: Int): Result<Unit> =
        runCatching { api.completeLesson(lessonId, CompleteLessonRequestBody(earnedXp)) }
}
