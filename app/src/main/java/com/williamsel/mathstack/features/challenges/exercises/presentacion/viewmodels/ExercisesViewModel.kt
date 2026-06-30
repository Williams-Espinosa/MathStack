package com.williamsel.mathstack.features.challenges.exercises.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import  com.williamsel.mathstack.features.challenges.exercises.domain.entities.ExerciseSession
import com.williamsel.mathstack.features.challenges.exercises.domain.entities.Lesson
import  com.williamsel.mathstack.features.challenges.exercises.domain.usecases.ExercisesUseCases
import   com.williamsel.mathstack.features.challenges.exercises.presentacion.screens.CheckResult
import  com.williamsel.mathstack.features.challenges.exercises.presentacion.screens.ExerciseUiState
import  com.williamsel.mathstack.features.challenges.exercises.presentacion.screens.LearningPathUiState
import  com.williamsel.mathstack.features.challenges.exercises.presentacion.screens.TheoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    private val useCases: ExercisesUseCases
) : ViewModel() {

    private val _pathState = MutableStateFlow<LearningPathUiState>(LearningPathUiState.Loading)
    val pathState: StateFlow<LearningPathUiState> = _pathState.asStateFlow()

    private val _theoryState = MutableStateFlow<TheoryUiState>(TheoryUiState.Idle)
    val theoryState: StateFlow<TheoryUiState> = _theoryState.asStateFlow()

    private val _exerciseState = MutableStateFlow<ExerciseUiState>(ExerciseUiState.Idle)
    val exerciseState: StateFlow<ExerciseUiState> = _exerciseState.asStateFlow()


    fun loadPath(pathId: String) {
        viewModelScope.launch {
            useCases.getLearningPath(pathId)
                .catch { e -> _pathState.value = LearningPathUiState.Error(e.localizedMessage ?: "Error") }
                .collect { _pathState.value = LearningPathUiState.Success(it) }
        }
    }

    fun openLesson(lesson: Lesson) {
        _theoryState.value = TheoryUiState.Loading
        viewModelScope.launch {
            useCases.getLessonTheory(lesson.id)
                .onSuccess { theory ->
                    _theoryState.value = TheoryUiState.Success(lesson = lesson, theory = theory)
                }
                .onFailure { e ->
                    _theoryState.value = TheoryUiState.Error(e.localizedMessage ?: "Error al cargar")
                }
        }
    }

    fun markTheoryRead() {
        val current = _theoryState.value as? TheoryUiState.Success ?: return
        _theoryState.value = current.copy(readyToStart = true)
    }

    fun startExercises() {
        val theory = _theoryState.value as? TheoryUiState.Success ?: return
        val exercises = theory.lesson.exercises
        if (exercises.isEmpty()) return
        _exerciseState.value = ExerciseUiState.Active(
            session = ExerciseSession(
                lessonId  = theory.lesson.id,
                exercises = exercises
            )
        )
    }

    fun onUserInput(text: String) {
        val current = _exerciseState.value as? ExerciseUiState.Active ?: return
        _exerciseState.value = current.copy(
            session     = current.session.copy(userInput = text),
            checkResult = null
        )
    }

    fun toggleHint() {
        val current = _exerciseState.value as? ExerciseUiState.Active ?: return
        _exerciseState.value = current.copy(hintVisible = !current.hintVisible)
    }

    fun checkAnswer() {
        val current  = _exerciseState.value as? ExerciseUiState.Active ?: return
        val exercise = current.session.current ?: return
        val input    = current.session.userInput.trim()
        if (input.isEmpty()) return

        viewModelScope.launch {
            useCases.checkAnswer(exercise.id, input)
                .onSuccess { isCorrect ->
                    val result = if (isCorrect) CheckResult.CORRECT else CheckResult.INCORRECT
                    val newXp  = if (isCorrect) current.session.earnedXp + exercise.xpReward
                                 else current.session.earnedXp
                    _exerciseState.value = current.copy(
                        session     = current.session.copy(
                            earnedXp = newXp,
                            attempts = current.session.attempts + 1
                        ),
                        checkResult = result
                    )
                }
                .onFailure { e ->
                    _exerciseState.value = ExerciseUiState.Error(e.localizedMessage ?: "Error")
                }
        }
    }

    fun nextExercise() {
        val current  = _exerciseState.value as? ExerciseUiState.Active ?: return
        val session  = current.session
        val nextIdx  = session.currentIndex + 1

        if (nextIdx >= session.totalExercises) {
            viewModelScope.launch {
                useCases.completeLesson(session.lessonId, session.earnedXp)
            }
            _exerciseState.value = ExerciseUiState.Finished(session.copy(isFinished = true))
        } else {
            _exerciseState.value = ExerciseUiState.Active(
                session = session.copy(
                    currentIndex = nextIdx,
                    userInput    = "",
                    attempts     = 0
                )
            )
        }
    }

    fun resetExerciseState() {
        _exerciseState.value = ExerciseUiState.Idle
    }

    fun resetTheoryState() {
        _theoryState.value = TheoryUiState.Idle
    }
}
