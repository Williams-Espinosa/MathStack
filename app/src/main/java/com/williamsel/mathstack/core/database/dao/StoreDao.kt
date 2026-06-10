package com.williamsel.mathstack.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.williamsel.mathstack.core.database.entity.StoreItemEntity

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: StoreItemEntity)

    @Query("SELECT * FROM store_items")
    suspend fun getItems(): List<StoreItemEntity>
}