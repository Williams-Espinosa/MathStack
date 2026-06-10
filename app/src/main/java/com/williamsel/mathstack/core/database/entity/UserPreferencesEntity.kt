package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_preferences")
data class UserPreferencesEntity(
    @PrimaryKey
    val userId: String,
    val pushNotificationsEnabled: Boolean = true,
    val preferredPracticeTime: String?
)