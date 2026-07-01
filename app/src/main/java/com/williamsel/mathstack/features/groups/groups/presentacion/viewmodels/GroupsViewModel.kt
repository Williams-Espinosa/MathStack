package com.williamsel.mathstack.features.groups.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.mathstack.features.groups.domain.usecases.GroupsUseCases
import com.williamsel.mathstack.features.groups.presentacion.screens.GroupsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    private val useCases: GroupsUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(GroupsUiState())
    val uiState: StateFlow<GroupsUiState> = _uiState.asStateFlow()

    init {
        loadGroups()
    }

    fun loadGroups() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = useCases.getGroups()
            if (result.isSuccess) {
                val data = result.getOrThrow()
                _uiState.update {
                    it.copy(
                        isLoading     = false,
                        myGroupsCount = data.myGroupsCount,
                        groups        = data.groups
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading    = false,
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "No se pudieron cargar los grupos"
                    )
                }
            }
        }
    }

    fun onLeaveGroup(groupId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(processingGroupId = groupId, errorMessage = null) }
            val result = useCases.leaveGroup(groupId)
            if (result.isSuccess) {
                loadGroups()
            } else {
                _uiState.update {
                    it.copy(
                        processingGroupId = null,
                        errorMessage      = result.exceptionOrNull()?.message
                            ?: "No se pudo abandonar el grupo"
                    )
                }
            }
        }
    }

    fun onErrorDismissed() = _uiState.update { it.copy(errorMessage = null) }
}