package com.williamsel.mathstack.features.store.presentacion.screens

import com.williamsel.mathstack.features.store.domain.entities.Avatar

data class StoreUiState(
    val coinBalance: Int           = 0,
    val avatars: List<Avatar>      = emptyList(),
    val isLoading: Boolean         = true,
    val processingAvatarId: String? = null,
    val confirmingAvatarId: String? = null,
    val errorMessage: String?      = null
) {
    val confirmingAvatar: Avatar?
        get() = confirmingAvatarId?.let { id -> avatars.find { it.id == id } }
}