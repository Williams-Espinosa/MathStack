package com.williamsel.mathstack.features.settings.accountmanagement.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.mathstack.features.settings.accountmanagement.domain.usecases.AccountmanagementUseCases
import com.williamsel.mathstack.features.settings.accountmanagement.presentacion.screens.AccountManagementUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountManagementViewModel @Inject constructor(
    private val useCases: AccountmanagementUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountManagementUiState())
    val uiState: StateFlow<AccountManagementUiState> = _uiState.asStateFlow()

    init {
        loadAccountInfo()
    }

    private fun loadAccountInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            useCases.getAccountInfo()
                .onSuccess { account ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            username = account.username,
                            email = account.email
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "No se pudo cargar la información de la cuenta"
                        )
                    }
                }
        }
    }

    fun onUsernameChange(newUsername: String) {
        _uiState.update { it.copy(username = newUsername) }
    }

    fun onSaveChanges() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null) }
            useCases.updateUsername(_uiState.value.username)
                .onSuccess {
                    _uiState.update {
                        it.copy(isSaving = false, successMessage = "Cambios guardados correctamente")
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = error.message ?: "No se pudieron guardar los cambios"
                        )
                    }
                }
        }
    }

    fun onDeleteAccount() {
        viewModelScope.launch {
            _uiState.update { it.copy(isDeleting = true, errorMessage = null) }
            useCases.deleteAccount()
                .onSuccess {
                    _uiState.update { it.copy(isDeleting = false, accountDeleted = true) }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isDeleting = false,
                            errorMessage = error.message ?: "No se pudo eliminar la cuenta"
                        )
                    }
                }
        }
    }

    fun onErrorDismissed() = _uiState.update { it.copy(errorMessage = null) }
    fun onSuccessDismissed() = _uiState.update { it.copy(successMessage = null) }
}
