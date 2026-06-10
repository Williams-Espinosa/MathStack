package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_attempts")
data class ExerciseAttemptEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val exerciseId: String,
    val isCorrect: Boolean,
    val attemptedAt: Long?
)