package com.williamsel.mathstack.features.challenges.exercises.di
import com.tuapp.exercises.data.repositories.ExercisesRepositoryImpl
import  com.williamsel.mathstack.features.challenges.exercises.data.datasource.api.ExercisesApi
import  com.williamsel.mathstack.features.challenges.exercises.domain.repositories.ExercisesRepository
import  com.williamsel.mathstack.features.challenges.exercises.domain.usecases.CheckAnswerUseCase
import  com.williamsel.mathstack.features.challenges.exercises.domain.usecases.CompleteLessonUseCase
import  com.williamsel.mathstack.features.challenges.exercises.domain.usecases.ExercisesUseCases
import  com.williamsel.mathstack.features.challenges.exercises.domain.usecases.GetLearningPathUseCase
import  com.williamsel.mathstack.features.challenges.exercises.domain.usecases.GetLessonTheoryUseCase
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
