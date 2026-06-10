package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity

@Entity(
    tableName = "challenge_participants",
    primaryKeys = ["challengeId", "userId"]
)
data class ChallengeParticipantEntity(
    val challengeId: String,
    val userId: String,
    val completionTimeSeconds: Int?,
    val isWinner: Boolean = false,
    val joinedAt: Long?
)