package com.williamsel.mathstack.features.private.creategroups.domain.repositories

import com.williamsel.mathstack.features.private.creategroups.domain.entities.CreateGroupParams
import com.williamsel.mathstack.features.private.creategroups.domain.entities.CreatedGroup

interface CreategroupsRepository {

    suspend fun createGroup(params: CreateGroupParams): Result<CreatedGroup>
}
