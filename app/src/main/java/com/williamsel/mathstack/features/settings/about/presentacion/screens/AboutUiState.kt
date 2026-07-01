package com.williamsel.mathstack.features.settings.about.presentacion.screens

enum class AboutTab { INFO, TEAM }

data class AboutUiState(
    val selectedTab: AboutTab = AboutTab.INFO
)
