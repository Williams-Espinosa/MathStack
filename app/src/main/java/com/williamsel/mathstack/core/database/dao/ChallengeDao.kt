package com.williamsel.mathstack.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.williamsel.mathstack.core.database.entity.ChallengeEntity

@Dao
interface ChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(challenge: ChallengeEntity)

    @Query("SELECT * FROM challenges")
    suspend fun getAll(): List<ChallengeEntity>
}