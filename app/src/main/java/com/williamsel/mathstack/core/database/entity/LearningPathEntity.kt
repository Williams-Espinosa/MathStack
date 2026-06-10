package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity

@Entity(
    tableName = "learning_paths",
    primaryKeys = ["userId", "lessonId"]
)
data class LearningPathEntity(
    val userId: String,
    val lessonId: String,
    val statusId: Int,
    val completedAt: Long?
)