package com.tuapp.exercises.di

import com.tuapp.exercises.data.datasource.api.ExercisesApi
import com.tuapp.exercises.data.repositories.ExercisesRepositoryImpl
import com.tuapp.exercises.domain.repositories.ExercisesRepository
import com.tuapp.exercises.domain.usecases.CheckAnswerUseCase
import com.tuapp.exercises.domain.usecases.CompleteLessonUseCase
import com.tuapp.exercises.domain.usecases.ExercisesUseCases
import com.tuapp.exercises.domain.usecases.GetLearningPathUseCase
import com.tuapp.exercises.domain.usecases.GetLessonTheoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExercisesModule {

    @Provides
    @Singleton
    fun provideExercisesApi(retrofit: Retrofit): ExercisesApi =
        retrofit.create(ExercisesApi::class.java)

    @Provides
    @Singleton
    fun provideExercisesRepository(
        api: ExercisesApi
    ): ExercisesRepository = ExercisesRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideExercisesUseCases(
        repository: ExercisesRepository
    ): ExercisesUseCases = ExercisesUseCases(
        getLearningPath = GetLearningPathUseCase(repository),
        getLessonTheory = GetLessonTheoryUseCase(repository),
        checkAnswer     = CheckAnswerUseCase(repository),
        completeLesson  = CompleteLessonUseCase(repository)
    )
}
