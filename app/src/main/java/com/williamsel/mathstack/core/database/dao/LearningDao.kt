package com.williamsel.mathstack.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.williamsel.mathstack.core.database.entity.ExerciseEntity
import com.williamsel.mathstack.core.database.entity.LessonEntity

@Dao
interface LearningDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lesson: LessonEntity)

    @Query("SELECT * FROM lessons")
    suspend fun getLessons(): List<LessonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Query("SELECT * FROM exercises WHERE lessonId = :lessonId")
    suspend fun getExercisesByLesson(lessonId: String): List<ExerciseEntity>
}