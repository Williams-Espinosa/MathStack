package com.williamsel.mathstack.features.private.streak.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.mathstack.features.private.streak.domain.usecases.StreakUseCases
import com.williamsel.mathstack.features.private.streak.presentacion.screens.StreakUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StreakViewModel @Inject constructor(
    private val useCases: StreakUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(StreakUiState())
    val uiState: StateFlow<StreakUiState> = _uiState.asStateFlow()

    init {
        loadStreak()
    }

    fun loadStreak() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = useCases.getStreak()
            if (result.isSuccess) {
                val streak = result.getOrThrow()
                _uiState.update {
                    it.copy(
                        isLoading        = false,
                        currentStreak    = streak.currentStreak,
                        weekDays         = streak.weekDays,
                        activityHistory  = streak.activityHistory
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading    = false,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo cargar tu racha"
                    )
                }
            }
        }
    }
}