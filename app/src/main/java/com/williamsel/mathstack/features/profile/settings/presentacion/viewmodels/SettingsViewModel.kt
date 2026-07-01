package com.williamsel.mathstack.features.settings.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.mathstack.features.settings.domain.entities.Settings
import com.williamsel.mathstack.features.settings.domain.usecases.SettingsUseCases
import com.williamsel.mathstack.features.settings.presentacion.screens.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val useCases: SettingsUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    fun loadSettings() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = useCases.getSettings()
            if (result.isSuccess) {
                val s = result.getOrThrow()
                _uiState.update {
                    it.copy(
                        isLoading                = false,
                        notificationsEnabled     = s.notificationsEnabled,
                        darkModeEnabled          = s.darkModeEnabled,
                        practiceRemindersEnabled = s.practiceRemindersEnabled
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading    = false,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo cargar la configuración"
                    )
                }
            }
        }
    }
    fun onNotificationsToggle(enabled: Boolean) {
        _uiState.update { it.copy(notificationsEnabled = enabled) }
        saveSettings()
    }

    fun onDarkModeToggle(enabled: Boolean) {
        _uiState.update { it.copy(darkModeEnabled = enabled) }
        saveSettings()
    }

    fun onPracticeRemindersToggle(enabled: Boolean) {
        _uiState.update { it.copy(practiceRemindersEnabled = enabled) }
        saveSettings()
    }

    private fun saveSettings() {
        val current = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            val result = useCases.updateSettings(
                Settings(
                    notificationsEnabled     = current.notificationsEnabled,
                    darkModeEnabled          = current.darkModeEnabled,
                    practiceRemindersEnabled = current.practiceRemindersEnabled
                )
            )
            _uiState.update { it.copy(isSaving = false) }
            if (result.isFailure) {
                _uiState.update {
                    it.copy(errorMessage = result.exceptionOrNull()?.message ?: "No se pudo guardar")
                }
            }
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoggingOut = true, errorMessage = null) }
            val result = useCases.logout()
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoggingOut = false, logoutSuccess = true) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoggingOut = false,
                        errorMessage = result.exceptionOrNull()?.message ?: "Error al cerrar sesión"
                    )
                }
            }
        }
    }

    fun onErrorDismissed() = _uiState.update { it.copy(errorMessage = null) }
}