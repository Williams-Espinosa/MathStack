package com.williamsel.mathstack.features.private.groupranking.domain.repositories

import com.williamsel.mathstack.features.private.groupranking.domain.entities.GlobalRanking
import com.williamsel.mathstack.features.private.groupranking.domain.entities.GroupRanking

interface GrouprankingRepository {

    suspend fun getGroupRanking(groupId: String): Result<GroupRanking>

    suspend fun getGlobalRanking(): Result<GlobalRanking>
}
