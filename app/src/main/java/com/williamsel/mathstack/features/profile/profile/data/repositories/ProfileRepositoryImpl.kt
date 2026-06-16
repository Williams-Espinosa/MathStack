package com.williamsel.mathstack.features.private.profile.data.repositories

import com.williamsel.mathstack.features.private.profile.data.datasource.api.ProfileApi
import com.williamsel.mathstack.features.private.profile.data.mapper.toDomain
import com.williamsel.mathstack.features.private.profile.domain.entities.Profile
import com.williamsel.mathstack.features.private.profile.domain.repositories.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileApi
) : ProfileRepository {

    override suspend fun getProfile(): Result<Profile> = runCatching {
        api.getProfile().toDomain()
    }
}