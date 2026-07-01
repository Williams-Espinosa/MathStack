package com.williamsel.mathstack.features.groupranking.presentacion.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.mathstack.features.groupranking.domain.usecases.GrouprankingUseCases
import com.williamsel.mathstack.features.groupranking.presentacion.screens.GrouprankingUiState
import com.williamsel.mathstack.features.groupranking.presentacion.screens.RankingTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GrouprankingViewModel @Inject constructor(
    private val useCases: GrouprankingUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val groupId: String = checkNotNull(savedStateHandle["groupId"])

    private val _uiState = MutableStateFlow(GrouprankingUiState())
    val uiState: StateFlow<GrouprankingUiState> = _uiState.asStateFlow()

    init {
        loadGroupRanking()
    }

    fun onTabSelected(tab: RankingTab) {
        _uiState.update { it.copy(selectedTab = tab) }
        when (tab) {
            RankingTab.GROUP  -> if (_uiState.value.groupRanking == null) loadGroupRanking()
            RankingTab.GLOBAL -> if (_uiState.value.globalRanking == null) loadGlobalRanking()
        }
    }

    private fun loadGroupRanking() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = useCases.getGroupRanking(groupId)
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, groupRanking = result.getOrThrow()) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading    = false,
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "No se pudo cargar el ranking del grupo"
                    )
                }
            }
        }
    }

    private fun loadGlobalRanking() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = useCases.getGlobalRanking()
            if (result.isSuccess) {
                _uiState.update { it.copy(isLoading = false, globalRanking = result.getOrThrow()) }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading    = false,
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "No se pudo cargar el ranking global"
                    )
                }
            }
        }
    }

    fun onErrorDismissed() = _uiState.update { it.copy(errorMessage = null) }
}
