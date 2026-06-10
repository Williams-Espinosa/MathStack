package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity

@Entity(
    tableName = "user_inventory",
    primaryKeys = ["userId", "itemId"]
)
data class UserInventoryEntity(
    val userId: String,
    val itemId: String,
    val isEquipped: Boolean = false,
    val acquiredAt: Long?
)