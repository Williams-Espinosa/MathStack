package com.tuapp.exercises.domain.entities

// ─────────────────────────────────────────────────────────────────────────────
//  Learning Path
// ─────────────────────────────────────────────────────────────────────────────

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
    val theory: LessonTheory?,          // loaded lazily when user taps
    val exercises: List<Exercise>        // available after finishing theory
)

enum class LessonStatus {
    COMPLETED,   // green check
    AVAILABLE,   // blue circle (unlocked)
    LOCKED       // grey lock
}

// ─────────────────────────────────────────────────────────────────────────────
//  Lesson theory (the "read" screen)
// ─────────────────────────────────────────────────────────────────────────────

data class LessonTheory(
    val lessonId: String,
    val estimatedMinutes: Int,
    val sections: List<TheorySection>
)

sealed interface TheorySection {
    /** Plain introductory paragraph */
    data class Introduction(val text: String) : TheorySection

    /** Worked example with a LaTeX problem and numbered steps */
    data class WorkedExample(
        val title: String,
        val problemLatex: String,        // e.g. "2x + 5 = 13"
        val steps: List<String>          // plain text steps
    ) : TheorySection

    /** Highlighted property / rule box */
    data class PropertyBox(
        val title: String,
        val body: String
    ) : TheorySection
}

// ─────────────────────────────────────────────────────────────────────────────
//  Exercises (free-input, not multiple choice)
// ─────────────────────────────────────────────────────────────────────────────

data class Exercise(
    val id: String,
    val number: Int,
    val instructionText: String,         // e.g. "Resuelve la siguiente ecuación"
    val questionLatex: String,           // LaTeX to render, e.g. "3x - 7 = 14"
    val hintLatex: String?,              // optional hint in LaTeX / plain text
    val answerLatex: String,             // correct answer latex, e.g. "x = 7"
    val acceptedAnswers: List<String>,   // normalised strings the server accepts
    val xpReward: Int
)

// ─────────────────────────────────────────────────────────────────────────────
//  Exercise session state (tracked in-memory / VM)
// ─────────────────────────────────────────────────────────────────────────────

data class ExerciseSession(
    val lessonId: String,
    val exercises: List<Exercise>,
    val currentIndex: Int = 0,
    val userInput: String = "",
    val earnedXp: Int = 0,
    val attempts: Int = 0,              // attempts for current exercise
    val isFinished: Boolean = false
) {
    val current: Exercise? get() = exercises.getOrNull(currentIndex)
    val totalExercises: Int get() = exercises.size
    val progress: Float get() =
        if (totalExercises == 0) 0f else currentIndex.toFloat() / totalExercises
}
