package com.williamsel.mathstack.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "path_statuses")
data class PathStatusEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
)