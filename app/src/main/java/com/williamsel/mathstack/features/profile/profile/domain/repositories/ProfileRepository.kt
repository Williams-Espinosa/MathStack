package com.williamsel.mathstack.features.private.profile.domain.repositories

import com.williamsel.mathstack.features.private.profile.domain.entities.Profile

interface ProfileRepository {

    suspend fun getProfile(): Result<Profile>
}