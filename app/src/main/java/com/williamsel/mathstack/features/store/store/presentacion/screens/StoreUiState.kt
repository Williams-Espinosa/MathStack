package com.williamsel.mathstack.features.private.store.presentacion.screens

import com.williamsel.mathstack.features.private.store.domain.entities.Avatar

data class StoreUiState(
    val coinBalance: Int = 0,
    val avatars: List<Avatar> = emptyList(),
    val isLoading: Boolean = true,
    val processingAvatarId: String? = null,
    val errorMessage: String? = null
)