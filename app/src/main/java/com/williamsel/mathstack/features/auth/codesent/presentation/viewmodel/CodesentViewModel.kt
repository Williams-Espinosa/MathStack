package com.williamsel.mathstack.features.auth.codesent.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.mathstack.features.auth.codesent.domain.usecase.CodesentUseCases
import com.williamsel.mathstack.features.auth.codesent.presentation.screen.CodesentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodesentViewModel @Inject constructor(
    private val useCases: CodesentUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        CodesentUiState(
            email = savedStateHandle.get<String>("email") ?: ""
        )
    )
    val uiState: StateFlow<CodesentUiState> = _uiState.asStateFlow()

    private var countdownJob: Job? = null

    init {
        startCountdown()
    }

    fun onDigitChange(index: Int, value: String) {
        if (value.length > 1) return
        val digits = _uiState.value.codeDigits.toMutableList()
        digits[index] = value.filter { it.isDigit() }
        _uiState.update { it.copy(codeDigits = digits, errorMessage = null) }
    }

    fun onVerifyCode() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = useCases.verifyCode(state.email, state.fullCode)
            _uiState.update { it.copy(isLoading = false) }
            if (result.isSuccess) {
                _uiState.update { it.copy(isVerified = true) }
                countdownJob?.cancel()
            } else {
                _uiState.update {
                    it.copy(errorMessage = result.exceptionOrNull()?.message ?: "Código incorrecto")
                }
            }
        }
    }

    fun onResendCode() {
        if (!_uiState.value.canResend) return
        viewModelScope.launch {
            _uiState.update { it.copy(codeDigits = List(6) { "" }, isLoading = true, errorMessage = null) }
            val result = useCases.resendCode(_uiState.value.email)
            _uiState.update { it.copy(isLoading = false) }
            if (result.isSuccess) {
                startCountdown()
            } else {
                _uiState.update {
                    it.copy(errorMessage = result.exceptionOrNull()?.message ?: "Error al reenviar el código")
                }
            }
        }
    }

    fun onErrorDismissed() = _uiState.update { it.copy(errorMessage = null) }

    private fun startCountdown() {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            _uiState.update { it.copy(resendCountdown = 60, canResend = false) }
            for (remaining in 59 downTo 0) {
                delay(1_000)
                _uiState.update { it.copy(resendCountdown = remaining) }
            }
            _uiState.update { it.copy(canResend = true) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }
}
