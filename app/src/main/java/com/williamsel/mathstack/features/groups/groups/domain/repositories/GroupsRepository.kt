package com.williamsel.mathstack.features.private.groups.domain.repositories

import com.williamsel.mathstack.features.private.groups.domain.entities.Groups

interface GroupsRepository {

    suspend fun getGroups(): Result<Groups>

    suspend fun leaveGroup(groupId: String): Result<Unit>
}