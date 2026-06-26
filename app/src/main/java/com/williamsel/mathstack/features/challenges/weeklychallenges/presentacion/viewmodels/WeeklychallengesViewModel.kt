package com.tuapp.weeklychallenges.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuapp.weeklychallenges.domain.entities.ChallengeSession
import com.tuapp.weeklychallenges.domain.entities.WeeklyChallenge
import com.tuapp.weeklychallenges.domain.usecases.WeeklychallengesUseCases
import com.tuapp.weeklychallenges.presentacion.screens.AnswerResult
import com.tuapp.weeklychallenges.presentacion.screens.ExerciseUiState
import com.tuapp.weeklychallenges.presentacion.screens.WeeklychallengesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeeklychallengesViewModel @Inject constructor(
    private val useCases: WeeklychallengesUseCases
) : ViewModel() {

    private val _listState = MutableStateFlow<WeeklychallengesUiState>(WeeklychallengesUiState.Loading)
    val listState: StateFlow<WeeklychallengesUiState> = _listState.asStateFlow()

    private val _exerciseState = MutableStateFlow<ExerciseUiState>(ExerciseUiState.Idle)
    val exerciseState: StateFlow<ExerciseUiState> = _exerciseState.asStateFlow()

    init {
        loadChallenges()
    }

    fun loadChallenges() {
        viewModelScope.launch {
            useCases.getChallenges()
                .catch { e ->
                    _listState.value = WeeklychallengesUiState.Error(
                        e.localizedMessage ?: "Error desconocido"
                    )
                }
                .collect { challenges ->
                    _listState.value = WeeklychallengesUiState.Success(
                        joinedChallenges    = challenges.filter { it.isJoined },
                        availableChallenges = challenges.filter { !it.isJoined }
                    )
                }
        }
    }

    fun joinChallenge(challenge: WeeklyChallenge) {
        viewModelScope.launch {
            useCases.joinChallenge(challenge.id)
                .onSuccess { updated ->
                    val current = _listState.value
                    if (current is WeeklychallengesUiState.Success) {
                        _listState.value = current.copy(
                            joinedChallenges    = current.joinedChallenges + updated,
                            availableChallenges = current.availableChallenges.filter { it.id != updated.id },
                            joinToast           = "Te uniste a \"${updated.title}\""
                        )
                    }
                }
                .onFailure { e ->
                    _listState.value = WeeklychallengesUiState.Error(
                        e.localizedMessage ?: "No se pudo unir al reto"
                    )
                }
        }
    }

    fun clearJoinToast() {
        val current = _listState.value
        if (current is WeeklychallengesUiState.Success) {
            _listState.value = current.copy(joinToast = null)
        }
    }

        fun startChallenge(challenge: WeeklyChallenge) {
        _exerciseState.value = ExerciseUiState.Question(
            session = ChallengeSession(challenge = challenge)
        )
    }

    fun selectOption(optionId: String) {
        val current = _exerciseState.value as? ExerciseUiState.Question ?: return
        _exerciseState.value = current.copy(selectedOptionId = optionId, hintVisible = false)
    }

    fun toggleHint() {
        val current = _exerciseState.value as? ExerciseUiState.Question ?: return
        _exerciseState.value = current.copy(hintVisible = !current.hintVisible)
    }

    fun checkAnswer() {
        val current = _exerciseState.value as? ExerciseUiState.Question ?: return
        val session  = current.session
        val exercise = session.challenge.exercises.getOrNull(session.currentExerciseIndex) ?: return
        val optionId = current.selectedOptionId ?: return

        viewModelScope.launch {
            useCases.submitAnswer(session.challenge.id, exercise.id, optionId)
                .onSuccess { earnedXp ->
                    val correctOption = exercise.options.first { it.isCorrect }
                    val result = AnswerResult(
                        isCorrect       = correctOption.id == optionId,
                        earnedXp        = earnedXp,
                        correctOptionId = correctOption.id
                    )
                    val updatedAnswers = session.answers + (exercise.id to optionId)
                    _exerciseState.value = current.copy(
                        session = session.copy(
                            answers   = updatedAnswers,
                            earnedXp  = session.earnedXp + earnedXp,
                            earnedCoins = session.earnedCoins + if (result.isCorrect) 10 else 0
                        ),
                        answerResult = result
                    )
                }
                .onFailure { e ->
                    _exerciseState.value = ExerciseUiState.Error(
                        e.localizedMessage ?: "Error al comprobar respuesta"
                    )
                }
        }
    }

    fun nextExercise() {
        val current = _exerciseState.value as? ExerciseUiState.Question ?: return
        val session  = current.session
        val nextIndex = session.currentExerciseIndex + 1

        if (nextIndex >= session.challenge.exercises.size) {
            viewModelScope.launch {
                useCases.saveProgress(
                    challengeId      = session.challenge.id,
                    progressFraction = session.correctCount.toFloat() / session.totalExercises,
                    earnedXp         = session.earnedXp,
                    earnedCoins      = session.earnedCoins
                )
            }
            _exerciseState.value = ExerciseUiState.SessionFinished(
                session.copy(isFinished = true)
            )
        } else {
            _exerciseState.value = ExerciseUiState.Question(
                session = session.copy(currentExerciseIndex = nextIndex),
                selectedOptionId = null,
                hintVisible      = false,
                answerResult     = null
            )
        }
    }

    fun resetExerciseState() {
        _exerciseState.value = ExerciseUiState.Idle
    }
}
