package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diagnostic_results")
data class DiagnosticResultEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val subjectId: Int,
    val deficiencyScore: Int,
    val evaluatedAt: Long?
)