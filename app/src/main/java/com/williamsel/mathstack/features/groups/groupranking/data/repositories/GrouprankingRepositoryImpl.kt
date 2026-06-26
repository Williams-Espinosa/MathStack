package com.williamsel.mathstack.features.private.groupranking.data.repositories

import com.williamsel.mathstack.features.private.groupranking.data.datasource.api.GrouprankingApi
import com.williamsel.mathstack.features.private.groupranking.data.mapper.toDomain
import com.williamsel.mathstack.features.private.groupranking.domain.entities.GlobalRanking
import com.williamsel.mathstack.features.private.groupranking.domain.entities.GroupRanking
import com.williamsel.mathstack.features.private.groupranking.domain.repositories.GrouprankingRepository
import javax.inject.Inject

class GrouprankingRepositoryImpl @Inject constructor(
    private val api: GrouprankingApi
) : GrouprankingRepository {

    override suspend fun getGroupRanking(groupId: String): Result<GroupRanking> =
        runCatching { api.getGroupRanking(groupId).toDomain() }

    override suspend fun getGlobalRanking(): Result<GlobalRanking> =
        runCatching { api.getGlobalRanking().toDomain() }
}
