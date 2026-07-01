package com.williamsel.mathstack.features.profile.profile.data.mapper

import com.williamsel.mathstack.features.profile.profile.data.models.AchievementDto
import com.williamsel.mathstack.features.profile.profile.data.models.ProfileDto
import com.williamsel.mathstack.features.profile.profile.domain.entities.Achievement
import com.williamsel.mathstack.features.profile.profile.domain.entities.Profile

fun ProfileDto.toDomain(): Profile = Profile(
    username              = username,
    email                 = email,
    avatarEmoji           = avatarEmoji,
    level                 = level,
    totalXp               = totalXp,
    coins                 = coins,
    streak                = streak,
    currentLevelXp        = currentLevelXp,
    nextLevelXp           = nextLevelXp,
    lessonsCompleted      = lessonsCompleted,
    achievementsUnlocked  = achievementsUnlocked,
    achievements          = achievements.map { it.toDomain() }
)

fun AchievementDto.toDomain(): Achievement = Achievement(
    id         = id,
    title      = title,
    emoji      = emoji,
    isUnlocked = isUnlocked
)