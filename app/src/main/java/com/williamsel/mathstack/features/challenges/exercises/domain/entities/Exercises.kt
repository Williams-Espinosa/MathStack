package com.williamsel.mathstack.features.challenges.exercises.domain.entities

data class LearningPath(
    val id: String,
    val title: String,
    val totalLessons: Int,
    val totalXp: Int,
    val completedLessons: Int,
    val lessons: List<Lesson>
) {
    val progressFraction: Float
        get() = if (totalLessons == 0) 0f else completedLessons.toFloat() / totalLessons
}

data class Lesson(
    val id: String,
    val number: Int,
    val title: String,
    val description: String,
    val xpReward: Int,
    val status: LessonStatus,
    val theory: LessonTheory?,
    val exercises: List<Exercise>
)

enum class LessonStatus {
    COMPLETED,
    AVAILABLE,
    LOCKED
}

data class LessonTheory(
    val lessonId: String,
    val estimatedMinutes: Int,
    val sections: List<TheorySection>
)

sealed interface TheorySection {
  data class Introduction(val text: String) : TheorySection

    data class WorkedExample(
        val title: String,
        val problemLatex: String,
        val steps: List<String>
    ) : TheorySection

    data class PropertyBox(
        val title: String,
        val body: String
    ) : TheorySection
}

data class Exercise(
    val id: String,
    val number: Int,
    val instructionText: String,
    val questionLatex: String,
    val hintLatex: String?,
    val answerLatex: String,
    val acceptedAnswers: List<String>,
    val xpReward: Int
)

data class ExerciseSession(
    val lessonId: String,
    val exercises: List<Exercise>,
    val currentIndex: Int = 0,
    val userInput: String = "",
    val earnedXp: Int = 0,
    val attempts: Int = 0,
    val isFinished: Boolean = false
) {
    val current: Exercise? get() = exercises.getOrNull(currentIndex)
    val totalExercises: Int get() = exercises.size
    val progress: Float get() =
        if (totalExercises == 0) 0f else currentIndex.toFloat() / totalExercises
}
