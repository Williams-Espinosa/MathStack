package com.williamsel.mathstack.features.private.groups.presentacion.screens

import com.williamsel.mathstack.features.private.groups.domain.entities.Group

data class GroupsUiState(
    val myGroupsCount: Int       = 0,
    val groups: List<Group>      = emptyList(),
    val isLoading: Boolean       = true,
    val processingGroupId: String? = null,
    val errorMessage: String?    = null
)