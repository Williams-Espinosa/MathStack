package com.williamsel.mathstack.features.settings.accountmanagement.presentacion.screens

data class AccountManagementUiState(
    val username: String = "",
    val email: String = "",
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val accountDeleted: Boolean = false
)
