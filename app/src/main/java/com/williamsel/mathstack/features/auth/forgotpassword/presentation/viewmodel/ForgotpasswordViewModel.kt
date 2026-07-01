package com.williamsel.mathstack.features.auth.forgotpassword.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.mathstack.features.auth.forgotpassword.domain.usecase.ForgotpasswordUseCases
import com.williamsel.mathstack.features.auth.forgotpassword.presentation.screen.ForgotPasswordStep
import com.williamsel.mathstack.features.auth.forgotpassword.presentation.screen.ForgotpasswordUiState
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
class ForgotpasswordViewModel @Inject constructor(
    private val useCases: ForgotpasswordUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotpasswordUiState())
    val uiState: StateFlow<ForgotpasswordUiState> = _uiState.asStateFlow()

    private var countdownJob: Job? = null


    fun onEmailChange(value: String) =
        _uiState.update { it.copy(email = value, errorMessage = null) }

    fun onSendCode() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = useCases.sendResetCode(_uiState.value.email)
            _uiState.update { it.copy(isLoading = false) }
            if (result.isSuccess) {
                _uiState.update { it.copy(step = ForgotPasswordStep.ENTER_CODE) }
                startCountdown()
            } else {
                _uiState.update {
                    it.copy(errorMessage = result.exceptionOrNull()?.message ?: "Error al enviar el código")
                }
            }
        }
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
            val result = useCases.verifyResetCode(state.email, state.fullCode)
            _uiState.update { it.copy(isLoading = false) }
            if (result.isSuccess) {
                _uiState.update { it.copy(step = ForgotPasswordStep.SUCCESS) }
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
        _uiState.update { it.copy(codeDigits = List(6) { "" }, errorMessage = null) }
        onSendCode()
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
