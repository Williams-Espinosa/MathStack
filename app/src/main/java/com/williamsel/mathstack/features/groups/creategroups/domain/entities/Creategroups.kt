package com.williamsel.mathstack.features.creategroups.domain.entities

data class CreateGroupParams(
    val name: String,
    val description: String,
    val subject: String,
    val maxMembers: Int
)

data class CreatedGroup(
    val id: String,
    val name: String,
    val description: String,
    val subject: String,
    val maxMembers: Int
)
