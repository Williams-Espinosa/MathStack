package com.williamsel.mathstack.features.profile.profile.domain.repositories

import com.williamsel.mathstack.features.profile.profile.domain.entities.Profile

interface ProfileRepository {

    suspend fun getProfile(): Result<Profile>
}