package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lessons")
data class LessonEntity(
    @PrimaryKey
    val id: String,
    val subjectId: Int,
    val lessonTypeId: Int,
    val title: String,
    val difficultyLevel: Int
)