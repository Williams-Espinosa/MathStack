package com.williamsel.mathstack.core.di

import android.content.Context
import androidx.room.Room
import com.williamsel.mathstack.core.database.MathStackDatabase
import com.williamsel.mathstack.core.database.dao.ChallengeDao
import com.williamsel.mathstack.core.database.dao.LearningDao
import com.williamsel.mathstack.core.database.dao.PracticeDao
import com.williamsel.mathstack.core.database.dao.StoreDao
import com.williamsel.mathstack.core.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMathStackDatabase(
        @ApplicationContext context: Context
    ): MathStackDatabase {
        return Room.databaseBuilder(
            context,
            MathStackDatabase::class.java,
            "mathstack_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(
        database: MathStackDatabase
    ): UserDao = database.userDao()

    @Provides
    fun provideLearningDao(
        database: MathStackDatabase
    ): LearningDao = database.learningDao()

    @Provides
    fun providePracticeDao(
        database: MathStackDatabase
    ): PracticeDao = database.practiceDao()

    @Provides
    fun provideChallengeDao(
        database: MathStackDatabase
    ): ChallengeDao = database.challengeDao()

    @Provides
    fun provideStoreDao(
        database: MathStackDatabase
    ): StoreDao = database.storeDao()
}