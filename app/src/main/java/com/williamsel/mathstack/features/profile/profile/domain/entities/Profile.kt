package com.williamsel.mathstack.features.private.profile.domain.entities

data class Profile(
    val username: String,
    val email: String,
    val avatarEmoji: String,
    val level: Int,
    val totalXp: Int,
    val coins: Int,
    val streak: Int,
    val currentLevelXp: Int,
    val nextLevelXp: Int,
    val lessonsCompleted: Int,
    val achievementsUnlocked: Int,
    val achievements: List<Achievement>
) {
    val levelProgress: Float
        get() = if (nextLevelXp > 0) currentLevelXp.toFloat() / nextLevelXp else 0f

    val xpRemaining: Int
        get() = nextLevelXp - currentLevelXp
}

data class Achievement(
    val id: String,
    val title: String,
    val emoji: String,
    val isUnlocked: Boolean
)