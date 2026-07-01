package com.williamsel.mathstack.features.creategroups.data.datasource.api

import com.williamsel.mathstack.features.creategroups.data.models.CreateGroupRequestDto
import com.williamsel.mathstack.features.creategroups.data.models.CreatedGroupDto
import retrofit2.http.Body
import retrofit2.http.POST

interface CreategroupsApi {

    @POST("groups")
    suspend fun createGroup(@Body request: CreateGroupRequestDto): CreatedGroupDto
}
