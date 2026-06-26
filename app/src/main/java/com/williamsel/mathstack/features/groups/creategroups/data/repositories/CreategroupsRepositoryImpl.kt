package com.williamsel.mathstack.features.private.creategroups.data.repositories

import com.williamsel.mathstack.features.private.creategroups.data.datasource.api.CreategroupsApi
import com.williamsel.mathstack.features.private.creategroups.data.mapper.toDomain
import com.williamsel.mathstack.features.private.creategroups.data.mapper.toDto
import com.williamsel.mathstack.features.private.creategroups.domain.entities.CreateGroupParams
import com.williamsel.mathstack.features.private.creategroups.domain.entities.CreatedGroup
import com.williamsel.mathstack.features.private.creategroups.domain.repositories.CreategroupsRepository
import javax.inject.Inject

class CreategroupsRepositoryImpl @Inject constructor(
    private val api: CreategroupsApi
) : CreategroupsRepository {

    override suspend fun createGroup(params: CreateGroupParams): Result<CreatedGroup> =
        runCatching {
            api.createGroup(params.toDto()).toDomain()
        }
}
