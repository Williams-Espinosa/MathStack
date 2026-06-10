package com.williamsel.mathstack.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.williamsel.mathstack.core.database.dao.ChallengeDao
import com.williamsel.mathstack.core.database.dao.LearningDao
import com.williamsel.mathstack.core.database.dao.PracticeDao
import com.williamsel.mathstack.core.database.dao.StoreDao
import com.williamsel.mathstack.core.database.dao.UserDao
import com.williamsel.mathstack.core.database.entity.*

@Database(
    entities = [
        UserEntity::class,
        UserGamificationStatsEntity::class,
        UserPreferencesEntity::class,

        SubjectEntity::class,
        LessonTypeEntity::class,
        LessonEntity::class,
        ExerciseEntity::class,
        DiagnosticResultEntity::class,
        PathStatusEntity::class,
        LearningPathEntity::class,

        PracticeSessionEntity::class,
        ExerciseAttemptEntity::class,

        ChallengeStatusEntity::class,
        ChallengeEntity::class,
        ChallengeParticipantEntity::class,

        ItemTypeEntity::class,
        StoreItemEntity::class,
        UserInventoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MathStackDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun learningDao(): LearningDao

    abstract fun practiceDao(): PracticeDao

    abstract fun challengeDao(): ChallengeDao

    abstract fun storeDao(): StoreDao
}