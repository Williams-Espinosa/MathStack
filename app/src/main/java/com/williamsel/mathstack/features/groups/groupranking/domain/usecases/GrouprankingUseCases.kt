package com.williamsel.mathstack.features.private.groupranking.domain.usecases

import com.williamsel.mathstack.features.private.groupranking.domain.entities.GlobalRanking
import com.williamsel.mathstack.features.private.groupranking.domain.entities.GroupRanking
import com.williamsel.mathstack.features.private.groupranking.domain.repositories.GrouprankingRepository
import javax.inject.Inject

class GetGroupRankingUseCase @Inject constructor(
    private val repository: GrouprankingRepository
) {
    suspend operator fun invoke(groupId: String): Result<GroupRanking> =
        repository.getGroupRanking(groupId)
}

class GetGlobalRankingUseCase @Inject constructor(
    private val repository: GrouprankingRepository
) {
    suspend operator fun invoke(): Result<GlobalRanking> =
        repository.getGlobalRanking()
}

data class GrouprankingUseCases(
    val getGroupRanking: GetGroupRankingUseCase,
    val getGlobalRanking: GetGlobalRankingUseCase
)
