package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_gamification_stats")
data class UserGamificationStatsEntity(
    @PrimaryKey
    val userId: String,
    val coins: Int = 0,
    val currentLevel: Int = 1,
    val xpPoints: Int = 0,
    val lessonsCompletedCount: Int = 0,
    val currentStreak: Int = 0,
    val maxStreak: Int = 0,
    val minutesPracticed: Int = 0,
    val lastPracticeDate: String?
)