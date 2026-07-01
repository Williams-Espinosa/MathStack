package com.williamsel.mathstack.features.groups.data.repositories

import com.williamsel.mathstack.features.groups.data.datasource.api.GroupsApi
import com.williamsel.mathstack.features.groups.data.mapper.toDomain
import com.williamsel.mathstack.features.groups.domain.entities.Groups
import com.williamsel.mathstack.features.groups.domain.repositories.GroupsRepository
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