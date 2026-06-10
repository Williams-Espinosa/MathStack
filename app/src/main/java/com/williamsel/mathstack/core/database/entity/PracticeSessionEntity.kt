package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "practice_sessions")
data class PracticeSessionEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val sessionDate: String?,
    val minutesSpent: Int,
    val createdAt: Long?
)