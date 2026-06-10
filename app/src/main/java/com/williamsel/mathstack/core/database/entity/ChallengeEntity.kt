package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenges")
data class ChallengeEntity(
    @PrimaryKey
    val id: String,
    val creatorId: String,
    val statusId: Int,
    val createdAt: Long?
)