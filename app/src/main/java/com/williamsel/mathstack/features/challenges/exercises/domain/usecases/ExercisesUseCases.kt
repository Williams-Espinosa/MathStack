package com.tuapp.exercises.domain.usecases

import com.tuapp.exercises.domain.entities.LearningPath
import com.tuapp.exercises.domain.entities.LessonTheory
import com.tuapp.exercises.domain.repositories.ExercisesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLearningPathUseCase @Inject constructor(
    private val repository: ExercisesRepository
) {
    operator fun invoke(pathId: String): Flow<LearningPath> =
        repository.getLearningPath(pathId)
}

class GetLessonTheoryUseCase @Inject constructor(
    private val repository: ExercisesRepository
) {
    suspend operator fun invoke(lessonId: String): Result<LessonTheory> =
        repository.getLessonTheory(lessonId)
}

class CheckAnswerUseCase @Inject constructor(
    private val repository: ExercisesRepository
) {
    suspend operator fun invoke(exerciseId: String, userAnswer: String): Result<Boolean> =
        repository.checkAnswer(exerciseId, userAnswer)
}

class CompleteLessonUseCase @Inject constructor(
    private val repository: ExercisesRepository
) {
    suspend operator fun invoke(lessonId: String, earnedXp: Int): Result<Unit> =
        repository.completeLesson(lessonId, earnedXp)
}
data class ExercisesUseCases(
    val getLearningPath : GetLearningPathUseCase,
    val getLessonTheory : GetLessonTheoryUseCase,
    val checkAnswer     : CheckAnswerUseCase,
    val completeLesson  : CompleteLessonUseCase
)
