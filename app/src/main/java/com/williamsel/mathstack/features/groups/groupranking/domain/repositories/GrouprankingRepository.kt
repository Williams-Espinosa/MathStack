package com.williamsel.mathstack.features.groupranking.domain.repositories

import com.williamsel.mathstack.features.groupranking.domain.entities.GlobalRanking
import com.williamsel.mathstack.features.groupranking.domain.entities.GroupRanking

interface GrouprankingRepository {

    suspend fun getGroupRanking(groupId: String): Result<GroupRanking>

    suspend fun getGlobalRanking(): Result<GlobalRanking>
}
