package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenge_statuses")
data class ChallengeStatusEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)