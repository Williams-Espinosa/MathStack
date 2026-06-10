package com.williamsel.mathstack.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.williamsel.mathstack.core.database.entity.PracticeSessionEntity

@Dao
interface PracticeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: PracticeSessionEntity)

    @Query("SELECT * FROM practice_sessions WHERE userId = :userId")
    suspend fun getSessions(userId: String): List<PracticeSessionEntity>
}