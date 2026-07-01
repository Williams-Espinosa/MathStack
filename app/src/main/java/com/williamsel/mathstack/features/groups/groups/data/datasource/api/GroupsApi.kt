package com.williamsel.mathstack.features.groups.data.datasource.api

import com.williamsel.mathstack.features.groups.data.models.GroupsDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupsApi {

    @GET("groups")
    suspend fun getGroups(): GroupsDto

    @DELETE("groups/{groupId}/members/me")
    suspend fun leaveGroup(@Path("groupId") groupId: String)
}