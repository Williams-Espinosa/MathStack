package com.tuapp.exercises.presentacion.screens

import com.tuapp.exercises.domain.entities.ExerciseSession
import com.tuapp.exercises.domain.entities.LearningPath
import com.tuapp.exercises.domain.entities.Lesson
import com.tuapp.exercises.domain.entities.LessonTheory

// ── Learning path list ────────────────────────────────────────────────────────
sealed interface LearningPathUiState {
    data object Loading : LearningPathUiState
    data class Success(val path: LearningPath) : LearningPathUiState
    data class Error(val message: String) : LearningPathUiState
}

// ── Theory reader screen ──────────────────────────────────────────────────────
sealed interface TheoryUiState {
    data object Idle : TheoryUiState
    data object Loading : TheoryUiState
    data class Success(
        val lesson: Lesson,
        val theory: LessonTheory,
        val readyToStart: Boolean = false   // becomes true when user scrolls to bottom
    ) : TheoryUiState
    data class Error(val message: String) : TheoryUiState
}

// ── Exercise session ──────────────────────────────────────────────────────────
sealed interface ExerciseUiState {
    data object Idle : ExerciseUiState
    data object Loading : ExerciseUiState

    data class Active(
        val session: ExerciseSession,
        val hintVisible: Boolean = false,
        val checkResult: CheckResult? = null    // null = not yet checked
    ) : ExerciseUiState

    data class Finished(val session: ExerciseSession) : ExerciseUiState
    data class Error(val message: String) : ExerciseUiState
}

enum class CheckResult { CORRECT, INCORRECT }
