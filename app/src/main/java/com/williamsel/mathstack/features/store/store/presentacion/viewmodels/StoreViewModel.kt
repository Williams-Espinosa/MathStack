package com.williamsel.mathstack.features.store.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.williamsel.mathstack.features.store.domain.entities.Store
import com.williamsel.mathstack.features.store.domain.usecases.StoreUseCases
import com.williamsel.mathstack.features.store.presentacion.screens.StoreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val useCases: StoreUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(StoreUiState())
    val uiState: StateFlow<StoreUiState> = _uiState.asStateFlow()

    private var currentStore: Store = Store(coinBalance = 0, avatars = emptyList())

    init {
        loadStore()
    }
    fun loadStore() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = useCases.getStore()
            if (result.isSuccess) {
                val store = result.getOrThrow()
                currentStore = store
                _uiState.update {
                    it.copy(
                        isLoading   = false,
                        coinBalance = store.coinBalance,
                        avatars     = store.avatars
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading    = false,
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "No se pudo cargar la tienda"
                    )
                }
            }
        }
    }
    fun onPurchaseClick(avatarId: String) {
        _uiState.update { it.copy(confirmingAvatarId = avatarId) }
    }
    fun onDismissConfirmation() {
        _uiState.update { it.copy(confirmingAvatarId = null) }
    }
    fun onConfirmPurchase() {
        val avatarId = _uiState.value.confirmingAvatarId ?: return
        _uiState.update { it.copy(confirmingAvatarId = null) }
        viewModelScope.launch {
            _uiState.update { it.copy(processingAvatarId = avatarId, errorMessage = null) }
            val result = useCases.purchaseAvatar(currentStore, avatarId)
            applyStoreResult(result)
        }
    }

    fun onEquipAvatar(avatarId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(processingAvatarId = avatarId, errorMessage = null) }
            val result = useCases.equipAvatar(currentStore, avatarId)
            applyStoreResult(result)
        }
    }

    fun onErrorDismissed() = _uiState.update { it.copy(errorMessage = null) }

    private fun applyStoreResult(result: Result<Store>) {
        if (result.isSuccess) {
            val store = result.getOrThrow()
            currentStore = store
            _uiState.update {
                it.copy(
                    processingAvatarId = null,
                    coinBalance        = store.coinBalance,
                    avatars            = store.avatars
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    processingAvatarId = null,
                    errorMessage       = result.exceptionOrNull()?.message ?: "Ocurrió un error"
                )
            }
        }
    }
}