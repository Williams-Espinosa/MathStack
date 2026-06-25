package com.williamsel.mathstack.features.private.groups.data.repositories

import com.williamsel.mathstack.features.private.groups.data.datasource.api.GroupsApi
import com.williamsel.mathstack.features.private.groups.data.mapper.toDomain
import com.williamsel.mathstack.features.private.groups.domain.entities.Groups
import com.williamsel.mathstack.features.private.groups.domain.repositories.GroupsRepository
import javax.inject.Inject


class GroupsRepositoryImpl @Inject constructor(
    private val api: GroupsApi
) : GroupsRepository {

    override suspend fun getGroups(): Result<Groups> = runCatching {
        api.getGroups().toDomain()
    }

    override suspend fun leaveGroup(groupId: String): Result<Unit> = runCatching {
        api.leaveGroup(groupId)
    }
}