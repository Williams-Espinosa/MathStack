package com.williamsel.mathstack.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    object Home : Route
}
