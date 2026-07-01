package com.williamsel.mathstack.features.groupranking.data.datasource.api

import com.williamsel.mathstack.features.groupranking.data.models.GlobalRankingDto
import com.williamsel.mathstack.features.groupranking.data.models.GroupRankingDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GrouprankingApi {

    @GET("groups/{groupId}/ranking")
    suspend fun getGroupRanking(@Path("groupId") groupId: String): GroupRankingDto

    @GET("ranking/global")
    suspend fun getGlobalRanking(): GlobalRankingDto
}
