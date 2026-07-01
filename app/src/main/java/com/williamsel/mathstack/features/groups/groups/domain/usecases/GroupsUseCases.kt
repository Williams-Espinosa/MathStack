package com.williamsel.mathstack.features.groups.domain.usecases

import com.williamsel.mathstack.features.groups.domain.entities.Groups
import com.williamsel.mathstack.features.groups.domain.repositories.GroupsRepository
import javax.inject.Inject

class GetGroupsUseCase @Inject constructor(
    private val repository: GroupsRepository
) {
    suspend operator fun invoke(): Result<Groups> = repository.getGroups()
}

class LeaveGroupUseCase @Inject constructor(
    private val repository: GroupsRepository
) {
    suspend operator fun invoke(groupId: String): Result<Unit> =
        repository.leaveGroup(groupId)
}

data class GroupsUseCases(
    val getGroups: GetGroupsUseCase,
    val leaveGroup: LeaveGroupUseCase
)