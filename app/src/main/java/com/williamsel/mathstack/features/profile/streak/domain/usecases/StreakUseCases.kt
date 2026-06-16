package com.williamsel.mathstack.features.private.streak.domain.usecases

import com.williamsel.mathstack.features.private.streak.domain.entities.Streak
import com.williamsel.mathstack.features.private.streak.domain.repositories.StreakRepository
import javax.inject.Inject
class GetStreakUseCase @Inject constructor(
    private val repository: StreakRepository
) {
    suspend operator fun invoke(): Result<Streak> = repository.getStreak()
}

data class StreakUseCases(
    val getStreak: GetStreakUseCase
)