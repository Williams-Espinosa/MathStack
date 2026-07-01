package com.williamsel.mathstack.features.profile.profile.data.models

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("avatar_emoji")
    val avatarEmoji: String,

    @SerializedName("level")
    val level: Int,

    @SerializedName("total_xp")
    val totalXp: Int,

    @SerializedName("coins")
    val coins: Int,

    @SerializedName("streak")
    val streak: Int,

    @SerializedName("current_level_xp")
    val currentLevelXp: Int,

    @SerializedName("next_level_xp")
    val nextLevelXp: Int,

    @SerializedName("lessons_completed")
    val lessonsCompleted: Int,

    @SerializedName("achievements_unlocked")
    val achievementsUnlocked: Int,

    @SerializedName("achievements")
    val achievements: List<AchievementDto>
)

data class AchievementDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("emoji")
    val emoji: String,

    @SerializedName("is_unlocked")
    val isUnlocked: Boolean
)