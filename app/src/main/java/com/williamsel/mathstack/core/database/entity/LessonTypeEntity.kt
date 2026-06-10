package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_types")
data class LessonTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)