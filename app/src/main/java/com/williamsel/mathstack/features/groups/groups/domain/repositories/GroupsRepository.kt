package com.williamsel.mathstack.features.groups.domain.repositories

import com.williamsel.mathstack.features.groups.domain.entities.Groups

interface GroupsRepository {

    suspend fun getGroups(): Result<Groups>

    suspend fun leaveGroup(groupId: String): Result<Unit>
}