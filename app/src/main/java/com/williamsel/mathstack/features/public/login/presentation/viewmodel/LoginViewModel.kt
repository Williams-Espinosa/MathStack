package com.williamsel.mathstack.features.public.login.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.mathstack.features.public.login.domain.usecase.LoginUseCase
import com.williamsel.mathstack.features.public.login.domain.usecase.LoginWithGoogleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginWithGoogleUseCase: LoginWithGoogleUseCase
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onEmailChange(newValue: String) {
        email = newValue
        errorMessage = null
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
        errorMessage = null
    }

    fun onLoginClick(onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Completa todos los campos"
            return
        }
        viewModelScope.launch {
            isLoading = true
            val result = loginUseCase(email, password)
            isLoading = false
            if (result.isSuccess) {
                onSuccess()
            } else {
                errorMessage = result.exceptionOrNull()?.message
                    ?: "Error al iniciar sesión"
            }
        }
    }

    fun onGoogleLoginClick(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            val result = loginWithGoogleUseCase()
            isLoading = false
            if (result.isSuccess) {
                onSuccess()
            } else {
                errorMessage = result.exceptionOrNull()?.message
                    ?: "Error al iniciar sesión con Google"
            }
        }
    }
}