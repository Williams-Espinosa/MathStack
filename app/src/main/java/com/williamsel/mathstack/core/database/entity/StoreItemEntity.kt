package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "store_items")
data class StoreItemEntity(
    @PrimaryKey
    val id: String,
    val itemTypeId: Int,
    val name: String,
    val cost: Int,
    val assetUrl: String
)