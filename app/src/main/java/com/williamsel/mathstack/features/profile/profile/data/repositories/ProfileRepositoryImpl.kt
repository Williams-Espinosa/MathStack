package com.williamsel.mathstack.features.profile.profile.data.repositories

import com.williamsel.mathstack.features.profile.profile.data.datasource.api.ProfileApi
import com.williamsel.mathstack.features.profile.profile.data.mapper.toDomain
import com.williamsel.mathstack.features.profile.profile.domain.entities.Profile
import com.williamsel.mathstack.features.profile.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi
) : ProfileRepository {

    override suspend fun getProfile(): Result<Profile> = runCatching {
        api.getProfile().toDomain()
    }
}