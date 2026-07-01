package com.williamsel.mathstack.features.profile.profile.domain.usecases

import com.williamsel.mathstack.features.profile.profile.domain.entities.Profile
import com.williamsel.mathstack.features.profile.profile.domain.repositories.ProfileRepository
import javax.inject.Inject
class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Result<Profile> = repository.getProfile()
}
data class ProfileUseCases(
    val getProfile: GetProfileUseCase
)