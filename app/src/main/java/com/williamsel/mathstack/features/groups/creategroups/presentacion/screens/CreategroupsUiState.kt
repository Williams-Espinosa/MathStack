package com.williamsel.mathstack.features.private.creategroups.presentacion.screens

data class CreategroupsUiState(
    val name: String           = "",
    val description: String    = "",
    val subject: String        = "Álgebra",
    val maxMembers: Int        = 20,
    val isLoading: Boolean     = false,
    val successGroupId: String? = null,
    val errorMessage: String?  = null
)
