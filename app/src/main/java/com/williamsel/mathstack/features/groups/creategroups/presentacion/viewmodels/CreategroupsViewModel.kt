package com.williamsel.mathstack.features.private.creategroups.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.mathstack.features.private.creategroups.domain.entities.CreateGroupParams
import com.williamsel.mathstack.features.private.creategroups.domain.usecases.CreategroupsUseCases
import com.williamsel.mathstack.features.private.creategroups.presentacion.screens.CreategroupsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreategroupsViewModel @Inject constructor(
    private val useCases: CreategroupsUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreategroupsUiState())
    val uiState: StateFlow<CreategroupsUiState> = _uiState.asStateFlow()

    fun onNameChange(value: String) =
        _uiState.update { it.copy(name = value) }

    fun onDescriptionChange(value: String) =
        _uiState.update { it.copy(description = value) }

    fun onSubjectChange(value: String) =
        _uiState.update { it.copy(subject = value) }

    fun onMaxMembersChange(value: Int) =
        _uiState.update { it.copy(maxMembers = value.coerceIn(5, 100)) }

    fun onCreateGroup() {
        val state = _uiState.value
        if (state.name.isBlank()) {
            _uiState.update { it.copy(errorMessage = "El nombre del grupo no puede estar vacío") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = useCases.createGroup(
                CreateGroupParams(
                    name        = state.name.trim(),
                    description = state.description.trim(),
                    subject     = state.subject,
                    maxMembers  = state.maxMembers
                )
            )
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoading      = false,
                        successGroupId = result.getOrThrow().id
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading    = false,
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "No se pudo crear el grupo"
                    )
                }
            }
        }
    }

    fun onErrorDismissed() = _uiState.update { it.copy(errorMessage = null) }

    fun onSuccessConsumed() = _uiState.update { it.copy(successGroupId = null) }
}
