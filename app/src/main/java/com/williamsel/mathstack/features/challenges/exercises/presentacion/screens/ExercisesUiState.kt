package com.williamsel.mathstack.features.challenges.exercises.presentacion.screens
import  com.williamsel.mathstack.features.challenges.exercises.domain.entities.ExerciseSession
import  com.williamsel.mathstack.features.challenges.exercises.domain.entities.LearningPath
import  com.williamsel.mathstack.features.challenges.exercises.domain.entities.Lesson
import  com.williamsel.mathstack.features.challenges.exercises.domain.entities.LessonTheory

sealed interface LearningPathUiState {
    data object Loading : LearningPathUiState
    data class Success(val path: LearningPath) : LearningPathUiState
    data class Error(val message: String) : LearningPathUiState
}

sealed interface TheoryUiState {
    data object Idle : TheoryUiState
    data object Loading : TheoryUiState
    data class Success(
        val lesson: Lesson,
        val theory: LessonTheory,
        val readyToStart: Boolean = false
    ) : TheoryUiState
    data class Error(val message: String) : TheoryUiState
}
sealed interface ExerciseUiState {
    data object Idle : ExerciseUiState
    data object Loading : ExerciseUiState

    data class Active(
        val session: ExerciseSession,
        val hintVisible: Boolean = false,
        val checkResult: CheckResult? = null
    ) : ExerciseUiState

    data class Finished(val session: ExerciseSession) : ExerciseUiState
    data class Error(val message: String) : ExerciseUiState
}

enum class CheckResult { CORRECT, INCORRECT }
