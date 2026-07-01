package com.williamsel.mathstack.features.creategroups.domain.usecases

import com.williamsel.mathstack.features.creategroups.domain.entities.CreateGroupParams
import com.williamsel.mathstack.features.creategroups.domain.entities.CreatedGroup
import com.williamsel.mathstack.features.creategroups.domain.repositories.CreategroupsRepository
import javax.inject.Inject

class CreateGroupUseCase @Inject constructor(
    private val repository: CreategroupsRepository
) {
    suspend operator fun invoke(params: CreateGroupParams): Result<CreatedGroup> =
        repository.createGroup(params)
}

data class CreategroupsUseCases(
    val createGroup: CreateGroupUseCase
)
