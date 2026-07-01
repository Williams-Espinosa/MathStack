package com.williamsel.mathstack.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Login : Route
    
    @Serializable
    data object Register : Route

    @Serializable
    data object TermsAndConditions : Route
    @Serializable
    data object Home : Route
    @Serializable
    data object Groups : Route
    @Serializable
    data object CreateGroup : Route
    @Serializable
    data class GroupDetails(val groupId: String) : Route
}
