package com.williamsel.mathstack.features.private.profile.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.mathstack.features.private.profile.domain.usecases.ProfileUseCases
import com.williamsel.mathstack.features.private.profile.presentacion.screens.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCases: ProfileUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = useCases.getProfile()
            if (result.isSuccess) {
                val p = result.getOrThrow()
                _uiState.update {
                    it.copy(
                        isLoading            = false,
                        username             = p.username,
                        email                = p.email,
                        avatarEmoji          = p.avatarEmoji,
                        level                = p.level,
                        totalXp              = p.totalXp,
                        coins                = p.coins,
                        streak               = p.streak,
                        levelProgress        = p.levelProgress,
                        xpRemaining          = p.xpRemaining,
                        nextLevel            = p.level + 1,
                        lessonsCompleted     = p.lessonsCompleted,
                        achievementsUnlocked = p.achievementsUnlocked,
                        achievements         = p.achievements
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading    = false,
                        errorMessage = result.exceptionOrNull()?.message ?: "No se pudo cargar el perfil"
                    )
                }
            }
        }
    }
}