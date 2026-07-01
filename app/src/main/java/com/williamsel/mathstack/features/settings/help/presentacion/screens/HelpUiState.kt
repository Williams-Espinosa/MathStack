package com.williamsel.mathstack.features.settings.help.presentacion.screens

import androidx.compose.ui.graphics.vector.ImageVector

data class FaqItem(
    val question: String,
    val answer: String,
    val isExpanded: Boolean = false
)

data class FaqCategory(
    val title: String,
    val icon: ImageVector,
    val items: List<FaqItem>
)

data class HelpUiState(
    val searchQuery: String = "",
    val categories: List<FaqCategory> = emptyList()
)
