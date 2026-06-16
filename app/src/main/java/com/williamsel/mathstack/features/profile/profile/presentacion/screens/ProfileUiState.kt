package com.williamsel.mathstack.features.private.profile.presentacion.screens

import com.williamsel.mathstack.features.private.profile.domain.entities.Achievement

data class ProfileUiState(
    val username: String         = "",
    val email: String            = "",
    val avatarEmoji: String      = "🧑‍🎓",
    val level: Int               = 0,

    val totalXp: Int             = 0,
    val coins: Int               = 0,
    val streak: Int              = 0,

    val levelProgress: Float     = 0f,
    val xpRemaining: Int         = 0,
    val nextLevel: Int           = 0,

    val lessonsCompleted: Int         = 0,
    val achievementsUnlocked: Int     = 0,

    val achievements: List<Achievement> = emptyList(),

    val isLoading: Boolean       = true,
    val errorMessage: String?    = null
)